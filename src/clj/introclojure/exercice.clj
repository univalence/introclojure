(ns introclojure.exercice)



(def seq-id (atom 0))

(defn exercice-id [title exs solution]
	(str "exercice-" (swap! seq-id inc)))

