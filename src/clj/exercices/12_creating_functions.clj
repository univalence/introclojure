(defn square [x] (* x x))

(exercice
  "On peut savoir ce qu'ils cherchent en sachant ce qu'ils ne cherchent pas"
  [(= [__ __ __] (let [not-a-symbol? (complement symbol?)]
                   (map not-a-symbol? [:a 'b "c"])))] true false true)


(exercice

  "Louange et «complément» peuvent vous aider à séparer le bon grain de l'ivraie"
  [(= [:wheat "wheat" 'wheat]
     (let [not-nil? ___]
       (filter not-nil? [nil :wheat nil "wheat" nil 'wheat nil])))] 4)


(exercice

  "Les fonctions partielles permettent la procrastination"
  [(= 20 (let [multiply-by-5 (partial * 5)]
           (___ __)))] :a :b :c :d)


(exercice

  "N'oubliez pas: premières choses d'abord"
  [(= [__ __ __ __]
     (let [ab-adder (partial concat [:a :b])]
       (ab-adder [__ __])))] :c :d)


(exercice

  "Les fonctions peuvent unir leurs forces en une fonction 'composé'"
  [(= 25 (let [inc-and-square (comp square inc)]
           (inc-and-square __)))] 4)


(exercice

  "Laissez vous tenter par un double dec-er"
  [(= __ (let [double-dec (comp dec dec)]
           (double-dec 10)))] 8)


(exercice

  "Soyez prudent sur l'ordre dans lequel vous mélangez vos fonctions"
  [(= 99 (let [square-and-dec ___]
           (square-and-dec 10)))] (complement nil?)
  multiply-by-5
  (comp dec square))
