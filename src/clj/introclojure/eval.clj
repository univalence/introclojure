(ns introclojure.eval
  (:require [net.cgrand.sjacket :as sj]
            [net.cgrand.sjacket.parser :as p]
            [clojure.string]
            [clojure.pprint]))


(defn pos-to-idx [text line pos]
  (apply + pos line
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
           #(zipmap [:line :pos]
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


(find-form-offsets "(+ 1 2)\n(+ 3 4)")


(idx-to-pos "(+ 1 2)\n a" 13)

(find-form-offsets " (+ 1 2)\n a")

(eval-from-text "(def a 1)\n a" 0 0)
