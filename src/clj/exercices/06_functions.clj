(defn multiply-by-ten [n]
  (* 10 n))

(defn square [n] (* n n))

(exercice
  "Appel d'une fonction est comme donner une accolade avec des parenthèses"
  [(= __ (square 9))] 81)


(exercice
  "Les fonctions sont généralement définis avant qu'ils ne soient utilisés"
  [(= __ (multiply-by-ten 2))] 20)


(exercice
  "Mais ils peuvent également être définies en ligne"
  [(= __ ((fn [n] (* 5 n)) 2))] 10)


(exercice
  "Ou en utilisant une syntaxe encore plus court"
  [(= __ (#(* 15 %) 4))] 60)


(exercice
  "Même les fonctions anonymes peuvent prendre plusieurs arguments"
  [(= __ (#(+ %1 %2 %3) 4 5 6))] 15)


(exercice
  "Les arguments peuvent également être ignorés"
  [(= __ (#(* 15 %2) 1 2))] 30)


(exercice
  "Une fonction peut engendrer une autre"
  [(= 9 (((fn [] ___)) 4 5))] +)


(exercice
  "Les fonctions peuvent aussi prendre d'autres fonctions comme entrée"
  [(= 20 ((fn [f] (f 4 5))
           ___))] *)


(exercice
  "Fonctions d'ordre supérieur prennent des arguments de fonction"
  [(= 25 (___
           (fn [n] (* n n))))] (fn [f] (f 5)))


(exercice
  "Mais ils sont souvent mieux écrite en utilisant les noms de fonctions"
  [(= 25 (___ square))] (fn [f] (f 5)))
