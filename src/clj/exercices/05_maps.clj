(exercice
  "Ne pas se perdre lors de la création d'une map"
  [(= {:a 1 :b 2} (hash-map :a 1 __ __))] :b 2)


(exercice
  "Une valeur doit être fournie pour chaque clé"
  [(= {:a 1} (hash-map :a __))] 1)


(exercice
  "La taille est le nombre d'entrées"
  [(= __ (count {:a 1 :b 2}))] 2)

(exercice
  "Vous pouvez rechercher la valeur pour une clé donnée"
  [(= __ (get {:a 1 :b 2} :b))] 2)


(exercice
  "Maps peut êtres utilisé comme une functions pour faire des lookups"
  [(= __ ({:a 1 :b 2} :a))] 1)


(exercice
  "Et peut être utilisé comme un mot clés"
  [(= __ (:a {:a 1 :b 2}))] 1)


(exercice
  "Mais les clés d'une map ne sont pas nécessairement des mots clés"
  [(= __ ({2010 "Vancouver" 2014 "Sochi" 2018 "PyeongChang"} 2014))] "Sochi")


(exercice
  "Vous ne pouvez pas être en mesure de trouver une entrée pour une clé"
  [(= __ (get {:a 1 :b 2} :c))] nil)


(exercice
  "Mais vous pouvez fournir votre propre valeur par défaut"
  [(= __ (get {:a 1 :b 2} :c :key-not-found))] :key-not-found)


(exercice
  "Vous pouvez savoir si une clé est présente"
  [(= __ (contains? {:a nil :b nil} :b))] true)


(exercice
  "Ou si elle est absente"
  [(= __ (contains? {:a nil :b nil} :c))] false)


(exercice
  "Les maps sont immuables, mais vous pouvez créer une nouvelle version améliorée"
  [(= {1 "January" 2 __} (assoc {1 "January"} 2 "February"))] "February")


(exercice
  "Vous pouvez également créer une nouvelle version avec une entrée enlevé"
  [(= {__ __} (dissoc {1 "January" 2 "February"} 2))] 1 "January")


(exercice
  "Souvent, vous devrez récupérer les clés, mais l'ordre est peu fiable"
  [(= (list __ __ __)
     (sort (keys {2014 "Sochi" 2018 "PyeongChang" 2010 "Vancouver"})))] 2006 2010 2014)


(exercice
  "Vous pouvez obtenir les valeurs de la même manière"
  [(= (list __ __ __)
     (sort (vals {2010 "Vancouver" 2014 "Sochi" 2018 "PyeongChang"})))] "PyeongChang" "Sochi" "Vancouver")


