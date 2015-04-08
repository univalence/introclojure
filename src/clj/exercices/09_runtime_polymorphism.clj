(defn hello
  ([] "Hello World!")
  ([a] (str "Hello, you silly " a "."))
  ([a & more] (str "Hello to this group: "
                (apply str
                  (interpose ", " (cons a more)))
                "!")))

(defmulti diet (fn [x] (:eater x)))
(defmethod diet :herbivore [a] __)
(defmethod diet :carnivore [a] __)
(defmethod diet :default [a] __)

(exercice
  "Certaines fonctions peuvent être utilisés de différentes manières - sans arguments"
  [(= __ (hello))] "Hello World!")


(exercice

  "Avec un argument"
  [(= __ (hello "world"))] "Hello, you silly world."
  )


(exercice

  "Avec un argument"
  [(= __
     (hello "Peter" "Paul" "Mary"))] "Hello to this group: Peter, Paul, Mary!")


(exercice

  "Les Multiméthodes permettent un dispatching plus complexe"
  [(= "Bambi eats veggies."
     (diet {:species "deer" :name "Bambi" :age 1 :eater :herbivore}))] (str (:name a) " eats veggies."))


(exercice

  "Les animaux ont des noms différents"
  [(= "Thumper eats veggies."
     (diet {:species "rabbit" :name "Thumper" :age 1 :eater :herbivore}))] (str (:name a) " eats veggies."))


(exercice

  "Différentes méthodes sont utilisées en fonction du résultat de la fonction de répartition"
  [(= "Simba eats animals."
     (diet {:species "lion" :name "Simba" :age 1 :eater :carnivore}))] (str (:name a) " eats animals."))


(exercice

  "Vous pouvez utiliser une méthode par défaut en l'absence d'autres correspondent"
  [(= "I don't know what Rich Hickey eats."
     (diet {:name "Rich Hickey"}))] (str "I don't know what " (:name a) " eats."))
