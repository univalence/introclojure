(def atomic-clock (atom 0))

(exercice
  "Les Atoms sont comme refs"
  [(= __ @atomic-clock)] 0)


(exercice

  "Vous pouvez modifier lors de la permutation"
  [(= __ (do
           (swap! atomic-clock inc)
           @atomic-clock))] 1)


(exercice

  "Maintenir les impôts sur ceci: échange nécessite pas de transaction"
  [(= 5 (do
          __
          @atomic-clock))] (swap! atomic-clock (partial + 4)))


(exercice

  "N'importe quel nombre d'arguments pourrait se produire lors d'une permutation"
  [(= __ (do
           (swap! atomic-clock + 1 2 3 4 5)
           @atomic-clock))] 20)


(exercice

  "Atomes atomiques sont atomiques"
  [(= __ (do
           (compare-and-set! atomic-clock 100 :fin)
           @atomic-clock))] 20)


(exercice

  "Lorsque vos attentes sont alignés avec la réalité, les choses se passent de cette façon"
  [(= :fin (do
             (compare-and-set! __ __ __)
             @atomic-clock))] atomic-clock 20 :fin)
