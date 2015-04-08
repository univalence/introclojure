(exercice
  "Vous pouvez créer un ensemble en convertissant une autre collection"
  [(= #{3} (set __))][3])

  (exercice
  "Compter, c'est comme compter les autres collections"
  [(= __ (count #{1 2 3}))]3)

    (exercice
  "Rappelez-vous qu'un set est un set * mathématique *"
  [(= __ (set '(1 1 2 2 3 3 4 4 5 5)))]#{1 2 3 4 5})

      (exercice
  "Vous pouvez demander à clojure l'union de deux ensembles"
  [(= __ (clojure.set/union #{1 2 3 4} #{2 3 5}))] #{1 2 3 4 5})

        (exercice
  "Et aussi l'intersection"
  [(= __ (clojure.set/intersection #{1 2 3 4} #{2 3 5}))] #{2 3})

          (exercice
  "Mais ne oubliez pas la différence"
  [(= __ (clojure.set/difference #{1 2 3 4 5} #{2 3 5}))]#{1 4})
