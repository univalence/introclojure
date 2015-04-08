//2_lits

(exercice
  "Les listes peuvent être exprimées par fonction ou une forme cité"
  [(= '(__ __ __ __ __) (list 1 2 3 4 5))]1 2 3 4 5)

(exercice

  "On peut accèder au premier élément..."
  [(= __ (first '(1 2 3 4 5)))]1)


(exercice
  "Ainsi que le reste"
  [(= __ (rest '(1 2 3 4 5)))][2 3 4 5])


(exercice
  "Comptez vos bénédictions"
  [(= __ (count '(dracula dooku chocula)))]3)


(exercice
  "Avant qu'ils ne disparaissent"
  [(= __ (count '()))]0)


(exercice

  "Le reste, quand rien n'est laissé, est vide"
  [(= __ (rest '(100)))]())


(exercice

  "Construction en ajoutant un élément à l'avant est facile"
  [(= __ (cons :a '(:b :c :d :e)))][:a :b :c :d :e])


(exercice

  "Conjoindre un élément à une liste ne est pas difficile non plus"
  [(= __ (conj '(:a :b :c :d) :e))] [:e :a :b :c :d])


(exercice

  "Vous pouvez utiliser une liste comme une pile pour obtenir le premier élément"
  [(= __ (peek '(:a :b :c :d :e)))]:a)


(exercice

  "Ou les autres"
  [(= __ (pop '(:a :b :c :d :e)))][:b :c :d :e])


(exercice

  "Mais attention si vous essayez de rien pop"
  [(= __ (try
           (pop '())
           (catch IllegalStateException e
             "No dice!")))]"No dice!")


(exercice

  "Le reste de rien n'est pas si stricte"
  (= __ (try
          (rest '())
          (catch IllegalStateException e
            "No dice!"))) ())
