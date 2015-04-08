(ns introclojure.parser)


(require '[clojure.java.io :as io])


(require '[rewrite-clj.zip :as z])




 (require '[leiningen.midje-doc.run.parser :refer [parse-content]])



 (require '[leiningen.midje-doc.run.renderer :refer [render-html-doc2 *plain*]])



(defn all [] (binding [*plain* true]
 (render-html-doc2 (parse-content (z/of-string (slurp

                                   (io/input-stream (io/resource "workshop/index.clj")))))
 )))


(defn do-things []
  (binding [*plain* true]
    (render-html-doc2 (parse-content (z/of-string (slurp

                                                    (io/input-stream (io/resource "workshop/do-things.clj")))))
      )))

 ; (z/of-file "./workshop/index.clj"))
