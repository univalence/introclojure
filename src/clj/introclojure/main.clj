(ns introclojure.main
    (:gen-class))

(defn -main []
  (eval '(do
           (require 'introclojure.server)
           (introclojure.server/run)
           (require 'clojure.main)
           (clojure.main/main))))
