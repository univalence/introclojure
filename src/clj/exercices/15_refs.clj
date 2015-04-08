(def the-world (ref "hello"))
(def bizarro-world (ref {}))

(exercice
  "Au début, il y avait un mot"
  [(= __ (deref the-world))] "hello")


(exercice

  "Vous pouvez obtenir le mot le plus succinctement, mais c'est la même"
  [(= __ @the-world)] "hello")


(exercice

  "Vous pouvez être le changement que vous voulez voir dans le monde."
  [(= __ (do
           (dosync (ref-set the-world "better"))
           @the-world))] "better")

(exercice

  "Alter where you need not replace"
  [(= __ (let [exclamator (fn [x] (str x "!"))]
           (dosync
             (alter the-world exclamator)
             (alter the-world exclamator)
             (alter the-world exclamator))
           @the-world))] "better!!!")


(exercice

  "N'oubliez pas de faire votre travail dans une transaction!"
  [(= 0 (do __
          @the-world))] (dosync (ref-set the-world 0)))


(exercice

  "Les fonctions transférées à modifier peuvent dépendre les données dans le ref"
  [(= 20 (do
           (dosync (alter the-world ___))))] (map :jerry [@the-world @bizarro-world]))


(exercice

  "Deux mondes valent mieux qu'une"
  [(= ["Real Jerry" "Bizarro Jerry"]
     (do
       (dosync
         (ref-set the-world {})
         (alter the-world assoc :jerry "Real Jerry")
         (alter bizarro-world assoc :jerry "Bizarro Jerry")
         __)))] (fn [x] (+ 20 x)))
