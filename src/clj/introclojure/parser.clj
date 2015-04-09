(ns introclojure.parser)


(require '[clojure.java.io :as io])


(require '[rewrite-clj.zip :as z])




 (require '[leiningen.midje-doc.run.parser :refer [parse-content]])



 (require '[leiningen.midje-doc.run.renderer :refer [render-html-doc2 *plain*]])



(defn all [] (binding [*plain* true]



 (render-html-doc2 (parse-content (z/of-string (slurp

                                   (io/input-stream (io/resource "workshop/index.clj")))))
 )))



(defn partition-with [f col]
 (apply conj (reduce  (fn [[acc c] n]
             (if (f n)
               [(conj acc c)[n]]
               [acc (conj c n)]

             )) [[] []] col)


  ))


(partition-with (partial = 2) [1 1 2 1])



(defn fmap [f m]
  (into {} (for [[k v] m] [k (f v)] )))


#_ (into {} (for [ch  (partition-with
 (fn [s]
   (and (map? s) (= :chapter (:type s))))
 (parse-content (z/of-string (slurp (io/input-stream (io/resource "workshop/do_things.clj"))))))]
  [(:tag (first ch)) ch]

  ))


(defn content ([]

  (into {} (for [ch  (partition-with
 (fn [s]
   (and (map? s) (= :chapter (:type s))))
 (parse-content (z/of-string (slurp (io/input-stream (io/resource "workshop/do_things.clj"))))))]
  [(:tag (first ch)) (render-html-doc2 ch)]

  )))

  ([tag] ((content) tag))

  )





(defn main []
  (keys (content))

  )

(defn do-things []
  (binding [*plain* true]
    (render-html-doc2 (parse-content (z/of-string (slurp (io/input-stream (io/resource "workshop/do_things.clj")))))
      )))

 ; (z/of-file "./workshop/index.clj"))
