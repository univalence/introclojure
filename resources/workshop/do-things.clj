(ns workshop.do-things)

[[{:private [
              (require 'clojure.walk)

              (defmacro exercice [doc exs result]
                ;;`(every? true? ~
                (clojure.walk/postwalk #(if (= %1 '__) result %1) exs))
              ]}]]




"Faire des choses: un cours Clojure crash

Il est temps de savoir comment effectivement faire des choses avec Clojure! Hot Damn!

Alors que vous avez sans doute entendu parler de l'appui de concurrence génial de Clojure et d'autres caractéristiques prodigieuses, caractéristique la plus saillante de Clojure est que ce est un Lisp. Dans ce chapitre, vous allez découvrir les éléments qui composent ce noyau Lisp: syntaxe, les fonctions et données. Cela vous donnera une base solide pour représenter et résoudre des problèmes dans Clojure.

Ce travail de fond vous permettra également d'écrire du code super important. Dans la dernière section, vous allez lier le tout en créant un modèle d'un hobbit et écrire une fonction pour frapper dans un endroit aléatoire. Super! Important!

Comme vous passez par le chapitre, je recommande que vous tapez les exemples dans un REPL et de les exécuter. Programmation dans une nouvelle langue est une compétence, et, tout comme yodel ou la natation synchronisée, vous devez pratiquer l'apprendre. Par ailleurs, \"Natation synchronisée pour yodelers pour les braves et True\" est due à être publié en Août 20never. Vérifiez-le!"



[[:chapter {:tag "syntaxe" :title "Syntaxe"}]]


"La syntaxe de Clojure est simple. Comme tous les Lisps, il emploie une structure uniforme, une poignée d'opérateurs spéciaux, et un approvisionnement constant de parenthèses livrés par les mines de parenthèses cachés sous le Massachusetts Institute of Technology, où Lisp est né."

[[:section {:tag "formulaires" :title "Formulaires"}]]


"Tout le code Clojure est écrit dans une structure uniforme. Clojure comprend:

* Représentations littérales de structures de données comme les numéros, cordes, cartes, et vecteurs
* Opérations
Nous utilisons la forme de terme pour se référer au code structurellement valide. Ces représentations littérales sont des formes toutes valides:

"
(comment
  "1
" a string "
[" a " " vector " " of " " strings "]"

  )

"Votre code contiendra rarement littéraux flottants, bien sûr, car ils ne *font* rien de leur propre chef. Au lieu de cela, vous allez utiliser des littéraux dans les opérations. Les opérations sont la façon dont vous *faites les choses*. Toutes les opérations prennent la forme, \"ouverture parthensis, opérateur, opérandes, parenthèse fermante\":"
(comment

  (operator operand1 operand2 ... operandn)
  )

"Notez qu'il n'y a pas de virgules. Clojure utilise espaces pour séparer opérandes et il traite des virgules comme espaces. Voici quelques opérations d'exemple:"
(comment

  (+ 1 2 3)
  ; => 6

  (str "It was the panda " "in the library " "with a dust buster")
  ; => "It was the panda in the library with a dust buster"
  )


"Pour rappel, Clojure est constitué de formes. Formes ont une structure uniforme. Ils se composent de littéraux et les opérations. Opérations consistent en des formulaires joints entre parenthèses.

Pour faire bonne mesure, voici quelque chose qui ne est pas une forme parce qu'il n'a pas une parenthèse de fermeture:"


[[{:lang "Clojure"}]]
[[:code "
(+
"]]

"Uniformité structurelle de Clojure est probablement différente de ce que vous êtes habitué. Dans d'autres langues, différentes opérations peuvent avoir différentes structures en fonction de l'opérateur et les opérandes. Par exemple, JavaScript emploie un assortiment de notation infixe, les opérateurs points, et les parenthèses:"

(comment

  1 + 2 + 3
  "It was the panda " .concat ("in the library ", "with a dust buster")
  )

"La structure de Clojure est très simple et cohérent par comparaison. Peu importe ce que l'opérateur vous utilisez ou quel type de données que vous êtes d'exploitation sur, la structure est la même.

Une dernière remarque: je utilise aussi l'expression de terme pour désigner les formes Clojure. Ne soyez pas trop accroché sur la terminologie, cependant."


[[:section {:tag "flow_control" :title "Flow Control"}]]

"Voici quelques opérateurs de contrôle de flux de base. Tout au long du livre, vous rencontrez plus."


[[:subsection {:tag "if" :title "if"}]]

"La structure générale *if* est:"

(comment
  (if boolean-form
    then-form
    optional-else-form)
  )

"Voici un exemple:"

(comment
  (if true
    "abra cadabra"
    "hocus pocus")
  ; => "abra cadabra"
  )


"Notez que chaque branche du cas ne peut avoir une forme. Ceci est différent de la plupart des langues. Par exemple, en Ruby vous pouvez écrire:"

(comment

  if true
  doer.do_thing (1)
  doer.do_thing (2)
  else
  other_doer.do_thing (1)
  other_doer.do_thing (2)
  end
  )


"Pour contourner cette limitation apparente, nous avons le do opérateur:"

[[:subsection {:tag "do" :title "do"}]]

"*do* vous permet de «boucler» des formes multiples. Essayez ce qui suit dans votre REPL:"

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
Dans ce cas, *Success!* est imprimé dans le REPL et \"abra cadabra\" est retourné comme la valeur de l'ensemble if expression.
"

[[:subsection {:tag "when" :title "when"}]]
(comment

  (when true
    (println "Success!")
    "abra cadabra")
  ; => Success!
  ; => "abra cadabra"
  )


"Utilisez *when* lorsque vous voulez faire plusieurs choses quand une certaine condition est vrai, et vous ne voulez pas faire quelque chose quand la condition est fausse.

Cela couvre les opérateurs de contrôle de flux essentiels!"



[[:section {:tag "def" :title "Nommer les choses avec def"}]]

"
Une dernière chose avant de passer aux structures de données: vous utilisez *def* pour *lier* un *nom* à une *valeur* dans Clojure:
"

(comment
  (def failed-protagonist-names
    ["Larry Potter"
     "Doreen the Explorer"
     "The Incredible Bulk"])
  )
"
Dans ce cas, vous contraignant le nom *failed-protagonist-names* à un vecteur contenant trois cordes. Remarquez que je utilise le terme «lier», alors que dans d'autres langauges vous diriez que vous *attribuer* une valeur à une *variable*. Par exemple, en Ruby, vous pouvez effectuer plusieurs affectations à une variable de «construire» sa valeur:
"
(comment

  severity = :mild error_message = "OH GOD! IT'S A DISASTER! WE'RE "
  if severity == :mild error_message = error_message + "MILDLY INCONVENIENCED!"
  else
  error_message = error_message + "DOOOOOOOMED!"
  end
  )

"L'équivalent Clojure serait:"

(comment
  (def severity :mild)
  (def error-message "OH GOD! IT'S A DISASTER! WE'RE ")
  (if (= severity :mild)
    (def error-message (str error-message "MILDLY INCONVENIENCED!"))
    (def error-message (str error-message "DOOOOOOOMED!")))
  )


"Cependant, ce est vraiment mauvais Clojure. Pour l'instant, vous devez traiter def comme si ce est la définition des constantes. Mais ne ayez pas peur! Au cours des prochains chapitres, vous apprendrez comment travailler avec cette limitation apparente en codant dans le style fonctionnel."

[[:chapter {:tag "data_structure" :title "Structures de données"}]]


"Clojure est livré avec une poignée de structures de données qui vous allez vous trouver en utilisant la majorité du temps. Si vous venez d'un milieu orienté objet, vous serez surpris de voir combien vous pouvez faire avec les types " de base " présentés ici.

Toutes les structures de données Clojure sont immuables, ce qui signifie que vous ne pouvez pas les changer en place. Il ya pas d'équivalent pour la Clojure Ruby suit:"
(comment


  failed_protagonist_names = [
                               "Larry Potter",
                               "Doreen the Explorer",
                               "The Incredible Bulk"
                               ]
  failed_protagonist_names [0] = "Gary Potter"
  failed_protagonist_names
  # => [
        #"Gary Potter",
        #"Doreen the Explorer",
        #"The Incredible Bulk"]
        )

"Vous en apprendrez plus sur les raisons de Clojure a été mis de cette façon, mais pour l'instant ce est amusant d'apprendre à faire les choses sans tout ce que philosopher. Sans plus tarder:"


[[:section {:tags "nil_true_false_truthiness_equality" :title "nil, true, false,  Equality"}]]

"Clojure a true et false valeurs. nil est utilisé pour indiquer "aucune valeur" dans Clojure. Vous pouvez vérifier si une valeur est nil avec le intelligemment nommé nil? fonction:"

(comment
  (nil? 1)
  ; => false

  (nil? nil)
  ; => true
  )

"nil et false sont utilisés pour représenter le faux logique, alors que tous les autres valeurs sont logiquement vraies. = est l'opérateur d'égalité:"

(comment
  (= 1 1)
  ; => true

  (= nil nil)
  ; => true

  (= 1 2)
  ; => false
  )


"Certains autres langues vous obligent à utiliser différents opérateurs lorsque l'on compare les valeurs de différents types. Par exemple, vous pourriez avoir à utiliser une sorte de l'opérateur spécial "chaîne de l'égalité" particulièrement fait juste pour les chaînes. Vous ne avez pas besoin de quelque chose bizarre ou pénible comme ça pour tester l'égalité lors de l'utilisation des structures de données intégrées de Clojure."

[[:section {:tags "nombres" :title "Nombres"}]]

"Clojure support numérique a assez sophistiqué. Je ne vais pas passer beaucoup de temps se attarder sur les détails techniques ennuyeux (comme la coercition et de la contagion), parce que cela va obtenir de la manière de faire les choses. Si vous êtes intéressé par ces détails ennuyeux, consultez http://clojure.org/data_structures#Data Ouvrages d'art-Numbers . Autant dire que Clojure va gaiement gérer à peu près tout ce que vous lui lancez."

"Dans le même temps, nous allons travailler avec des nombres entiers et flottants. Nous allons aussi travailler avec des ratios, qui Clojure peut représenter directement. Voici un nombre entier, un flotteur, et un rapport:"

(comment

  93
  1.2
  1/5
  )

[[:section {:tags "strings" :title "Strings"}]]

"Voici quelques exemples de chaînes:"

(comment
  "Lord Voldemort"
  "\"He who must not be named\""
  "\"Great cow of Moscow!\" - Hermes Conrad"
  )


"Notez que Clojure ne permet guillemets pour délimiter les chaînes. 'Lord Voldemort' , par exemple, ne est pas une chaîne valide. Notez également que Clojure n'a pas interpolation de chaîne. Il ne permet concaténation via le str fonction:"

[[:section {:tags "map" :title "maps"}]]

"Les maps sont semblables à des dictionnaires ou hachés dans d'autres langues. Ils sont une façon d'associer une valeur à une autre valeur. Voici par exemple map littéraux:"

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

"Notez que les valeurs d'une map peuvent être de tout type. Chaîne, nombre, map, vecteur, même fonctionner! Clojure ne se soucient pas!

Vous pouvez rechercher des valeurs dans les cartes avec la fonction *get* :"

(comment

  (get {:a 0 :b 1} :b)
  ; => 1

  (get {:a 0 :b {:c "ho hum"}} :b)
  ; => {:c "ho hum"}
  )


"*get* retourera nil si elle ne trouve pas votre clé, mais vous pouvez lui donner une valeur par défaut pour retourner:"

(comment

  (get {:a 0 :b 1} :c)
  ; => nil

  (get {:a 0 :b 1} :c "UNICORNS")
  ; => "UNICORNS"
  )


"Le get-in fonction vous permet de rechercher des valeurs dans les cartes imbriquées:"
(comment
  (get-in {:a 0 :b {:c "ho hum"}} [:b :c])
  ; => "ho hum"
  )


"[:b :c] est un vecteur, que vous lisez dans une minute.

Une autre façon de rechercher une valeur dans une map est de traiter la map comme une fonction, avec la clé comme argument:"


(comment
  ({:name "The Human Coffee Pot"} :name)
  ; => "The Human Coffee Pot"
  )

"Les vrais Clojurists ne feront presque jamais cela. Cependant, Les vrais Clojurists  utilisent des mots clés pour rechercher les valeurs dans les maps:
"

[[:section {:tags "keywords" :title "Mots Clés"}]]

"Les mots-clés en Clojure sont mieux compris par la façon dont ils sont utilisés. Ils sont principalement utilisés comme clés dans les cartes, comme vous pouvez le voir ci-dessus. Exemples de mots clés:"


(comment
  :a
  :rumplestiltsken
  :34
  :_?
  )

"Mots-clés peuvent être utilisés comme des fonctions qui ressemblent jusqu'à la valeur correspondante dans une structure de données. Par exemple:"


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

"Je pense que ce est super cool et réel Clojurists le fais tout le temps. Vous devriez le faire aussi!

Outre l'utilisation de la carte littéraux, vous pouvez utiliser le hash-map fonction pour créer une carte:"

(comment
  (hash-map :a 1 :b 2)
; => {:a 1 :b 2}
)

"Clojure vous permet également de créer des cartes triées, mais je ne couvrira pas cela."

[[:section {:tags "vectors" :title "Vecteurs"}]]


"Un vecteur est similaire à un tableau en ce qu'elle est une collection 0 indexées:"

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

"Notez que nous utilisons le même fonction get  que nous utilisons lors de la recherche des valeurs dans les maps. Le chapitre suivant explique pourquoi nous faisons cela.

Vous pouvez créer des vecteurs avec la fonction vector :"


(comment
  (vector "creepy" "full" "moon")
  ; => ["creepy" "full" "moon"]
  )

"Les éléments sont ajoutés à la fin d'un vecteur:"

(comment
  (conj [1 2 3] 4)
  ; => [1 2 3 4]
  )
[[:section {:tags "listes" :title "Listes"}]]
