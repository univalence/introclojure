

(defn explain-defcon-level [exercise-term]
  (case exercise-term
        :fade-out          :you-and-what-army
        :double-take       :call-me-when-its-important
        :round-house       :o-rly
        :fast-pace         :thats-pretty-bad
        :cocked-pistol     :sirens
        :say-what?))

(exercice
  "Vous ferez face à de nombreuses décisions"
 [ (= __ (if (false? (= 4 5))
          :a
          :b))] :a)


  (exercice
  "Certains d'entre eux vous laisse pas d'alternative"
  [(= __ (if (> 4 3)
          []))] [])


    (exercice
  "Et dans une telle situation, vous pouvez ne rien avoir"
  [(= __ (if (nil? 0)
          [:a :b :c]))]nil)


      (exercice
  "Dans d'autres cas  votre alternative peut être intéressant"
  [(= :glory (if (not (empty? ()))
              :doom
              __))] :glory)


        (exercice
  "Vous pouvez avoir une multitude de chemins possibles"
  [(let [x 5]
    (= :your-road (cond (= x __) :road-not-taken
                        (= x __) :another-road-not-taken
                        :else __)))] 4 6 :your-road)


          (exercice
  "Ou votre destin peut être scellé"
  [(= 'doom (if-not (zero? __)
          'doom
          'more-doom))]1)


            (exercice
  "En cas d'urgence,sonorez les alarmes "
 [ (= :sirens
     (explain-defcon-level __))]:cocked-pistol)


              (exercice
  "Mais l'admettre quand vous ne savez pas quoi faire"
  [(= __
     (explain-defcon-level :yo-mama))] :say-what?)
