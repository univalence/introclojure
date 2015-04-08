(exercice
  "Pour diviser une collection, vous pouvez utiliser la fonction de partition"
  [(= '((0 1) (2 3)) (__ 2 (range 4)))] partition)


(exercice

  "Mais attention, si il n'y a pas suffisamment d'éléments pour former la N séquences"
  [(= '(__) (partition 3 [:a :b :c :d :e]))] [:a :b :c])


(exercice

  "Vous pouvez utiliser la partition-all pour obtenir également des partitions avec moins de n éléments  "
  [(= __ (partition-all 3 (range 5)))] '((0 1 2) (3 4)))


(exercice

  "Si vous avez besoin, vous pouvez commencer chaque séquence avec un décalage"
  [(= '((0 1 2) (5 6 7) (10 11 12)) (partition 3 __ (range 13)))] 5)


(exercice

  "Considérez le padding comme étant la dernière séquence avec certaines valeurs par défaut ..."
  [(= '((0 1 2) (3 4 5) (6 :hello)) (partition 3 3 [__] (range 7)))] :hello)


(exercice
"...mais notez qu'il est limité par la taille de la séquence donnée"
  [(= '((0 1 2) (3 4 5) __) (partition 3 3 [:these :are "my" "words"] (range 7)))] (6 :these :are))
