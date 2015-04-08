
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
  "Vous pouvez généralement pas "flotter" vers les cieux d'entiers"
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
