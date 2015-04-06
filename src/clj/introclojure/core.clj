(ns introclojure.core
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.webjars :refer [wrap-webjars]]))




(defroutes app-routes
  ; to serve document root address
  (GET "/" [] "<p>Hello from compojure</p>")
  ; to serve static pages saved in resources/public directory
  (route/resources "/")

  ; if page is not found
  (route/not-found "Page not found"))

;; site function creates a handler suitable for a standard website,
;; adding a bunch of standard ring middleware to app-route:

(def app
  (-> (handler/site app-routes)
      (wrap-webjars)
  ))

;(defonce app 3000)

(do
  (use 'ring.adapter.jetty)
  (defonce server (run-jetty #'app {:port 8080 :join? false})))
