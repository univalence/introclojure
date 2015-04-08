(exercice
  "Vous pouvez utiliser des vecteurs dans clojure comme des structures de tableau "
  [(= __ (count [42]))]1)

  (exercice
  "Vous pouvez créer un vecteur à partir d'une liste"
  [(= __ (vec '(1)))][1])

    (exercice
  "Ou à partir de certains éléments"
  [(= __ (vector nil nil))][nil nil])

      (exercice
  "Mais vous pouvez le remplir avec n'importe quel nombre d'éléments"
  [(= [1 __] (vec '(1 2)))] 2)

      (exercice
  "Conjoindre à un vecteur est différent que de conjoindre à une liste"
  [(= __ (conj [111 222] 333))][111 222 333])

      (exercice
  "Vous pouvez obtenir le premier élément d'un vecteur comme ça"
 [(= __ (first [:peanut :butter :and :jelly]))]:peanut)


(exercice  "Et le dernier d'une façon similaire"
  [(= __ (last [:peanut :butter :and :jelly]))]:jelly)

      (exercice
  "Ou n'importe quel indice si vous le souhaitez"
  [(= __ (nth [:peanut :butter :and :jelly] 3))]:jelly)

      (exercice
  "Vous pouvez également découper un vecteur"
  [(= __ (subvec [:peanut :butter :and :jelly] 1 3))] [:butter :and])

      (exercice
  "L'égalité  des collections est une question de valeurs"
  [(= (list 1 2 3) (vector 1 2 __))]3)
