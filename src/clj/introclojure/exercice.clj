(ns introclojure.exercice
  (:require [net.cgrand.sjacket :as sj]
            [net.cgrand.sjacket.parser :as p]
            [clojure.string]
            [clojure.pprint]))


(defn pos-to-idx [text line ch]
  (apply + ch line
         (map count
              (take line
                    (clojure.string/split-lines text)))))


(defn idx-to-pos [text idx]
  (let [lines
        (clojure.string/split-lines
         (apply str (take (inc idx) text)))]
    [(-> lines count dec)
     (- idx (reduce (partial + 1) 0
                    (map count (or (butlast lines) []))))]))




 ; (map #(if (< %1 0) 0 %1)
 ;       ((juxt #(-> % count dec) #(-> % last count dec))
 ;        (clojure.string/split-lines (apply str (take (inc idx) (str text " ")))))))



(idx-to-pos "\n111\n2" 5)
       ;    "0 123 45"

(defn find-form-offsets [text]
  (second
   (reduce (fn [[idx forms] form]
                   (let [st (sj/str-pt form)
                         nidx (+ idx (count st))]
                     [nidx
                      (case (:tag form)
                        :whitespace
                        forms
                        :newline
                        forms
                        (conj forms [idx nidx]))]
                     ))
                 [0 []]
                 (:content  (p/parser text)))))


;; TRY TO CORRECT FORM
(require 'clojure.walk)
(clojure.walk/postwalk
 (fn [f] (if (instance? net.cgrand.parsley.Node f)
           (case (:tag f ) :net.cgrand.parsley/unfinished (filter identity (flatten (:content f ))) nil) f)) (p/parser "((abc"))


(defn fo-to-info [fo text]
  (zipmap [:start :end]
          (map
           #(zipmap [:line :ch]
                    (idx-to-pos text %)) fo)))

(defn pprint-to-str [f]
  (if (var? f)
    (str f)
    (let [s (java.io.StringWriter.)]
      (binding [*out* s]
        (clojure.pprint/pprint f))
      (let [r (.toString s)]
        (.substring r 0 (dec (.length r)))))))


(defn eval-from-text [text line pos]
  (let [ffo (find-form-offsets text)
        idx (pos-to-idx text line pos)
        [s e :as fo]  (or (last (filter #(>= idx (first %)) ffo)) (first ffo))
        f   (.substring text s e)]
    (assoc (fo-to-info  fo text) :eval (pprint-to-str (eval (read-string f))))))


#_ (find-form-offsets "(+ 1 2)\n(+ 3 4)")

#_ (idx-to-pos "(+ 1 2)\n a" 13)

#_ (find-form-offsets " (+ 1 2)\n a")

#_ (eval-from-text "(def a 1)\n a" 0 0)



(def seq-id (atom 0))


(def store (atom {}))


(defn store-and-get-id [struc]
  (if-let [id? (@store struc)]
    id?
    (let [id (str "s" (swap! seq-id inc))]
      (swap! store assoc id struc struc id)
      id)))





store

(eval-exercice {:e_id "s1",
 :text "(def a 1)\n\n(+ a a)",
 :reset false,
 :all false,
 :selection {:anchor {:line 2, :ch 6}, :head {:line 2, :ch 6}}})


(defn eval-exercice
  ([m] (eval-exercice m @store))
  ([{:keys [e_id text reset all selection] :as m} store ]
  (clojure.pprint/pprint m)
  (let [exo (store e_id)
        {:keys [line ch]} (:anchor selection)
        [_ cts _] (:content exo)

        ffo  (find-form-offsets text)
        idx  (pos-to-idx text line ch)
        [s e :as fo]  (or (last (filter #(>= idx (first %)) ffo)) (first ffo))
        f    (.substring text s e)
        sexp (read-string f)]

    {:exercice (into {} (for [c cts]
                 [(store [exo c]) (= (eval (clojure.walk/postwalk #(if (= %1 '__) sexp %1) c)) true)]))
     :eval [(assoc (fo-to-info  fo text)
              :result (pprint-to-str (eval (read-string f)))
              :out ""
              :err "")]}
  )))
