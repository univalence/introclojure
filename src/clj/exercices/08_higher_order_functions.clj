(exercice
  "La fonction map transforme une séquence en une autre"
  [(= [__ __ __] (map (fn [x] (* 4 x)) [1 2 3]))] 4 8 12)


(exercice
  "Vous pouvez créer votre mapping"
  [(= [1 4 9 16 25] (map (fn [x] __) [1 2 3 4 5]))] (* x x))


(exercice

  "Ou utiliser les noms de fonctions existantes"
  [(= __ (map nil? [:a :b nil :c :d]))] [false false true false false])


(exercice

  "Un filtre peut être forte"
  [(= __ (filter (fn [x] false) '(:anything :goes :here)))] ())


(exercice

  "Ou très faible"
  [(= __ (filter (fn [x] true) '(:anything :goes :here)))] [:anything :goes :here])


(exercice

  "Ou quelque part entre les deux"
  [(= [10 20 30] (filter (fn [x] __) [10 20 30 40 50 60 70 80]))] (< x 31))



(exercice

  "Maps et filters peuvent être combinées"
  [(= [10 20 30] (map (fn [x] __) (filter (fn [x] __) [1 2 3 4 5 6 7 8])))] (* 10 x) (< x 4))


(exercice

  "Réduire peut augmenter le résultat"
  [(= __ (reduce (fn [a b] (* a b)) [1 2 3 4]))] 24)


(exercice

  "Vous pouvez commencer quelque part ailleurs"
  [(= 2400 (reduce (fn [a b] (* a b)) __ [1 2 3 4]))] 100)


(exercice

  "Les chiffres ne sont pas les seules choses que l'on peut réduire"
  [(= "longest" (reduce (fn [a b]
                          (if (< __ __) b a))
                  ["which" "word" "is" "longest"])) (* 10 x) (< x 4)] (count a) (count b))
