(exercice
  "Il ya plusieurs façons de générer une séquence"
  [(= __ (range 1 5))] [1 2 3 4])


(exercice

  "La gamme commence au début par défaut"
  [(= __ (range 5))] [0 1 2 3 4])


(exercice

  "Prenez uniquement ce que vous avez besoin quand la séquence est importante"
  [(= [0 1 2 3 4 5 6 7 8 9]
     (take __ (range 100)))] 10)


(exercice

  "Ou limiter les résultats en laissant tomber ce que vous ne avez pas besoin"
  [(= [95 96 97 98 99]
     (drop __ (range 100)))] 95)


(exercice

  "L'itération fournit une séquence paresseux infinie"
  [(= __ (take 20 (iterate inc 0)))] (range 20))


(exercice

  "Répétition est la clé"
  [(= [:a :a :a :a :a :a :a :a :a :a]
     (repeat 10 __))] :a)


(exercice

  "L'itération peut être utilisé pour la répétition"
  [(= (repeat 100 :foo)
     (take 100 (iterate ___ :foo)))] (fn [x] x))
