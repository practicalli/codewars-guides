;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Codewars: esreveR
;;
;; Implement the clojure.core/reverse function
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Write a function reverse which reverses a list
;; or in clojure's case, any list-like data structure

(ns practicalli.esrever
  (:refer-clojure :exclude [reverse]))

;; In the challenge the clojure.core/reverse function
;; has been excluded.
;; So calling the reverse function will cause an error.


;; However, if you provide the fully qualified name,
;; you can use the reverse function.

(defn reverse
"Reverse a list"
[xs]
(clojure.core/reverse xs))


;; This does not adhere to the spirit of the challenge though.
;; We learn very little with this answer too.


;; Anatomy of a list in Clojure
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; A Clojure list is a linked list
;; so in a list of (1 2 3)
;; the first number point to the second...
;; 1 -> 2 -> 3

;; So to get to the end of the list,
;; you need to traverse every value in that list.

;; Therefore, when adding values to a list
;; they are added to the start of the list
;; as that is the most efficient approach.

(cons 4 '(1 2 3))
;; => (4 1 2 3)

;; cons creates a new list (sequence) with the value 4 at the head
;; pointing to the other values
;; 4 -> 1 -> 2 -> 3

;; The cons function treats vectors in the same way
(cons 4 [1 2 3])
;; => (4 1 2 3)

;; conj also creates a new collection,
;; returning the same type of collection.
;; Values are added with respect to the collection type.

;; conj with a list adds the value to the start
(conj '(1 2 3) 4)
;; => (4 1 2 3)

;; conj with a vector adds the value at the end
;; as a vector has an index,
;; it is just as quick to add the value at the end

(conj [1 2 3] 4)
;; => [1 2 3 4]

;; we can use conj to add values or a collection
(conj '() 1 2 3)
;; => (3 2 1)

(conj '() '(1 2 3))
;; => ((1 2 3))

;; The adding a collection to a collection simply nest the collections
;; something interesting happened when we add numbers to a collection

;; As conj adds values to the start of the list,
;; it has effectively reversed them.
;; we can use this as a simple reverse.

;; But all our values are in a collection,
;; the reduce function also treats the string as a sequence of characters.

;; We can reduce conj to put all the values from a collection
;; into a new list
;; reversing the values in the process

(reduce conj '() '(1 2 3) )
;; => (3 2 1)

;; this is the same as calling

(conj (conj (conj () 1) 2) 3)
;; => (3 2 1)


;; using reduce and conj together is a very common pattern,
;; so the into function was defined to do the same thing


(into () '(1 2 3))
;; => (3 2 1)

;; With into we define the collection type
;; to create with all the new values

(into () [1 2 3])
;; => (3 2 1)


;; sets are intersting though,
;; as order is not guaranteed
(into () #{1 2 3})


;; In fact, unless something is a sequence, then relying on order can lead to errors


;; Putting our learning into an answer
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn reverse
  "Reverse a list"
  [xs]
  (into () xs))


;; We have a working solution that is nice and clean.


;; Using a def rather than defn
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Assuming the function only takes one argument
;; then we can use a def expression rather than a defn

;; using partial delays the evaluation of the `into ()` evaluation
;; until an argument is given.

(def reverse (partial into ()))

;; When we evaluate `reverse` with a value,
;; it is replaced by the `partial` expression.

(reverse [1 2 3])

((partial into ()) [1 2 3])

;; `partial` passes the argument to `into`
;; and gives the final expression that generates the result

(into () [1 2 3])
