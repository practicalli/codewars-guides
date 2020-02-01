(ns codewars.maximum-subarray-sum)

(defn max-sequence
  [xs]
  (apply max (reductions #(max (+ %1 %2) 0) 0 xs)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; First define some unit tests
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; See test/codewars/maximum_subarray_sum_test.clj file


;; Testing for a positive integer
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; The pos? function is a predicate function,
;; returning true or false.
;; If a numerical value is positive, then true returned,
;; otherwise false

(pos? -1)
;; => false

(pos? 1)
;; => true

;; Also works for decimal numbers
(pos? 1.2)
;; => true

(pos-int? 1.2)

;; Error if passing a non-integer number as an argument
;; class cast exceptions
#_(pos? "one")
#_(pos? :one)


;; We can map pos? function over our data

(map pos? [-2, 1, -3, 4, -1, 2, 1, -5, 4])
;; => (false true false true false true true false true)

;; now we have each value represented as a boolean
;; if we and all the values together
;; we will get true if they are all true
;; and false if any or all are false

(and true true)
;; => true
(and true false)
;; => false
(and false false)
;; => false



;; putting those together

;; Error
#_(reduce and (map pos? [-2, 1, -3, 4, -1, 2, 1, -5, 4]))


(and '(false true false true false true true false true))
;; => (false true false true false true true false true)

;; cant use a macro, and, with apply
(apply and '(,, false true false true false true true false true))


;; instead of and, we can use every?

(every? identity '(false true false true false true true false true))
;; => false

(every? identity '(true true))
;; => true

;; so we would have a combined expression
(every? identity (map pos? [-2, 1, -3, 4, -1, 2, 1, -5, 4]))


;; Looking at the every? page on clojuredocs.org,
;; there is also an every-pred? function

;; It looks very powerful as we can combine multiple predicates,
;; we have to call an every-pred expression in another ()
;; as every-pred returns a function definition
;; i.e. its a higher order function.

((every-pred number? odd?) 3 9 11)

;; we cant use pos? on a collection
((every-pred pos?)
 [-2, 1, -3, 4, -1, 2, 1, -5, 4])

;; but we can use apply to construct an expression
;; that puts every-pred pos? at the same level as the values

(apply (every-pred pos?)
       [-2, 1, -3, 4, -1, 2, 1, -5, 4])
;; => false

;; This is the same as writing

((every-pred pos?) -2, 1, -3, 4, -1, 2, 1, -5, 4)
;; => false

(every? pos? [-2, 1, -3, 4, -1, 2, 1, -5, 4])


;; put those together
(defn max-sequence
  [data]
  (if (apply (every-pred pos?) data)
    (apply + data)
    nil))

(max-sequence
  [1 4 2 1 4])


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Run unit tests to see where we are
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



;; Testing for a negative integer
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; If all numbers are negative then return zero

;; Well this is easy

(defn max-sequence
  [data]
  (if (apply (every-pred pos?) data)
    (apply + data)
    0))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Run unit tests to see where we are
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



;; If that was the challenge, then we wold be finished.
;; But those are just the simple paths.

;; Now we need the case where numbers are both positive
;; and negative.



;; What does the other part actually mean though
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; understanding this was the hardest part of the problem for me


(apply + [-2, 1, -3, 4, -1, 2, 1, -5, 4])
;; => 1
(apply + [ 1, -3, 4, -1, 2, 1, -5, 4])
;; => 3
(apply + [ -3, 4, -1, 2, 1, -5, 4])
;; => 2
(apply + [ 4, -1, 2, 1, -5, 4])
;; => 5
(apply + [-1, 2, 1, -5, 4])
;; => 1
(apply + [2, 1, -5, 4])
;; => 2
(apply + [1, -5, 4])
;; => 0
(apply + [-5, 4])
;; => -1
(apply + [4])
;; => 4
(apply + [-2, 1, -3, 4, -1, 2, 1, -5, 4])
;; => 1
(apply + [-2, 1, -3, 4, -1, 2, 1, -5])
;; => -3
(apply + [-2, 1, -3, 4, -1, 2, 1])
;; => 2
(apply + [-2, 1, -3, 4, -1, 2])
;; => 1
(apply + [-2, 1, -3, 4, -1])
;; => -1
(apply + [-2, 1, -3, 4])
;; => 0
(apply + [-2, 1, -3])
;; => -4
(apply + [-2, 1])
;; => -1
(apply + [-2])
;; => -2



;; first thoughts is a reducing function that builds the subarray
;; going through every combination of sequence

;; or lazily generate every possible sequential combination,
;; and one by one sum each sequence up,
;; dropping that sequence if it is less than the first sum of sequence



(defn tails
  [xs]
  (take-while seq (iterate rest xs)))


(tails [-2, 1, -3, 4, -1, 2, 1, -5, 4])
;; => ([-2 1 -3 4 -1 2 1 -5 4] (1 -3 4 -1 2 1 -5 4) (-3 4 -1 2 1 -5 4) (4 -1 2 1 -5 4) (-1 2 1 -5 4) (2 1 -5 4) (1 -5 4) (-5 4) (4))


;; rest would never end as it continues to return an empty sequence
(rest '())
;; => ()

;; seq checks if to see if its an empty sequence and returns nil if true.
(seq '())
;; => nil

;; use seq instead of not empty.
#_(not (empty? []))


;; Therefore seq is our end condition that causes the iterate lazy sequence
;; to evaluate into a result.


;; From the end we can also use reductions

(defn heads
  [xs]
  (reductions conj [] xs))

(heads [-2, 1, -3, 4, -1, 2, 1, -5, 4])
;; => ([] [-2] [-2 1] [-2 1 -3] [-2 1 -3 4] [-2 1 -3 4 -1] [-2 1 -3 4 -1 2] [-2 1 -3 4 -1 2 1] [-2 1 -3 4 -1 2 1 -5] [-2 1 -3 4 -1 2 1 -5 4])


;; reductions provides all the intermediate answers as
;; conj takes the next value from the sequence xs
;; and puts it into the empty vector.

;; If reduce was used instead, then we would only get the final answer.
(reduce conj [] [-2, 1, -3, 4, -1, 2, 1, -5, 4])
;; => [-2 1 -3 4 -1 2 1 -5 4]


(defn combinations
  [xs]
  (map concat (heads xs) (tails xs)))

(combinations [-2, 1, -3, 4, -1, 2, 1, -5, 4])
;; => ((-2 1 -3 4 -1 2 1 -5 4) (-2 1 -3 4 -1 2 1 -5 4) (-2 1 -3 4 -1 2 1 -5 4) (-2 1 -3 4 -1 2 1 -5 4) (-2 1 -3 4 -1 2 1 -5 4) (-2 1 -3 4 -1 2 1 -5 4) (-2 1 -3 4 -1 2 1 -5 4) (-2 1 -3 4 -1 2 1 -5 4) (-2 1 -3 4 -1 2 1 -5 4))



(map #(apply + %)
     (combinations [-2, 1, -3, 4, -1, 2, 1, -5, 4]))
;; => (1 1 1 1 1 1 1 1 1)

;; This doesnt seem right at all :()
;; actually the apply result is right,
;; but I didn't look closely at map concat... oops.

;; I dont need to (map)

(defn combinations
  [xs]
  (concat (heads xs) (tails xs)))

(combinations [-2, 1, -3, 4, -1, 2, 1, -5, 4])
;; => ([] [-2] [-2 1] [-2 1 -3] [-2 1 -3 4] [-2 1 -3 4 -1] [-2 1 -3 4 -1 2] [-2 1 -3 4 -1 2 1] [-2 1 -3 4 -1 2 1 -5] [-2 1 -3 4 -1 2 1 -5 4] [-2 1 -3 4 -1 2 1 -5 4] (1 -3 4 -1 2 1 -5 4) (-3 4 -1 2 1 -5 4) (4 -1 2 1 -5 4) (-1 2 1 -5 4) (2 1 -5 4) (1 -5 4) (-5 4) (4))


;; How many results did we get back

(count (combinations [-2, 1, -3, 4, -1, 2, 1, -5, 4]))
;; => 19


;; That's better
;; now lets add them up

(map #(apply + %)
     (combinations [-2, 1, -3, 4, -1, 2, 1, -5, 4]))
;; => (0 -2 -1 -4 0 -1 1 2 -3 1 1 3 2 5 1 2 0 -1 4)


;; and the biggest value is?

(reduce max
        (map #(apply + %)
             (combinations [-2, 1, -3, 4, -1, 2, 1, -5, 4])))
;; => 5


;; But our test said 6 was the largest number,
;; so it seems we haven't generated all the combinations


;; recursive function which calls itself
;; whilst varying the data passed each time


;; Using partition to generate all the combinations
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; If we use partition on the data, then we can divide the data into
;; sequences of a specific size (partitions)
;; with a step of 1 then we can create all the combinations
;; for a specific partiton size.

(partition 2 1 [-2, 1, -3, 4, -1, 2, 1, -5, 4])
;; => ((-2 1) (1 -3) (-3 4) (4 -1) (-1 2) (2 1) (1 -5) (-5 4))


(partition 3 1 [-2, 1, -3, 4, -1, 2, 1, -5, 4])
;; => ((-2 1 -3) (1 -3 4) (-3 4 -1) (4 -1 2) (-1 2 1) (2 1 -5) (1 -5 4))


(partition 4 1 [-2, 1, -3, 4, -1, 2, 1, -5, 4])
;; => ((-2 1 -3 4) (1 -3 4 -1) (-3 4 -1 2) (4 -1 2 1) (-1 2 1 -5) (2 1 -5 4))


;; If we create partitions of increasing size
;; then we should be able to get all the combinations
;; as we iterate over partition size
;; until the partition size is the same as the size of data

;; This can be expressed as a simple loop.


(loop [data          [-2, 1, -3, 4, -1, 2, 1, -5, 4]
       partiton-size 1
       current-max   0]
  (if (= partiton-size (count data))
    current-max

    ;; call the loop again with new values for data, partition-size and current-max
    (recur  data ;; the data is always the same, our initial data

            (inc partiton-size)

            ;; calculate the sum of each new partition created
            ;; assign the biggest value to new-max
            ;; and compare current-max with new-max, returning which ever is larger
            (let [new-max (reduce max
                                  (map #(apply + %)
                                       (partition partiton-size 1 data)))]
              (if (< current-max new-max)
                new-max
                current-max)))))


;; Now we can put this loop-recur expression in to our main function,
;; using a cond to replace the original if statement
;; to select which calculation we do on the data.

(defn max-sequence [xs]
  (cond
    (apply (every-pred pos?) xs) (apply + xs)

    (apply (every-pred neg?) xs) 0

    :else (loop [data          xs
                 partiton-size 1
                 current-max   0]
            (if (= partiton-size (count data))
              current-max
              (recur  data
                      (inc partiton-size)

                      (let [new-max (reduce max
                                            (map #(apply + %)
                                                 (partition partiton-size 1 data)))]
                        (if (< current-max new-max)
                          new-max
                          current-max)))))))



;; reducing function
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; A reducing function is one that is passed to reduce.
;; A reducing function can build a new data structure
;; using an accumulator which it passes back to itself
;; as the reducing function iterates through all the data

(defn max-fold

  ;; destructure the accumulator vector, taking out the two values
  ;; max-here will be the maximum value from the last iteration
  ;; max-so-far will be the maximum value ever found
  [[max-here max-so-far] x]

  ;; Use the let binding names to update the accumulator
  ;; updating the value if there is a bigger maximum this iteration

  (let [max-here (max 0 (+ max-here x))]  ;; compare max with zero for all negative numbers

    ;; return the maximum for this iteration
    ;; along with the maximum valued from this iteration and all time.
    [max-here (max max-so-far max-here)]))

;; A simple call to the reducing function, max-fold
;; passing an accumulator vector
;; and the data in which to find the maximum sequence value
(defn max-sequence
  [xs]
  (second (reduce max-fold [0 0] xs)))


(max-sequence [-2, 1, -3, 4, -1, 2, 1, -5, 4])



;; Using reductions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; we saw in head function earlier that reductions
;; can produce all the intermediate results
;; We can use reductions to streamline things further


;; Reduce a function over the data set
;; that finds the local maximum by adding the accumulator, initially zero,
;; with the current value from the data set.
;; If that sum is greater than 0, then the return the sum value,
;; otherwise return zero.
;; (fn [accumulator value] (max (+ accumulator value) 0))

;; using reductions we get all the intermediary maximum values
;; apply max then gets the maximum from all those values

(defn max-sequence
  [xs]
  (apply max (reductions #(max (+ %1 %2) 0) 0 xs)))


(max-sequence [-2, 1, -3, 4, -1, 2, 1, -5, 4])
