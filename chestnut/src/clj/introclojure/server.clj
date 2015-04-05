(ns introclojure.server
  (:require [clojure.java.io :as io]
            [introclojure.dev :refer [is-dev? inject-devmode-html browser-repl start-figwheel]]
            [compojure.core :refer [GET defroutes POST]]
            [compojure.route :refer [resources]]
            [net.cgrand.enlive-html :refer [deftemplate]]
            [net.cgrand.reload :refer [auto-reload]]
            [ring.middleware.reload :as reload]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [environ.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]]))

(deftemplate page (io/resource "index.html") []
  [:body] (if is-dev? inject-devmode-html identity))

(require 'introclojure.eval)

(use '[ring.middleware.json :only [wrap-json-response]]
     '[ring.util.response :only [response]])


(defroutes routes
  (resources "/")
  (resources "/react" {:root "react"})
  (POST "/eval" [text line pos] (response (introclojure.eval/eval-from-text text (read-string line) (read-string pos))))
  (GET "/*" req (page)))




(def http-handler
   (if is-dev?
    (reload/wrap-reload (wrap-json-response (wrap-defaults #'routes api-defaults)))
    (wrap-json-response (wrap-defaults routes api-defaults))))

(defn run-web-server [& [port]]
  (let [port (Integer. (or port (env :port) 10555))]
    (print "Starting web server on port" port ".\n")
    (run-jetty http-handler {:port port :join? false})))

(defn run-auto-reload [& [port]]
  (auto-reload *ns*)
  (start-figwheel))

(defn run [& [port]]
  (when is-dev?
    (run-auto-reload))
  (run-web-server port))

(defn -main [& [port]]
  (run port))

(defonce server (run))

