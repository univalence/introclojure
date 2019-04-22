(ns workshop.do-things)

[[{:private [
              (require 'clojure.walk)

              (defmacro exercice [doc exs result]
                (clojure.walk/postwalk #(if (= %1 '__) result %1) exs))
              ]}]]


[[:chapter {:tag "introduction" :title "Introduction"}]]


"Yeah !"


(exercice
  "Ceci est un REPL n'hésitez pas à tester les exemples ici ! "
  [] [])
;(exercice
;  "Ceci est un REPL n'hésitez pas à tester les exemples ici ! "
;  [] [])

;"Faire des choses: un cours Clojure crash

;Il est temps de savoir comment effectivement faire des choses avec Clojure! Hot Damn!
;"






[[:chapter {:tag "syntaxe" :title "Syntaxe"}]]


"La syntaxe de Clojure est simple. Comme tous les Lisps, il emploie une structure uniforme, une poignée d'opérateurs spéciaux, et un approvisionnement constant de parenthèses livrées par les mines de parenthèses, cachées sous le Massachusetts Institute of Technology, où Lisp est né."


[[:section {:tag "forms" :title "Forms"}]]


"Tout code Clojure est écrit dans une structure uniforme. Clojure comprend :

* Des représentations littérales de structures de données comme les nombres, strings, maps, et vecteurs
* Des opérations

Nous utilisons le terme **form** pour se référer au code structurellement valide. Ces représentations littérales sont toutes des forms valides:

"
(comment
  "1
" a string "
[" a " " vector " " of " " strings "]"

  )

"Votre code contiendra rarement des littéraux flottants, bien sûr, car ils ne font rien de leur propre chef. Au lieu de cela, vous allez utiliser des littéraux dans les opérations.
Les opérations sont la façon dont vous *faites les choses*. Toutes les opérations prennent la forme, \"parenthèse ouverte, opérateur, opérandes, parenthèse fermante\":"
(comment

  (operator operand1 operand2 ... operandn)
  )

"Notez qu'il n'y a pas de virgules. Clojure utilise des espaces pour séparer les opérandes et il traite les virgules comme des espaces. Voici quelques examples d'opérations:"
(comment

  (+ 1 2 3)
  ; => 6

  (str "It was the panda " "in the library " "with a dust buster")
  ; => "It was the panda in the library with a dust buster"
  )


"En résumé, Clojure est constitué de **form**. Les **forms** ont une structure uniforme. Ils se composent de littéraux et d'opérations.
Les opérations sont des forms entourés de parenthèses.

Pour faire bonne mesure, voici quelque chose qui n'est pas un form parce qu'il n'a pas de parenthèse fermante:"


[[{:lang "Clojure"}]]
[[:code "
(+
"]]

"L'uniformité structurelle de Clojure est probablement différente de ce à quoi vous êtes habitué.
Dans d'autres langages, différentes opérations peuvent avoir différentes structures en fonction de l'opérateur et des opérandes.
Par exemple, JavaScript emploie un assortiment de notation infix, d'opérateurs points, et des parenthèses:"

(comment

  1 + 2 + 3
  "It was the panda " .concat ("in the library ", "with a dust buster")
  )

"La structure de Clojure est très simple et cohérente en comparaison.
 Peu importe l'opérateur utilisé ou le type de données que vous exploitez, la structure est la même.

Une dernière remarque: nous utiliserons aussi le terme **expression** pour désigner les forms Clojure. Cependant, ne soyez pas trop rigide sur la terminologie."


[[:section {:tag "flow_control" :title "Flow Control"}]]

"Voici quelques opérateurs basique de contrôle. Vous en rencontrerez plus tout au long du workshop."


[[:subsection {:tag "if" :title "if"}]]

"La structure générale *if* est :"

(comment
  (if boolean-form
    then-form
    optional-else-form)
  )

"Voici un exemple :"

(comment
  (if true
    "abra cadabra"
    "hocus pocus")
  ; => "abra cadabra"
  )


"Notez que chaque branche d'un *if* ne peut avoir qu'un form. Ceci est différent de la plupart des langages, par exemple, en Ruby vous pouvez écrire :"

(comment

  if true
  doer.do_thing (1)
  doer.do_thing (2)
  else
  other_doer.do_thing (1)
  other_doer.do_thing (2)
  end
  )


"Pour contourner cette limitation, nous avons l'opérateur *do* :"

(exercice
  "Vous ferez face à de nombreuses décisions"
  [ (= __ (if (false? (= 4 5))
            :a
            :b))] :a)


(exercice
  "Certaines d'entre elles ne vous laissent pas d'alternative"
  [(= __ (if (> 4 3)
           []))] [])


(exercice
  "Et dans une telle situation, vous pouvez ne rien avoir"
  [(= __ (if (nil? 0)
           [:a :b :c]))] nil)


(exercice
  "Dans d'autres cas, votre alternative peut être intéressante"
  [(= :glory (if (not (empty? ()))
               :doom
               __))] :glory)


(exercice
  "Ou votre destin peut être scellé"
  [(= 'doom (if-not (zero? __)
              'doom
              'more-doom))]1)

(defn explain-defcon-level [exercise-term]
  (case exercise-term
    :fade-out          :you-and-what-army
    :double-take       :call-me-when-its-important
    :round-house       :o-rly
    :fast-pace         :thats-pretty-bad
    :cocked-pistol     :sirens
    :say-what?))

""


(exercice
  "En cas d'urgence, sonnez les alarmes"
  [ (= :sirens
      (explain-defcon-level __))]:cocked-pistol)


(exercice
  "Mais admettez le quand vous ne savez pas quoi faire"
  [(= __
     (explain-defcon-level :yo-mama))] :say-what?)


[[:subsection {:tag "do" :title "do"}]]

"*do* vous permet d'«envelopper» plusieurs forms. Essayez ce qui suit dans votre REPL:"

(comment
  (if true
    (do (println "Success!")
      "abra cadabra")
    (do (println "Failure :(")
      "hocus pocus"))
  ; => Success!
  ; => "abra cadabra"
  )

"
Dans ce cas, *Success!* est imprimé dans le REPL et \"abra cadabra\" est retourné comme la valeur de l'entière expression if.
"

[[:subsection {:tag "when" :title "when"}]]
(comment

  (when true
    (println "Success!")
    "abra cadabra")
  ; => Success!
  ; => "abra cadabra"
  )


"Utilisez *when* lorsque vous voulez faire plusieurs choses quand une certaine condition est vraie et que vous voulez toujours retourner nil lorsque la condition est fausse.

Cela couvre les opérateurs de contrôle essentiels !"



[[:section {:tag "def" :title "Nommer les choses avec def"}]]

"
Une dernière chose avant de passer aux structures de données : utilisez *def* pour *lier* un *nom* à une *valeur* dans Clojure :
"

(comment
  (def failed-protagonist-names
    ["Larry Potter"
     "Doreen the Explorer"
     "The Incredible Bulk"])
  )
"
Dans ce cas, vous liez le nom *failed-protagonist-names* à un vecteur contenant trois strings. Remarquez que j'utilise le terme «lier», alors que dans d'autres langages vous diriez que vous *attribuez* une valeur à une *variable*. Par exemple, en Ruby, vous pouvez effectuer plusieurs affectations à une variable afin de «construire» sa valeur :
"
(comment

  severity = :mild error_message = "OH GOD! IT'S A DISASTER! WE'RE "
  if severity == :mild error_message = error_message + "MILDLY INCONVENIENCED!"
  else
  error_message = error_message + "DOOOOOOOMED!"
  end
  )

"L'équivalent en Clojure serait :"

(comment
  (def severity :mild)
  (def error-message "OH GOD! IT'S A DISASTER! WE'RE ")
  (if (= severity :mild)
    (def error-message (str error-message "MILDLY INCONVENIENCED!"))
    (def error-message (str error-message "DOOOOOOOMED!")))
  )


"Cependant, c'est vraiment une mauvaise pratique en Clojure. Pour l'instant, vous devez traiter def comme si c'était une définition de constantes. Mais n'ayez pas peur! Au cours des prochains chapitres, vous apprendrez comment travailler avec cette limitation apparente en codant dans le style fonctionnel."

[[:chapter {:tag "data_structure" :title "Structures de données"}]]


"Clojure est livré avec une poignée de structures de données que vous découvririez seul car elles sont utilisées la majorité du temps. Si vous venez d'un milieu orienté objet, vous serez surpris de voir combien de choses vous pouvez faire avec les types \" de base \" présentés ici.

Toutes les structures de données Clojure sont immuables, ce qui signifie que vous ne pouvez pas les modifier. Il n'y a pas d'équivalent Clojure pour le code Ruby suivant :"
(comment


  failed_protagonist_names = [
                               "Larry Potter",
                               "Doreen the Explorer",
                               "The Incredible Bulk"
                               ]
  failed_protagonist_names [0] = "Gary Potter"
  failed_protagonist_names
  ;# =>
  [
        #"Gary Potter",
        #"Doreen the Explorer",
        #"The Incredible Bulk"]
        )

"Plus loin, vous en apprendrez plus sur pourquoi Clojure a été implémenté de cette façon. Pour l'instant, il est plus amusant d'apprendre à faire des choses sans ce préoccuper de cela. Commençons donc sans plus tarder :"


[[:section {:tags "nil_true_false_truthiness_equality" :title "nil, true, false, Equality"}]]

"Clojure possède les valeurs true et false. nil est utilisé pour indiquer \"aucune valeur\" dans Clojure. Vous pouvez vérifier si une valeur est nil avec la fonction nil? :"

(comment
  (nil? 1)
  ; => false

  (nil? nil)
  ; => true
  )

"nil et false sont utilisés pour représenter la logique fausse, alors que toutes les autres valeurs sont logiquement vraies. = est l'opérateur d'égalité :"

(comment
  (= 1 1)
  ; => true

  (= nil nil)
  ; => true

  (= 1 2)
  ; => false
  )


"Certains langages vous obligent à utiliser différents opérateurs lorsque vous comparez les valeurs de différents types. Par exemple, vous seriez forcé d'utiliser un opérateur spécial \"égalité de String\" pour les chaînes de caractère.
Vous n'aurez pas besoin de quelque chose de bizarre ou pénible comme ça pour tester l'égalité grâce aux structures de données intégrées de Clojure."

[[:section {:tags "nombres" :title "Nombres"}]]

"Clojure possède un support assez sophistiqué pour les nombres. Nous n'allons pas nous attarder sur les détails techniques ennuyeux (comme la coercition et de la contagion).
 Si vous êtes intéressé par ces détails ennuyeux, consultez http://clojure.org/data_structures#Data. Autant dire que Clojure va allègrement gérer à peu près tout ce que vous lui mettez."

"Pendant ce temps, nous allons travailler avec des nombres entiers et flottants. Nous allons aussi travailler avec des ratios que Clojure peut représenter directement. Voici respectivement un nombre entier, un flottant, et un ratio :"

(comment

  93
  1.2
  1/5
  )

[[:section {:tags "strings" :title "Strings"}]]

"Voici quelques exemples de chaînes de caractère:"

(comment
  "Lord Voldemort"
  "\"He who must not be named\""
  "\"Great cow of Moscow!\" - Hermes Conrad"
  )


"Notez que Clojure ne permet pas les guillemets pour délimiter les chaînes. 'Lord Voldemort', par exemple, n'est pas une chaîne valide. Notez également que Clojure n'a pas d'interpolation de chaînes. Clojure ne permet pas la concaténation via la fonction str :"

[[:section {:tags "map" :title "Maps"}]]

"Les maps sont semblables à des dictionnaires dans d'autres langages. Voici quelques exemples de map :"

(comment

  ;; An empty map
  {}

  ;; ":a", ":b", ":c" are keywords and we'll cover them in the next section
  {:a 1
   :b "boring example"
   :c []}

  ;; Associate "string-key" with the "plus" function
  {"string-key" +}

  ;; Maps can be nested
  {:name {:first "John" :middle "Jacob" :last "Jingleheimerschmidt"}}
  )

"Notez que les valeurs d'une map peuvent être de tout type. Chaîne, nombre, map, vecteur, et même des fonctions !

Vous pouvez rechercher des valeurs dans les Maps avec la fonction *get* :"

(comment

  (get {:a 0 :b 1} :b)
  ; => 1

  (get {:a 0 :b {:c "ho hum"}} :b)
  ; => {:c "ho hum"}
  )


"*get* retournera **nil** si elle ne trouve pas votre clé, mais vous pouvez lui donner une valeur par défaut :"

(comment

  (get {:a 0 :b 1} :c)
  ; => nil

  (get {:a 0 :b 1} :c "UNICORNS")
  ; => "UNICORNS"
  )


"La fonction get-in vous permet de rechercher des valeurs dans les maps imbriquées :"
(comment
  (get-in {:a 0 :b {:c "ho hum"}} [:b :c])
  ; => "ho hum"
  )


"[:b :c] est un vecteur, que vous lisez dans une minute.

Une autre façon de rechercher une valeur dans une map est de traiter la map comme une fonction, avec la clé comme argument :"


(comment
  ({:name "The Human Coffee Pot"} :name)
  ; => "The Human Coffee Pot"
  )

"Les Clojuristes ne feront presque jamais cela. Cependant, les Clojuristes utilisent des mots-clés pour rechercher les valeurs dans les maps :
"


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
  "Maps peut être utilisé comme une function pour faire des lookups"
  [(= __ ({:a 1 :b 2} :a))] 1)


(exercice
  "Et peut être utilisé comme un mot clé"
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







[[:section {:tags "keywords" :title "Mots-clés"}]]

"La notion de mots-clés dans Clojure est plus compréhensible lorsqu'on les utilise. Ils sont principalement utilisés comme clés dans les maps, comme vous l'avez vu ci-dessus. Exemples de mots-clés :"


(comment
  :a
  :rumplestiltsken
  :34
  :_?
  )

"Les mots-clés peuvent être utilisés comme des fonctions trouvant la valeur correspondante dans une structure de données. Par exemple :"


(comment

  ;; Look up :a in map
  (:a {:a 1 :b 2 :c 3})
  ; => 1

  ;; This is equivalent to:
  (get {:a 1 :b 2 :c 3} :a)
  ; => 1

  ;; Provide a default value, just like get:
  (:d {:a 1 :b 2 :c 3} "FAERIES")
  ; => "FAERIES"
  )

"Outre l'utilisation des littéraux map, vous pouvez utiliser la fonction hash-map pour créer une map:"

(comment
  (hash-map :a 1 :b 2)
; => {:a 1 :b 2}
)

"Clojure vous permet également de créer des maps triées."

[[:section {:tags "vectors" :title "Vecteurs"}]]


"Un vecteur est similaire à un tableau, c'est une collection dont le premier élément a pour index 0 :"

(comment
  ;; Here's a vector literal
  [3 2 1]

  ;; Here we're returning an element of a vector
  (get [3 2 1] 0)
  ; => 3

  ;; Another example of getting by index. Notice as well that vector
  ;; elements can be of any type and you can mix types.
  (get ["a" {:name "Pugsley Winterbottom"} "c"] 1)
  ; => {:name "Pugsley Winterbottom"}
  )

"Notez que nous utilisons la même fonction get que nous utilisons lors de la recherche des valeurs dans les maps. Le chapitre suivant explique pourquoi nous faisons cela.

Vous pouvez créer des vecteurs avec la fonction vector :"


(comment
  (vector "creepy" "full" "moon")
  ; => ["creepy" "full" "moon"]
  )

"Les éléments sont ajoutés à la fin d'un vecteur :"

(comment
  (conj [1 2 3] 4)
  ; => [1 2 3 4]
  )


(exercice
  "Vous pouvez utiliser des vecteurs dans Clojure comme des structures de tableaux"
  [(= __ (count [42]))]1)

(exercice
  "Vous pouvez créer un vecteur à partir d'une liste"
  [(= __ (vec '(1)))][1])

(exercice
  "Ou à partir de certains éléments"
  [(= __ (vector nil nil))][nil nil])

(exercice
  "Mais vous pouvez le remplir avec n'importe quel nombre d'éléments"
  [(= [1 __] (vec '(1 2)))] 2)

(exercice
  "Conjoindre à un vecteur est différent que de conjoindre à une liste"
  [(= __ (conj [111 222] 333))][111 222 333])

(exercice
  "Vous pouvez obtenir le premier élément d'un vecteur comme ça"
  [(= __ (first [:peanut :butter :and :jelly]))]:peanut)


(exercice  "Et le dernier d'une façon similaire"
  [(= __ (last [:peanut :butter :and :jelly]))]:jelly)

(exercice
  "Ou n'importe quel indice si vous le souhaitez"
  [(= __ (nth [:peanut :butter :and :jelly] 3))]:jelly)

(exercice
  "Vous pouvez également découper un vecteur"
  [(= __ (subvec [:peanut :butter :and :jelly] 1 3))] [:butter :and])

(exercice
  "L'égalité des collections est une question de valeurs"
  [(= (list 1 2 3) (vector 1 2 __))]3)



[[:section {:tags "listes" :title "Listes"}]]


"Les listes sont similaires aux vecteurs sur certains points. Cependant, vous ne pouvez pas récupérer les éléments d'une liste avec get :"


(comment

  ;; Here's a list - note the preceding single quote
  '(1 2 3 4)
  ; => (1 2 3 4)
  ;; Notice that the REPL prints the list without a quote. This is OK,
  ;; and it'll be explained later.


  ;; Doesn't work for lists
  (get '(100 200 300 400) 0)

  ;; This works but has different performance characteristics which we
  ;; don't care about right now.
  (nth '(100 200 300 400) 3)
  ; => 400
  )


"Vous pouvez créer des listes avec la fonction list :"

(comment

  (list 1 2 3 4)
; => (1 2 3 4)
)

"Les éléments sont ajoutés au début de la liste :"

(comment

  (Conj '(1 2 3 4))
;  => (4 1 2 3)
)




(exercice

  "On peut accèder au premier élément..."
  [(= __ (first '(1 2 3 4 5)))]1)


(exercice
  "Ainsi que le reste"
  [(= __ (rest '(1 2 3 4 5)))][2 3 4 5])


(exercice
  "Comptez vos bénédictions"
  [(= __ (count '(dracula dooku chocula)))]3)


(exercice
  "Avant qu'elles ne disparaissent"
  [(= __ (count '()))]0)


(exercice

  "Le reste, quand rien n'est laissé, est vide"
  [(= __ (rest '(100)))]())


(exercice

  "Construire en ajoutant un élément en premier est facile"
  [(= __ (cons :a '(:b :c :d :e)))][:a :b :c :d :e])


(exercice

  "Conjoindre un élément à une liste n'est pas difficile non plus"
  [(= __ (conj '(:a :b :c :d) :e))] [:e :a :b :c :d])


(exercice

  "Vous pouvez utiliser une liste comme une pile pour obtenir le premier élément"
  [(= __ (peek '(:a :b :c :d :e)))]:a)


(exercice

  "Ou les autres"
  [(= __ (pop '(:a :b :c :d :e)))][:b :c :d :e])


(exercice

  "Mais attention si vous essayez de ne rien pop"
  [(= __ (try
           (pop '())
           (catch IllegalStateException e
             "No dice!")))]"No dice!")


(exercice

  "Le reste de rien n'est pas si strict"
  [(= __ (try
          (rest '())
          (catch IllegalStateException e
            "No dice!")))] ())

(exercice
  "Pour diviser une collection, vous pouvez utiliser la fonction de partition"
  [(= '((0 1) (2 3)) (__ 2 (range 4)))] partition)


(exercice

  "Mais attention, s'il n'y a pas suffisamment d'éléments pour former la N séquences"
  [(= '(__) (partition 3 [:a :b :c :d :e]))] [:a :b :c])


(exercice

  "Vous pouvez utiliser partition-all pour obtenir également des partitions avec moins de n éléments"
  [(= __ (partition-all 3 (range 5)))] '((0 1 2) (3 4)))


(exercice

  "Si vous avez besoin, vous pouvez commencer chaque séquence avec un décalage"
  [(= '((0 1 2) (5 6 7) (10 11 12)) (partition 3 __ (range 13)))] 5)


(exercice

  "Considérez le padding comme étant la dernière séquence avec certaines valeurs par défaut..."
  [(= '((0 1 2) (3 4 5) (6 :hello)) (partition 3 3 [__] (range 7)))] :hello)


(exercice
  "...mais notez qu'il est limité par la taille de la séquence donnée"
  [(= '((0 1 2) (3 4 5) __) (partition 3 3 [:these :are "my" "words"] (range 7)))] (6 :these :are))



[[:section {:tags "sets" :title "Sets"}]]

"Les sets sont des collections de valeurs uniques :"

(comment

  ;; Literal notation
    #{"hannah montana" "miley cyrus" 20 45}

  ;; If you try to add :b to a set which already contains :b,
  ;; the set still only has one :b
  (conj #{:a :b} :b)
  ; => #{:a :b}

  ;; You can check whether a value exists in a set
  (get #{:a :b} :a)
  ; => :a

  (:a #{:a :b})
  ; => :a

  (get #{:a :b} "hannah montana")
  ; => nil

  )

"Vous pouvez créer des sets à partir de vecteurs et listes existants en utilisant la fonction set.
Une utilisation non évidente est de vérifier si un élément existe dans une collection :"


(comment
  (set [3 3 3 4 4])
  ; => #{3 4}

  ;; 3 exists in vector
  (get (set [3 3 3 4 4]) 3)
  ; => 3

  ;; but 5 doesn't
  (get (set [3 3 3 4 4]) 5)
  ; => nil

  )
"Tout comme vous pouvez créer des maps, vous pouvez créer des sets de hash et des sets triés :"

(comment
  (Hash-set 1 1 3 1 2)
  ;  => # {1 2 3}

  ;(Triés-set: b: a: c)
  ;  => # {: A: b: c}
  )

"Clojure vous permet également de définir la façon dont un ensemble est trié en utilisant la fonction sorted-set-by."


(exercice
  "Vous pouvez créer un ensemble en convertissant une autre collection"
  [(= #{3} (set __))][3])

(exercice
  "Compter, c'est comme compter les autres collections"
  [(= __ (count #{1 2 3}))]3)

(exercice
  "Rappelez-vous qu'un set est un ensemble * mathématique *"
  [(= __ (set '(1 1 2 2 3 3 4 4 5 5)))]#{1 2 3 4 5})

(exercice
  "Vous pouvez demander à Clojure l'union de deux ensembles"
  [(= __ (clojure.set/union #{1 2 3 4} #{2 3 5}))] #{1 2 3 4 5})

(exercice
  "Et aussi l'intersection"
  [(= __ (clojure.set/intersection #{1 2 3 4} #{2 3 5}))] #{2 3})

(exercice
  "Mais n'oubliez pas la différence"
  [(= __ (clojure.set/difference #{1 2 3 4 5} #{2 3 5}))]#{1 4})


[[:section {:tags "symboles_naming" :title "Symboles et Naming"}]]

"Les symboles sont des identifiants qui sont normalement utilisés pour se référer à quelque chose. Par exemple :"

(comment
  (def failed-movie-titles ["Gone With the Moving Air" "Swellfellas"])

  )


"Dans ce cas, def associe la valeur [\"Gone With the Moving Air\" \"Swellfellas\"] avec le symbole failed-movie-titles.

Vous pensez alors peut-être : "So what? Tout autre langage de programmation me permet d'associer un nom à une valeur."\"
Lisp, toutefois, vous permet de manipuler des symboles comme des données, quelque chose que nous allons beaucoup rencontrer quand nous commencerons à travailler avec des macros.
Les fonctions peuvent retourner des symboles et les prendre comme arguments :

"

(comment
  ;; Identity returns its argument
  (identity 'test)
  ; => test
  )


[[:section {:tags "quote" :title "Quote"}]]

"Vous avez peut-être remarqué l'apostrophe, ' , dans les exemples ci-dessus. On appelle ceci une \"quote\". Voici comment elles marchent.

Normalement en donnant à Clojure un symbole, il renvoit l'objet auquel le symbole se réfère :
"

(comment

  failed-protagonist-names
  ; => ["Larry Potter" "Doreen the Explorer" "The Incredible Bulk"]

  (first failed-protagonist-names)
  ; => "Larry Potter"
  )


"*Quoter* un symbole dit à Clojure d'utiliser le symbole lui-même comme une structure de donnée, et non pas l'objet auquel le symbole se réfère :"

(comment

  'failed-protagonist-names
  ; => failed-protagonist-names

  (eval 'failed-protagonist-names)
  ; => ["Larry Potter" "Doreen the Explorer" "The Incredible Bulk"]

  (first 'failed-protagonist-names)
  ; => Throws exception!

  (first ['failed-protagonist-names 'failed-antagonist-names])
  ; => failed-protagonist-names
  )
"Vous pouvez également *quoter* des collections comme les listes, les maps, et les vecteurs. Tous les symboles de la collection ne seront pas évalués :"

(comment

  '(failed-protagonist-names 0 1)
  ; => (failed-protagonist-names 0 1)

  (first '(failed-protagonist-names 0 1))
  ; => failed-protagonist-names

  (second '(failed-protagonist-names 0 1))
  ; => 0
  )


[[:section {:tags "simplicity" :title "Simplicité"}]]

"Vous avez peut-être remarqué que le traitement des structures de données ne comprend pas d'explication sur la façon de créer de nouveaux types ou classes.
Cela réside dans le fait que Clojure met l'accent sur la simplicité et vous encourage à rechercher en premier les structures de données \"de base\" intégrées.

Si vous venez d'un milieu orienté objet, vous pourriez penser que cette approche est bizarre et vieille.
Voici un épigramme adoré par les Clojuristes qui fait allusion à la philosophie Clojure :"

(comment

  It is better to have 100 functions operate on one data structure
  than 10 functions on 10 data structures.

  -- Alan Perlis)




[[:chapter {:tag "fonctions" :title "Fonctions"}]]

"Une des raisons qui fait que les gens sont fous de Lisp, c'est qu'il vous permet de construire des programmes se comportant de façon complexe, mais dont le premier bloc de construction - la fonction - est simple. Cette section va vous initier à la beauté et l'élégance des fonctions Lisp en abordant les points suivants :

* Appel de fonctions
* Comment fonctions diffèrent de macros et les formulaires spéciaux
* Définir des fonctions
* Les fonctions anonymes
* Fonctions retour"

[[:section {:tags "appel_de_fonctions" :title "Appel de fonctions"}]]

"A présent, vous avez vu de nombreux exemples d'appels de fonction :"

(comment

  (+ 1 2 3 4)
  (* 1 2 3 4)
  (first [1 2 3 4])
  )


" Voici une expression de fonction qui renvoie la fonction + :"

(comment

  ;; Return value of "or" is first truthy value, and + is truthy
  (or + -)
  )

"Vous pouvez utiliser cette expression comme un opérateur dans une autre expression :"

(comment

  ((or + -) 1 2 3)
  ; => 6
  )

"2 autres exemples plus valide"

(comment

  ;; Return value of "and" is first falsey value or last truthy value.
  ;; + is the last truthy value
  ((and (= 1 1) +) 1 2 3)

  ;; Return value of "first" is the first element in a sequence
  ((first [+ 0]) 1 2 3)


  )

"Cependant, ce ne sont pas des appels de fonction valides :"
(comment
  ;; Numbers aren't functions
  (1 2 3 4)

  ;; Neither are strings
  ("test" 1 2 3)
  )

"Si vous exécutez ces derniers dans votre REPL vous obtiendrez quelque chose comme"

(comment
  ClassCastException java.lang.String cannot be cast to clojure.lang.IFn
  user/eval728 (NO_SOURCE_FILE:1)
  )
"La flexibilité des fonctions ne s'arrête pas avec l'expression des fonctions !

Syntaxiquement, les fonctions peuvent prendre des expressions comme arguments, y compris d'autres fonctions.

Prenez la fonction map (à ne pas confondre avec la structure de données de map). map crée une nouvelle liste en appliquant une fonction à chaque membre d'une collection :"

(comment

  ;; The "inc" function increments a number by 1
  (inc 1.1)
  ; => 2.1

  (map inc [0 1 2 3])
  ; => (1 2 3 4)
  )


"(Notez que map ne retourne pas un vecteur, même si nous avons fourni un vecteur comme argument. Vous apprendrez pourquoi plus tard.

En effet, la capacité de Clojure à recevoir des fonctions comme arguments vous permet de construire des abstractions plus puissantes.

Clojure (et tout langage Lisp) vous permet de créer des fonctions qui généralisent sur ​​les processus. Map vous permet de généraliser le processus de transformation d'une collection en appliquant une fonction - toute fonction - sur toute collection.

La dernière chose que vous devez savoir sur les appels de fonction est que Clojure évalue tous les arguments d'une fonction récursive avant de les transmettre à la fonction.

Voici comment Clojure va évaluer un appel de fonction dont les arguments sont aussi des appels de fonctions :"


(comment

  ;; Here's the function call. It kicks off the evaluation process
  (+ (inc 199) (/ 100 (- 7 2)))

  ;; All sub-forms are evaluated before applying the "+" function
  (+ 200 (/ 100 (- 7 2))) ; evaluated "(inc 199)"
  (+ 200 (/ 100 5)) ; evaluated (- 7 2)
  (+ 200 20) ; evaluated (/ 100 5)
  220 ; final evaluation
  )

[[:section {:tags "appel_de_fonctions_macro_forme_special" :title "Fonction, macro, et forms spéciales"}]]


"Dans la dernière section, vous avez appris que les appels de fonction sont des expressions qui ont une expression de fonction en tant qu'opérateur.
Il existe deux autres types d'expressions : appels de macro et **forms spéciaux**. Vous avez déjà vu un couple forms spéciaux :"

(comment

  (def failed-movie-titles ["Gone With the Moving Air" "Swellfellas"])
  (if (= severity :mild)
    (def error-message (str error-message "MILDLY INCONVENIENCED!"))
    (def error-message (str error-message "DOOOOOOOMED!")))
  )

"Vous apprendrez tout ce qu'il y a à savoir sur les appels de macro et les forms spéciaux dans le chapitre \"Clojure Alchemy: lecture, l'évaluation et les macros». Pour l'instant, la principale caractéristique qui rend les forms spéciaux \"spécial\", ce est qu'ils n'évaluent pas toujours tous leurs opérandes, contrairement aux appels de fonction.
Prenez if, par exemple. Sa structure générale est :"

(comment
  (if boolean-form
    then-form
    optional-else-form)
  )


"Maintenant, imaginez que vous aviez une déclaration if de ce genre :"

(comment
  (if good-mood
    (tweet walking-on-sunshine-lyrics)
    (tweet mopey-country-song-lyrics))
  )

"
Une caractéristique qui différencie les forms spéciaux, est que vous ne pouvez pas les utiliser comme arguments de fonctions.

En général, les forms spéciaux implémentent des fonctionnalités Clojure de base qui ne peuvent pas être implémentées avec des fonctions. Il y a seulement une poignée de forms spéciaux Clojure, et il est assez étonnant que cette langue riche est implémentée avec un ensemble aussi petit de blocs.

Les macros sont semblables à des forms particuliers en ce qu'elles évaluent leurs opérandes différemment des appels de fonction et ils ont aussi ne peuvent pas être passés comme arguments à des fonctions. Il est maintenant temps d'apprendre à définir des fonctions!"


[[:section {:tags "definition_de_fonctions" :title "Définition de fonctions"}]]

"Les définitions de fonctions sont constituées de cinq parties principales :

*defn
*Un nom
*(Facultatif) un docstring
*Les paramètres
*Le corps de la fonction
"

"Voici un exemple d'une définition de fonction et un appel de la fonction :"

(comment

  (defn too-enthusiastic
    "Return a cheer that might be a bit too enthusiastic"
    [name]
    (str "OH. MY. GOD! " name " YOU ARE MOST DEFINITELY LIKE THE BEST "
      "MAN SLASH WOMAN EVER I LOVE YOU AND WE SHOULD RUN AWAY TO SOMEWHERE"))

  (too-enthusiastic "Zelda")
  ; => "OH. MY. GOD! Zelda YOU ARE MOST DEFINITELY LIKE THE BEST MAN SLASH WOMAN EVER I LOVE YOU AND WE SHOULD RUN AWAY TO SOMEWHERE"

  )

"Immersons-nous plus profondément dans la docstring, les paramètres et le corps de la fonction."
[[:subsection {:tags "definition_de_fonctions" :title "Définition de fonctions"}]]


"La docstring est vraiment cool. Vous pouvez afficher la docstring pour une fonction dans le REPL avec (doc fn-name) , par exemple (doc map) .
La docstring est également utile si vous utilisez un outil pour générer la documentation de votre code.
Dans l'exemple ci-dessus, \"Return a cheer that might be a bit too enthusiastic\" est la docstring."

[[:subsection {:tags "Les_parametres" :title "Les Paramètres"}]]

"Les fonctions Clojure peuvent être définies avec zéro ou plusieurs paramètres :"

(comment
  (defn no-params
    []
    "I take no parameters!")

  (defn one-param
    [x]
    (str "I take one param: " x " It'd better be a string!"))

  (defn two-params
    [x y]
    (str "Two parameters! That's nothing! Pah! I will smoosh them "
      "together to spite you! " x y))
  )

"Les fonctions peuvent également être surchargées par arité. Cela signifie qu'un corps de fonction fonctionne différement selon le nombre d'arguments passés à une fonction.

Voici la forme générale d'une définition de fonction de plusieurs arité"

(comment

  (defn multi-arity
    ;; 3-arity arguments and body
    ([first-arg second-arg third-arg]
      (do-things first-arg second-arg third-arg))
    ;; 2-arity arguments and body
    ([first-arg second-arg]
      (do-things first-arg second-arg))
    ;; 1-arity arguments and body
    ([first-arg]
      (do-things first-arg)))
  )

"Surcharge par arité est un moyen de fournir des valeurs par défaut pour les arguments. Dans ce cas, \"karate\" est l'argument par défaut pour le paramètre chop-type :"

(comment

  (defn x-chop
    "Describe the kind of chop you're inflicting on someone"
    ([name chop-type]
      (str "I " chop-type " chop " name "! Take that!"))
    ([name]
      (x-chop name "karate")))
  )


"Si vous appelez x-chop avec deux arguments, la fonction fonctionne comme elle le ferait si elle n'était pas une fonction avec une arité multiple :"

(comment
  (x-chop "Kanye West" "slap")
  ; => "I slap chop Kanye West! Take that!"
  )

"Si vous appelez x-chop avec un seul argument, x-chop sera appellée avec le second argument \"karate\" fourni :"

(comment

  (x-chop "Kanye East")
  ; => "I karate chop Kanye East! Take that!"

  )

"Il peut sembler inhabituel de définir une fonction comme çela. Si vous pensez cela, super ! Vous avez appris une nouvelle façon de faire les choses !

Vous pouvez également faire de chaque arité faire quelque chose de complètement indépendant :"

(comment

  (defn weird-arity
    ([]
      "Destiny dressed you this morning my friend, and now Fear is
      trying to pull off your pants. If you give up, if you give in,
      you're gonna end up naked with Fear just standing there laughing
      at your dangling unmentionables! - the Tick")
    ([number]
      (inc number)))

  )

"Mais le plus probable, c'est que vous ne voulez pas le faire. Clojure vous permet également de définir des fonctions d'arité variable en incluant un \"reste-param\", comme dans \"mettre le reste de ces arguments dans une liste avec le nom suivant\" :"

(comment
  (defn codger-communication
    [whippersnapper]
    (str "Get off my lawn, " whippersnapper "!!!"))

  (defn codger
    [& whippersnappers] ;; the ampersand indicates the "rest-param"
    (map codger-communication whippersnappers))

  (codger "Billy" "Anne-Marie" "The Incredible Bulk")
  ; =>
  ; ("Get off my lawn, Billy!!!"
  ;  "Get off my lawn, Anne-Marie!!!"
  ;  "Get off my lawn, The Incredible Bulk!!!")

  )


"Comme vous pouvez le voir, lorsque vous fournissez des arguments aux fonctions d'arité variable, les arguments sont traités comme une liste.

Vous pouvez mélanger les paramètres rest avec des paramètres normaux, mais le paramètre rest doit venir en dernier :"

(comment
  (defn favorite-things
    [name & things]
    (str "Hi, " name ", here are my favorite things: "
      (clojure.string/join ", " things)))

  (favorite-things "Doreen" "gum" "shoes" "kara-te")
  ; => "Hi, Doreen, here are my favorite things: gum, shoes, kara-te"
  )

"Enfin, Clojure a une façon plus sophistiquée pour définir des paramètres, appelé \"déstructuration\", qui mérite son propre paragraphe :"
[[:subsection {:tags "Destructuration" :title "Déstructuration"}]]

"L'idée de base derrière déstructuration est qu'il vous permet de lier de façon concise *symboles* à des *valeurs* au sein d'une *collection*. Regardons un exemple de base :"


(comment


  ;; Return the first element of a collection
  (defn my-first
    [[first-thing]] ; Notice that first-thing is within a vector
    first-thing)

  (my-first ["oven" "bike" "waraxe"])
  ; => "oven"

  )

"Voici comment vous pouvez faire la même chose sans déstructuration :"

(comment

  (defn my-other-first
    [collection]
    (first collection))
  (my-other-first ["nickel" "hair"])
  ; => "nickel"
  )



"Comme vous pouvez le voir, les my-first associent le symbole first-thing avec le premier élément du vecteur qui a été utilisé comme argument. Vous dites à my-first de le faire en plaçant le symbole first-thing dans un vecteur.

Ce vecteur est comme une énorme pancarte montrée à Clojure qui dit, \"Hey ! Cette fonction va recevoir une liste ou un vecteur ou un ensemble comme argument. Rend ma vie plus facile en démontant la structure de l'argument pour moi et en associant les noms avec les différentes parties de l'argument ! \"

Lorsque vous déstructurez un vecteur ou une liste, vous pouvez nommer autant d'éléments que vous voulez et aussi utiliser les paramèetres rest :
"

(comment

  (defn chooser
    [[first-choice second-choice & unimportant-choices]]
    (println (str "Your first choice is: " first-choice))
    (println (str "Your second choice is: " second-choice))
    (println (str "We're ignoring the rest of your choices. "
               "Here they are in case you need to cry over them: "
               (clojure.string/join ", " unimportant-choices))))
  (chooser ["Marmalade", "Handsome Jack", "Pigpen", "Aquaman"])
  ; =>
  ; Your first choice is: Marmalade
  ; Your second choice is: Handsome Jack
  ; We're ignoring the rest of your choices. Here they are in case \
  ; you need to cry over them: Pigpen, Aquaman

  )

"Vous pouvez aussi déstructurer les maps. De la même manière que vous dites Clojure de déstructurer un vecteur ou une liste en fournissant un vecteur comme un paramètre, vous déstructurez les maps en fournissant une map en paramètre :"

(comment

  (defn announce-treasure-location
    [{lat :lat lng :lng}]
    (println (str "Treasure lat: " lat))
    (println (str "Treasure lng: " lng)))
  (announce-treasure-location {:lat 28.22 :lng 81.33})
  ; =>
  ; Treasure lat: 28.22
  ; Treasure lng: 81.33
  )

"Regardons plus en détail la ligne suivante :"

(comment
  [{lat :lat lng :lng}]

  )

"C'est comme dire à Clojure, \"Yo Clojure rend moi service et associe le nom lat avec la valeur correspondant à la clé :lat . Fais la même chose avec lng et :lng, ok ?. \"

Nous voulons souvent sortir des keywords en dehors d'une map et une syntaxe plus courte pour faire cela existe. Le code suivant aura le même résultat que le code précédent :"

(comment

  ;; Works the same as above.
  (defn announce-treasure-location
    [{:keys [lat lng]}]
    (println (str "Treasure lat: " lat))
    (println (str "Treasure lng: " lng)))

  )

"Vous pouvez conserver l'accès à l'argument de la map originale en utilisant le mot-clé :as. Dans l'exemple ci-dessous, la map originale est accessible avec treasure-location :"


(comment
  ;; Works the same as above.
  (defn receive-treasure-location
    [{:keys [lat lng] :as treasure-location}]
    (println (str "Treasure lat: " lat))
    (println (str "Treasure lng: " lng))

    ;; One would assume that this would put in new coordinates for your ship
    (steer-ship! treasure-location))
  )

  "En général, vous pouvez penser de la déstructuration que vous indiquez à Clojure comment associer des symboles à des valeurs dans une liste, map, ensemble, ou un vecteur.

Maintenant, regardons de plus près la partie de la fonction qui fait quelque chose: le corps de la fonction !"




(def test-address
  {:street-address "123 Test Lane"
   :city "Testerville"
   :state "TX"})

(exercice
  "Déstructuration est un arbitre: il brise les arguments"
  [(= __ ((fn [[a b]] (str b a))
           [:foo :bar]))]":bar:foo")




  [[:subsection {:tags "corps_de_fonction" :title "Corps de fonction"}]]

"Votre corps de fonction peut contenir des forms. Clojure retourne automatiquement le dernier form évalué :"

(comment
  (defn illustrative-function
  []
  (+ 1 304)
  30
  "joe")
(illustrative-function)
; => "joe"

(defn number-comment
  [x]
  (if (> x 6)
    "Oh my gosh! What a big number!"
    "That number's OK, I guess"))

(number-comment 5)
; => "That number's OK, I guess"

(number-comment 7)
; => "Oh my gosh! What a big number!"

)


[[:subsection {:tags "toutes_les_fonctions_sont_crees_egaux" :title "Toutes les fonctions sont créés égaux"}]]

"Une dernière remarque : Clojure ne possède pas fonctions privilégiées. + est seulement une fonction, - est juste une fonction, inc et map sont seulement des fonctions. Elles ne sont pas mieux que les fonctions que vous définissez vous-même.

Plus important encore, ce fait permet de démontrer la simplicité sous-jacente de Clojure. D'une certaine manière, Clojure est très bête. Lorsque vous effectuez un appel de fonction, Clojure dit simplement, \" map ? Bien sûr ! J'applique ce truc et je passe à autre chose. \" Clojure ne se soucie pas de la fonction ni de sa provenance, il traite toutes les fonctions de la même manière. À la base, Clojure ne fait pas de distinction entre addition, multiplication, mapping, il se soucie juste d'appliquer des fonctions.

Plus vous continuerez à programmer avec Clojure, plus vous verrez que la simplicité du langage est idéale. Vous n'aurez pas à vous préoccuper de règles ou syntaxes spéciales quand vous travaillez avec des fonctions diverses. Elles fonctionnent toute de la même manière !"

[[:section {:tags "fonctions_anonymes" :title "Fonctions anonymes"}]]

"Dans Clojure, vos fonctions n'ont pas à avoir de nom. Vous utiliserez en fait des fonctions anonymes tout le temps. Assez mystérieux !

Il y a deux façons de créer des fonctions anonymes. La première consiste à utiliser la forme fn:
"

(comment

  ;; This looks a lot like defn, doesn't it?
  (fn [param-list]
    function body)

  ;; Example
  (map (fn [name] (str "Hi, " name))
    ["Darth Vader" "Mr. Magoo"])
  ; => ("Hi, Darth Vader" "Hi, Mr. Magoo")

  ;; Another example
  ((fn [x] (* x 3)) 8)
  ; => 24
  )


"
Vous pouvez traiter fn de manière presque identique à defn. Les paramètres et les corps des fonctions fonctionnent exactement de la même manière. Vous pouvez utiliser la déstructuration des arguments, les paramètres rest, et ainsi de suite.
Vous pouvez même associer votre fonction anonyme avec un nom ce qui n'est pas surprenant (si ça l'est... surprise !) :
"

(comment

  (def my-special-multiplier (fn [x] (* x 3)))
  (my-special-multiplier 12)
  ; => 36
  )


"

Il y a une autre façon, plus compacte, pour créer des fonctions anonymes :"

(comment

  ;; Whoa this looks weird.
  #(* % 3)

  ;; Apply this weird looking thing
  (#(* % 3) 8)
  ; => 24

  ;; Another example
  (map #(str "Hi, " %)
    ["Darth Vader" "Mr. Magoo"])
  ; => ("Hi, Darth Vader" "Hi, Mr. Magoo")

  )


"Vous pouvez voir que c'est nettement plus compact, mais aussi pas très naturel. Voyons cela un peu plus en détail.

Ce type de fonction anonyme ressemble beaucoup à un appel de fonction sauf qu'il est précédé d'un dièse, # :

"

(comment

  ;; Function call
  (* 8 3)

  ;; Anonymous function
  #(* % 3)
  )

"Cette ressemblance vous permet de voir plus rapidement ce qui se passera lorsque cette fonction anonyme est appliquée. \"Oh,\" vous diriez-vous, \"cela va multiplier par trois son argument\".

Comme vous l'aurez deviné maintenant, le signe pourcent, % , indique l'argument passé à la fonction. Si votre fonction anonyme prend plusieurs arguments, vous pouvez les distinguer comme ceci: 1% , 2% , 3% , etc. % est équivalent à 1% :"


(comment
  (#(str %1 " and " %2) "corn bread" "butter beans")
  ; => "corn bread and butter beans"
  )

"Vous pouvez également passer un paramètre rest :"

(comment

  (#(identity %&) 1 "blarg" :yip)
  ; => (1 "blarg" :yip)
  )

"La principale différence entre cette forme et fn est que cette forme peut facilement devenir illisible et est mieux utilisée pour les petites fonctions."


[[:section {:tags "fonctions_renvoyant" :title "Fonctions renvoyant"}]]

"Les fonctions peuvent renvoyer d'autres fonctions. Les fonctions renvoyant sont des fermetures, ce qui signifie qu'elles peuvent accéder à toutes les variables qui étaient dans son scope lorsque la fonction a été créée.

Voici un exemple type:"

(comment

  ;; inc-by is in scope, so the returned function has access to it even
  ;; when the returned function is used outside inc-maker
  (defn inc-maker
    "Create a custom incrementor"
    [inc-by]
    #(+ % inc-by))

  (def inc3 (inc-maker 3))

  (inc3 7)
  ; => 10
  )


"Woohoo!"



(defn multiply-by-ten [n]
  (* 10 n))

(defn square [n] (* n n))

""
(exercice
  "Appel d'une fonction est comme donner une accolade avec des parenthèses"
  [(defn square [n] (* n n))
    (= __ (square 9))] 81)


(exercice
  "Les fonctions sont généralement définies avant qu'elles ne soient utilisées"
  [
    (defn multiply-by-ten [n] (* 10 n))
   (= __ (multiply-by-ten 2))] 20)


(exercice
  "Mais elles peuvent également être définies en ligne"
  [(= __ ((fn [n] (* 5 n)) 2))] 10)


(exercice
  "Ou en utilisant une syntaxe encore plus courte"
  [(= __ (#(* 15 %) 4))] 60)


(exercice
  "Même les fonctions anonymes peuvent prendre plusieurs arguments (= __ ( #(+ %1 %2 %3) 4 5 6))"
  [(= __ ( #(+ %1 %2 %3) 4 5 6))] 15)


(exercice
  "Les arguments peuvent également être ignorés (= __ (#(* 15 %2) 1 2))"
  [(= __ (#(* 15 %2) 1 2))] 30)


(exercice
  "Une fonction peut engendrer une autre"
  [(= 9 (((fn [] __)) 4 5))] +)


(exercice
  "Les fonctions peuvent aussi prendre d'autres fonctions comme entrée"
  [(= 20 ((fn [f] (f 4 5))
           __))] *)


(exercice
  "Les fonctions d'ordre supérieur prennent des arguments en fonction"
  [(= 25 (__
           (fn [n] (* n n))))] (fn [f] (f 5)))


(exercice
  "Mais ils sont souvent mieux écrit en utilisant les noms de fonctions"
  [(= 25 (__ square))] (fn [f] (f 5)))




(exercice
  "Vous pouvez créer votre map"
  [(= [1 4 9 16 25] (map (fn [x] __) [1 2 3 4 5]))] (* x x))


(exercice
  "Ou utiliser les noms de fonctions existantes"
  [(= __ (map nil? [:a :b nil :c :d]))] [false false true false false])


(exercice

  "Un filtre peut être fort"
  [(= __ (filter (fn [x] false) '(:anything :goes :here)))] ())


(exercice

  "Ou très faible"
  [(= __ (filter (fn [x] true) '(:anything :goes :here)))] [:anything :goes :here])


(exercice

  "Ou quelque part entre les deux"
  [(= [10 20 30] (filter (fn [x] __) [10 20 30 40 50 60 70 80]))] (< x 31))








(defn square [x] (* x x))

(exercice
  "On peut savoir ce qu'ils cherchent en sachant ce qu'ils ne cherchent pas"
  [(= [__ __ __] (let [not-a-symbol? (complement symbol?)]
                   (map not-a-symbol? [:a 'b "c"])))] true false true)


#_ (exercice

  "Louange et «complément» peuvent vous aider à séparer le bon grain de l'ivraie"
  [(= [:wheat "wheat" 'wheat]
     (let [not-nil? __]
       (filter not-nil? [nil :wheat nil "wheat" nil 'wheat nil])))] 4)


#_ (exercice

  "Les fonctions partielles permettent de procrastinater"
  [(= 20 (let [multiply-by-5 (partial * 5)]
           (__ __)))] :a :b :c :d)


#_ (exercice

  "N'oubliez pas: premières choses d'abord"
  [(= [__ __ __ __]
     (let [ab-adder (partial concat [:a :b])]
       (ab-adder [__ __])))] :c :d)


(exercice

  "Les fonctions peuvent unir leurs forces en une fonction 'composée'"
  [(= 25 (let [inc-and-square (comp square inc)]
           (inc-and-square __)))] 4)


(exercice

  "Laissez vous tenter par un double dec-er"
  [(= __ (let [double-dec (comp dec dec)]
           (double-dec 10)))] 8)




[[:chapter {:tags "mise_en_commun" :title "Mise en commun"}]]

"OK ! Il est temps d'utiliser nos nouvelles connaissances pour un but noble : la fessée d'hobbits!

Afin de frapper un hobbit, nous allons d'abord modéliser ses parties du corps. Chaque partie du corps comprendra sa taille relative pour nous indiquer la probabilité que cette partie soit touchée.

Afin d'éviter les répétitions, ce modèle de hobbit n'inclurera que des entrées comme \"pied gauche\", \"oreille gauche\", etc. Par conséquent, nous aurons besoin d'une fonction pour rendre le modèle symmétrique.

Enfin, nous allons créer une fonction qui parcourera les parties du corps et choisira au hasard une partie.

Fun!"

[[:section {:tags "the_shire_s_next_top_model" :title "The Shire's Next Top Model"}]]

"Pour notre modèle de hobbit, nous éviterons des caractéristiques comme «jovialité» et «malice» et nous concentrerons uniquement sur le petit corps du hobbit. Voici notre modèle de hobbit:"

(comment
  (  def  asym-hobbit-body-parts  [{  :name  "head"  :size  3  }
                                   {  :name  "left-eye"  :size  1  }
                                   {  :name  "left-ear"  :size  1  }
                                   {  :name  "mouth"  :size  1  }
                                   {  :name  "nose"  :size  1  }
                                   {  :name  "neck"  :size  2  }
                                   {  :name  "left-shoulder"  :size  3  }
                                   {  :name  "left-upper-arm"  :size  3  }
                                   {  :name  "chest"  :size  10  }
                                   {  :name  "back"  :size  10  }
                                   {  :name  "left-forearm"  :size  3  }
                                   {  :name  "abdomen"  :size  6  }
                                   {  :name  "left-kidney"  :size  1  }
                                   {  :name  "left-hand"  :size  2  }
                                   {  :name  "left-knee"  :size  2  }
                                   {  :name  "left-thigh"  :size  4  }
                                   {  :name  "left-lower-leg"  :size  3  }
                                   {  :name  "left-achilles"  :size  1  }
                                   {  :name  "left-foot"  :size  2  }])

  )

"C'est un vecteur de maps. Chaque map a le nom de la partie du corps et la taille relative de la partie du corps. Ecoutez, je sais que seuls les personnages d'anime ont des yeux faisant 1/3 de leur tête, mais admettons OK?

Manifestement le côté droit du hobbit est absent. Corrigeons cela. Le code ci-dessous est le code le plus complexe que nous avons examiné jusqu'ici. Il introduit quelques idées que nous ne avons pas encore couverts. Ne vous inquiétez pas cependant, parce que nous allons examiner cela en détail:

"

(comment
  (defn needs-matching-part?
    [part]
    (re-find #"^left-" (:name part)))

  (defn make-matching-part
    [part]
    {:name (clojure.string/replace (:name part) #"^left-" "right-")
     :size (:size part)})

  (defn symmetrize-body-parts
    "Expects a seq of maps which have a :name and :size"
    [asym-body-parts]
    (loop [remaining-asym-parts asym-body-parts
           final-body-parts []]
      (if (empty? remaining-asym-parts)
        final-body-parts
        (let [[part & remaining] remaining-asym-parts
              final-body-parts (conj final-body-parts part)]
          (if (needs-matching-part? part)
            (recur remaining (conj final-body-parts (make-matching-part part)))
            (recur remaining final-body-parts))))))

  (symmetrize-body-parts asym-hobbit-body-parts)
  ; => the following is the return value
  [{:name "head", :size 3}
   {:name "left-eye", :size 1}
   {:name "right-eye", :size 1}
   {:name "left-ear", :size 1}
   {:name "right-ear", :size 1}
   {:name "mouth", :size 1}
   {:name "nose", :size 1}
   {:name "neck", :size 2}
   {:name "left-shoulder", :size 3}
   {:name "right-shoulder", :size 3}
   {:name "left-upper-arm", :size 3}
   {:name "right-upper-arm", :size 3}
   {:name "chest", :size 10}
   {:name "back", :size 10}
   {:name "left-forearm", :size 3}
   {:name "right-forearm", :size 3}
   {:name "abdomen", :size 6}
   {:name "left-kidney", :size 1}
   {:name "right-kidney", :size 1}
   {:name "left-hand", :size 2}
   {:name "right-hand", :size 2}
   {:name "left-knee", :size 2}
   {:name "right-knee", :size 2}
   {:name "left-thigh", :size 4}
   {:name "right-thigh", :size 4}
   {:name "left-lower-leg", :size 3}
   {:name "right-lower-leg", :size 3}
   {:name "left-achilles", :size 1}
   {:name "right-achilles", :size 1}
   {:name "left-foot", :size 2}
   {:name "right-foot", :size 2}]
  )

"Analysons ce code !"
[[:section {:tags "let" :title "let"}]]

"Dans notre fonction de symétrie ci-dessus, nous avons ce qui suit:"

(comment

  (let [[part & remaining] remaining-asym-parts
        final-body-parts (conj final-body-parts part)]
    some-stuff)
  )


"Tout cela permait de lier les noms sur la gauche avec les valeurs sur la droite. Vous pouvez voir \"let\" comme un raccourci pour \"Let it be\", qui est aussi une belle chanson des Beatles (au cas où vous ne le saviez pas (dans ce cas, wtf?)). Par exemple, \"Laissez -finale parties de corps soient (conj-finales parties de corps partie) . \""

"Voici quelques exemples simples:"

(comment

  (let [x 3]
    x)
  ; => 3


  (def dalmatian-list
    ["Pongo" "Perdita" "Puppy 1" "Puppy 2"]) ; and 97 more...
  (let [dalmatians (take 2 dalmatian-list)]
    dalmatians)
  ; => ("Pongo" "Perdita")
  )


"*let*  introduit également un nouveau scope :"

(comment
  (def x 0)
  (let [x 1] x)
  ; => 1
  )

"Cependant, vous pouvez référencer des liaisons (binding) existantes dans votre let lié:"

(comment
  (def x 0)
  (let [x (inc x)] x)
  ; => 1
  )

"Vous pouvez également utiliser des paramètres rest dans let comme avec les fonctions :"

(comment

  (let [[pongo & dalmatians] dalmatian-list]
    [pongo dalmatians])
  ; => ["Pongo" ("Perdita" "Puppy 1" "Puppy 2")]
  )

"Notez que la valeur d'une form *let* est la dernière form dans son corps qui est évaluée.

*let* suit les règles de déstructuration qui nous introduit dans «Appel d'une fonction\" ci-dessus.

Une façon de voir les forms *let*, c'est qu'ils fournissent des paramètres et leurs arguments secondaires à côte. Les forms *let* ont deux utilisations principales:

* Ils fournissent de la clarté en vous permettant de nommer les choses
* Ils vous permettent d'évaluer une expression qu'une seule fois et de ré-utiliser le résultat. Ceci est particulièrement important lorsque vous avez besoin de réutiliser le résultat d'un appel d'une fonction coûteux, comme un appel d'API de réseau. Il est également important lorsque l'expression a des effets secondaires.
Changeons de regard sur la form *let* dans notre fonction de symétrie afin que nous puissions comprendre exactement ce qui se passe:"


(comment
  ;; Associate "part" with the first element of "remaining-asym-parts"
  ;; Associate "remaining" with the rest of the elements in "remaining-asym-parts"
  ;; Associate "final-body-parts" with the result of (conj final-body-parts part)
  (let [[part & remaining] remaining-asym-parts
        final-body-parts (conj final-body-parts part)]
    (if (needs-matching-part? part)
      (recur remaining (conj final-body-parts (make-matching-part part)))
      (recur remaining final-body-parts)))
  )

"Notez que *part*, *remaining*, et *final-body-parts* du corps sont utilisés plusieurs fois dans le corps de *let*. Si, au lieu d'utiliser les noms *part* , *remaining*, et *final-body-parts* , nous avions utilisé les expressions originales, ce serait du gâchis ! Par exemple :"


(comment

  (if (needs-matching-part? (first remaining-asym-parts))
    (recur (rest remaining-asym-parts)
      (conj (conj final-body-parts (first remaining-asym-parts))
        (make-matching-part (first remaining-asym-parts))))
    (recur (rest remaining-asm-parts)
      (conj (conj final-body-parts (first remaining-asym-parts)))))
  )

"Ainsi, let est un moyen pratique d'introduire des noms pour les valeurs."

[[:section {:tags "boucle" :title " boucle"}]]

"Loop fournit une autre façon de faire la récursivité dans Clojure. Regardons un exemple simple:"

(comment

  (loop [iteration 0]
    (println (str "Iteration " iteration))
    (if (> iteration 3)
      (println "Goodbye!")
      (recur (inc iteration))))
  ; =>
  Iteration 0
  Iteration 1
  Iteration 2
  Iteration 3
  Iteration 4
  Goodbye!
  )

"La première ligne, *loop [itération 0]* commence la boucle et présente une liaison avec une valeur initiale. C'est comme appeler une fonction anonyme avec une valeur par défaut. Au premier passage dans la boucle, *iteration* a une valeur de 0.

Ensuite, il imprime un petit message super intéressant.

Ensuite, il vérifie la valeur de *iteration* - si elle est supérieure à 3, alors il est temps de dire au revoir. Sinon, nous bouclons via *recur* . C'est comme appeler la fonction anonyme créée par *loop*, mais cette fois nous passons un argument (itération inc).

Vous pourriez en fait accomplir la même chose tout en utilisant les fonctions:"

(comment

  (defn recursive-printer
    ([]
      (recursive-printer 0))
    ([iteration]
      (println (str "Iteration " iteration))
      (if (> iteration 3)
        (println "Goodbye!")
        (recursive-printer (inc iteration)))))
  (recursive-printer)
  ; =>
  Iteration 0
  Iteration 1
  Iteration 2
  Iteration 3
  Iteration 4
  Goodbye!
  )

"Comme vous pouvez le voir, c'est un peu plus verbeux. En outre, la boucle a de bien meilleures performances."


[[:section {:tags "expressions_regulieres" :title "Expressions régulières"}]]

"Les expressions régulières sont des outils pour effectuer un filtrage dans un texte. Nous ne verrons pas comment ils fonctionnent, mais voici leur notation littérale:"
(comment
  ;; pound, open quote, close quote
  #"regular-expression"
  )

"Dans notre fonction de symétrie, re-find renvoie true ou false selon ou non que le nom de la partie commence par la chaîne de caractère \"left\":"

(comment
  (defn needs-matching-part?
    [part]
    (re-find #"^left-" (:name part)))
  (needs-matching-part? {:name "left-eye"})
  ; => true
  (needs-matching-part? {:name "neckbeard"})
  ; => false
  )


"*make-matching-part* utilise une expression régulière pour remplacer \" *left \" par \"* right- \" :"


[[:section {:tags "symetriseur" :title "Symétriseur"}]]

"Maintenant, revenons sur la fonction entière de symétrie. Les remarques sont de la forme *~~~1~~~* :"

(comment
  (def asym-hobbit-body-parts [{:name "head" :size 3}
                               {:name "left-eye" :size 1}
                               {:name "left-ear" :size 1}
                               {:name "mouth" :size 1}
                               {:name "nose" :size 1}
                               {:name "neck" :size 2}
                               {:name "left-shoulder" :size 3}
                               {:name "left-upper-arm" :size 3}
                               {:name "chest" :size 10}
                               {:name "back" :size 10}
                               {:name "left-forearm" :size 3}
                               {:name "abdomen" :size 6}
                               {:name "left-kidney" :size 1}
                               {:name "left-hand" :size 2}
                               {:name "left-knee" :size 2}
                               {:name "left-thigh" :size 4}
                               {:name "left-lower-leg" :size 3}
                               {:name "left-achilles" :size 1}
                               {:name "left-foot" :size 2}])

  (defn needs-matching-part?
    [part]
    (re-find #"^left-" (:name part)))

  (defn make-matching-part
    [part]
    {:name (clojure.string/replace (:name part) #"^left-" "right-")
     :size (:size part)})

  ; ~~~1~~~
  (defn symmetrize-body-parts
    "Expects a seq of maps which have a :name and :size"
    [asym-body-parts] ;
    (loop [remaining-asym-parts asym-body-parts ; ~~~2~~~
           final-body-parts []]
      (if (empty? remaining-asym-parts) ; ~~~3~~~
        final-body-parts
        (let [[part & remaining] remaining-asym-parts ; ~~~4~~~
              final-body-parts (conj final-body-parts part)]
          (if (needs-matching-part? part) ; ~~~5~~~
            (recur remaining (conj final-body-parts (make-matching-part part))) ; ~~~6~~~
            (recur remaining final-body-parts))))))
  )


"1. Cette fonction utilise une stratégie générale qui est commun dans la programmation fonctionnelle.
 Soit une séquence (dans ce cas, un vecteur des parties du corps et de leurs tailles), la fonction sépare continuellement la séquence en «head» et «tail». Ensuite, elle traite le head, l'ajoute au résultat, et utilise la récursion pour continuer le traitement du tail.

2. On commence par boucler sur les parties du corps. Le \"tail\" de la séquence sera lié à *remaining-asym-parts* . Au début, il est lié à la séquence complète passée à la fonction, *asym-body-parts* . Nous créons également une séquence de résultats, *final-body-parts* ; sa valeur initiale est un vecteur vide.

3. Si remaining-asym-parts est vide, cela signifie que nous avons traité la séquence entière et nous pouvons retourner le résultat, *final-body-parts*.

4. Sinon, on divise la liste en head, part, et tail, *remaining* . En outre, ajouter une *part* à *final-body-parts* de corps et re-lier le résultat du nom *final-body-parts* . Cela peut sembler bizarre, et il est intéressant de comprendre pourquoi cela fonctionne.

5. Notre séquence croissante de *final-body-parts* comprend déjà la partie du corps que nous sommes en train d'examiner, *part* . Ici, nous décidons si nous devons ajouter la partie du corps correspondant à la liste.

6. Si oui, nous ajoutons le résultat de *make-matching-part* à *final-body-parts* de corps et recur. Sinon, on recur.


Si vous êtes nouveau dans ce type de programmation, cela peut prendre un certain temps à déchiffrer. Persévérez ! Une fois que vous comprenez ce qui se passe, vous vous sentirez comme un million de dollars!
"

[[:section {:tags "symetriseur_reduice" :title "Symétriseur et reduce"}]]

"Le modèle de «traiter chaque élément d'une séquence et construire un résultat\" est si commun qu'il y a une fonction pour cela: reduce .

Voici un exemple simple:"

(comment
  ;; sum with reduce
  (reduce + [1 2 3 4])
  ; => 10
  )

"C'est comme demander à Clojure de faire cela:"

(comment

  (+ (+ (+ 1 2) 3) 4)

  )

"C'est a dire :"

"1. Appliquer la fonction donnée pour les deux premiers éléments d'une séquence. (+ 1 2) vient de là.
2. Appliquer la fonction donnée pour le résultat et l'élément suivant de la séquence. Dans ce cas, le résultat de l'étape 1 est 3 , et l'élément suivant de la séquence est 3 aussi. Donc, vous vous retrouvez avec (+ 3 3) .
3. Répétez l'étape 2 pour chaque élément restant dans la séquence.
Reduce prend également une valeur optionnelle initiale. 15 est la valeur initiale ici:"

(comment
  (reduce + 15 [1 2 3 4])
  )

"Si vous fournissez une valeur initiale, reduce commence par appliquer la fonction donnée à la valeur initiale et le premier élément de la séquence, plutôt que les deux premiers éléments de la séquence.

Pour mieux comprendre comment reduce marche, voici une façon de l'implémenter :"

(comment
  (defn my-reduce
    ([f initial coll]
      (loop [result initial
             remaining coll]
        (if (empty? remaining)
          result
          (recur (f result (first remaining)) (rest remaining)))))
    ([f [head & tail]]
      (my-reduce f head tail)))
  )

"Nous pourrions ré-implémenter la fonction de symétrie comme cela :"

(comment

  (defn better-symmetrize-body-parts
    "Expects a seq of maps which have a :name and :size"
    [asym-body-parts]
    (reduce (fn [final-body-parts part]
              (let [final-body-parts (conj final-body-parts part)]
                (if (needs-matching-part? part)
                  (conj final-body-parts (make-matching-part part))
                  final-body-parts)))
      []
      asym-body-parts))
  )

"Sympa !"



[[:chapter {:tag "extra" :title "extra"}]]


(exercice
  "Nous contemplerons la vérité en testant la réalité, par l'intermédiaire de l'égalité"
  [(= __ true)] true)

(exercice
  "Pour comprendre la réalité, nous devons comparer nos attentes contre la réalité"
  [(= __ (+ 1 1))] 2)

(exercice
  "Vous pouvez tester l'égalité de beaucoup de choses"
  [(= (+ 3 4) 7 (+ 2 __))] 5)

(exercice
  "Certaines choses peuvent être différentes, mais être les mêmes"
  [(= __ (= 2 2/1))] true)

(exercice
  "Vous pouvez généralement pas \"flotter\" vers les cieux d'entiers"
  [(= __ (= 2 2.0))] false)

(exercice
  "Mais une égalité moins parfaite est également possible"
  [(= __ (== 2.0 2))] true)

(exercice
  "Quelque chose n'est pas égal à rien"
  [(= __ (not (= 1 nil)))] true)

(exercice
  "Chaine de caractère,  mot-clé, et des symbols: OH MON DIEU!"
  [(= __ (= "foo" :foo 'foo))] false)

(exercice
  "Faire un mot-clé avec ou sans mot-clé"
  [(= :foo (keyword __))] "foo")

(exercice
  "Le symbolisme est tout autour de nous"
  [(= 'foo (symbol __))] "foo")

(exercice
  "Quand les choses ne peuvent pas être égaux, ils doivent être différents"
  [(not= :fill-in-the-blank __)] 3)


;;[[:chapter {:tags "hobbit_violence" :title "Hobbit violence"}]]

; "Ma parole, ce est vraiment Clojure pour les braves et True!
;
;Maintenant, nous allons créer une fonction qui permettra de déterminer quelle partie du hobbit se fait frapper:"

;(comment
;
;  (defn hit
;    [asym-body-parts]
;    (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
;          body-part-size-sum (reduce + 0 (map :size sym-parts))
;          target (inc (rand body-part-size-sum))]
;      (loop [[part & rest] sym-parts
;             accumulated-size (:size part)]
;        (if (> accumulated-size target)
;          part
;          (recur rest (+ accumulated-size (:size part)))))))
;
;  (hit asym-hobbit-body-parts)
;  ; => {:name "right-upper-arm", :size 3}
;
;  (hit asym-hobbit-body-parts)
;  ; => {:name "chest", :size 10}
;
;  (hit asym-hobbit-body-parts)
;  ; => {:name "left-eye", :size 1}
;  )
;
;"Oh mon dieu, ce pauvre hobbit! Vous monstre!"
