(exercice
  "Compréhensions de séquences peuvent lier chaque élément à son tour à un symbole"
  [(= __
     (for [x (range 6)]
       x))] [0 1 2 3 4 5])

(exercice

  "Ils peuvent facilement émuler l'utilisation d'un map"
  [(= '(0 1 4 9 16 25)
     (map (fn [x] (* x x))
       (range 6))
     (for [x (range 6)]
       __))] (* x x))

(exercice

  "Et aussi l'utilisation d'un filte"
  [(= '(1 3 5 7 9)
     (filter odd? (range 10))
     (for [x __ :when (odd? x)]
       x))] (range 10))

(exercice

  "Les combinaisons de ces transformations est trivial"
  [(= '(1 9 25 49 81)
     (map (fn [x] (* x x))
       (filter odd? (range 10)))
     (for [x (range 10) :when __]
       __))] (odd? x) (* x x))

(exercice

  "Les transformations plus complexes prennent simplement des formes multiples de liaison"
  [(= [[:top :left] [:top :middle] [:top :right]
       [:middle :left] [:middle :middle] [:middle :right]
       [:bottom :left] [:bottom :middle] [:bottom :right]]
     (for [row [:top :middle :bottom]
           column [:left :middle :right]]
       __))] [row column])
