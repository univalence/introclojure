




(ns introclojure.parser)


(require '[rewrite-clj.zip :as z])




 (require '[leiningen.midje-doc.run.parser :refer [parse-content]])



 (require '[leiningen.midje-doc.run.renderer :refer [render-html-doc2 *plain*]])



(binding [*plain* false]

 (render-html-doc2 "idx2.html" (parse-content (z/of-file "workshop/index.clj")))
 )

 (render-html-doc2 "idx2.html"

 (parse-content

  (z/of-string "(comment (+ 1 2))
                (exercice :yo)
               (comment (+ 3 4 5))
               ")))

 ; (z/of-file "./workshop/index.clj"))
