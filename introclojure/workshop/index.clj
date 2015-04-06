(ns workshop.index)

"bibliographie :
- [http://java.ociweb.com/mark/clojure/article.html](http://java.ociweb.com/mark/clojure/article.html)
- http://www.braveclojure.com/"


[[{:private [
(require 'clojure.walk)

(defmacro exercice [doc exs result]
  ;;`(every? true? ~
  (clojure.walk/postwalk #(if (= %1 '__) result %1) exs))
]}]]



[[:chapter {:tag "intro" :title "Introduction"}]]


"Clojure est un langage de programmtion fonctionnel sur la JVM
 .. lors de cette introduction, nous allons  ... "


[[:chapter {:tag "do-thing" :title "Premiers pas"}]]


"ref : http://www.braveclojure.com/do-things/"


[[:section {:tag "syntax" :title "La syntaxe de Clojure"}]]

"La syntaxe de Clojure est simple. Comme tous les Lisps, il emploie une structure uniforme,
une poignée d'opérateurs spéciaux , et un approvisionnement constant de parenthèses livrés par
les mines de parenthèses cachés sous le Massachusetts Institute of Technology, où Lisp est né."

[[:subsection {:title "Les formes"}]]

"
Tout le code Clojure est écrit dans une structure uniforme. Clojure comprends :
1. les représentations litérales des structures de données comme les nombres, les strings, les maps et les vecteurs,
2. les opérations
"

"On utilise le terme forme pour désigner le code valide structurellement.
Les représentations suivantes sont des formes valides :"

(comment
  1
  "a string"
  ["a" "b" "c"])


(exercice
 "veuillez entrer une forme valide"
 [(= __ __)]
 'yolo)



"Votre code contiendra rarement des formes littérales flottantes,
car ils ne font rien de leur propre chef. Au lieu de cela,
vous allez utiliser des littéraux dans les opérations.
Les opérations sont la façon dont vous faites les choses.
Toutes les opérations prennent la forme, \"parenthèse ouvrante ,
opérateur, opérandes, parenthèse fermante\" :"

(comment (operator operand1 operand2 ... operandn))

"En comparaison, voici le code Java équivalent :"

[[{:lang "Java"}]]
[[:code "operator(operand1,operand2, ... , operandn);"]]

[[{:private [
   (defmacro contains-op? [form] (if (list? form) true false))
]}]]

(exercice
 "Veuillez entrer une forme qui contient un opérateur et qui évalue vers `2`."
 [(= __ 2)
  (contains-op? __)]
 (+ 1 1))


(exercice
 "this exercice is an introduction to ...."
 [(= __ true)]
 true)


(exercice
 "another equality"
 [(= __ (+ 1 1))]
 2)


(exercice
 "You can test equality of many things"
 [(= (+ 3 4) 7 (+ 2 __))]
 5)


(exercice
 "Simple math"
 [(= (- 10 (* 2 3)) __)]
 4)
