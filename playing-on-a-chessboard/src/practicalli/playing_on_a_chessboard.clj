;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Codewars: Playing on a Chessboard
;;
;; https://www.codewars.com/kata/55ab4f980f2d576c070000f4/
;;
;; This covers a brute-force approach which is too slow for all the tests
;; and a mathematical approach that will pass tests when boards are very large
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(ns practicalli.playing-on-a-chessboard)

;; Kata Instructions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; With a friend we used to play the following game on a chessboard (8, rows, 8 columns).
;; On the first row at the bottom we put numbers:

;; 1/2, 2/3, 3/4, 4/5, 5/6, 6/7, 7/8, 8/9

;; On row 2 (2nd row from the bottom) we have:

;; 1/3, 2/4, 3/5, 4/6, 5/7, 6/8, 7/9, 8/10

;; On row 3:

;; 1/4, 2/5, 3/6, 4/7, 5/8, 6/9, 7/10, 8/11

;; until last row:

;; 1/9, 2/10, 3/11, 4/12, 5/13, 6/14, 7/15, 8/16

;; When all numbers are on the chessboard each in turn we toss a coin.
;; The one who get "head" wins and the other gives him, in dollars, the sum of the numbers on the chessboard.
;; We play for fun, the dollars come from a monopoly game!

;; Task

;; How much can I (or my friend) win or loses for each game if the chessboard has n rows and n columns?
;; Add all of the fractional values on an n by n sized board and give the answer as a simplified fraction.

;; Ruby, Python, JS, Coffee, Clojure, PHP, Elixir, Crystal, Typescript, Go:

;; The function called 'game' with parameter n (integer >= 0) returns as result an irreducible fraction
;; written as an array of integers: [numerator, denominator].
;; If the denominator is 1 return [numerator].


;; The meaning of numerator and denominator
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; In a fraction, the denominator represents the number of equal parts in a whole,
;; and the numerator represents how many parts are being considered.
;; A fraction as p/q is as p parts, which is the numerator of a whole object,
;; which is divided into q parts of equal size, which is the denominator.




;; Brute-force approach
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Generate a fractions for each grid on the board


;; range will generate the sequence of numbers needed
(range 1 9)
;; => (1 2 3 4 5 6 7 8)

(range 9)
;; => (0 1 2 3 4 5 6 7 8)

;; map over both sequences using a function that creates each fraction

(map
  (fn [x y] (/ x y))
  (range 1 9) (range 2 10))
;; => (1/2 2/3 3/4 4/5 5/6 6/7 7/8 8/9)


;; Then use apply to sum up all the values
;; as ratio types are values in Clojure, this can be done with `+`

(apply + (map
           (fn [x y] (/ x y))
           (range 1 9) (range 2 10)))
;; => 15551/2520


;; Generating sequences can be done with `for`

(for [row  (range 1 9)
      :let [values (map
                     (fn [x y] (/ x y))
                     (range row (+ row 8))
                     (range (inc row) (+ row 9)))]]
  values)


(clojure.pprint/pprint
  (for [row  (range 1 9)
        :let [values (map
                       (fn [x y] (/ x y))
                       (range row (+ row 8))
                       (range (inc row) (+ row 9)))]]
    values))

;; ((1/2 2/3 3/4 4/5 5/6 6/7 7/8 8/9)
;;  (2/3 3/4 4/5 5/6 6/7 7/8 8/9 9/10)
;;  (3/4 4/5 5/6 6/7 7/8 8/9 9/10 10/11)
;;  (4/5 5/6 6/7 7/8 8/9 9/10 10/11 11/12)
;;  (5/6 6/7 7/8 8/9 9/10 10/11 11/12 12/13)
;;  (6/7 7/8 8/9 9/10 10/11 11/12 12/13 13/14)
;;  (7/8 8/9 9/10 10/11 11/12 12/13 13/14 14/15)
;;  (8/9 9/10 10/11 11/12 12/13 13/14 14/15 15/16))


;; This does not generate the right combinations,
;; so the `for` approach is abandoned.




;; Basic recursive approach - loop/recur
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Define a sequence of numerators and denominators
;; If the accumulator count is less than the board size
;; generate a new row of fractions
;; and increment the denominator sequence.
;; Once the accumulator count is the same size as the board
;; we have generated all the rows needed.
;; Add up all the fractions in all the rows to a single value
;; If that value is a ratio, return the numerator and denominator in a vector
;; otherwise return an integer value in a vector.


(defn game
  [board-size]
  (loop [numerators   (range 1 (inc board-size))
         denominators (range 2 (+ board-size 2))
         accumulator  []]

    (if (= (count accumulator) board-size)

      ;; total all values generated for the board
      (let [total (apply + (map #(apply + %) accumulator))]

        (if (ratio? total)
          [(numerator total) (denominator total)]
          [(int total)]))  ;; int is used to convert big number results

      ;; Generate more values
      (recur

        ;; numerator stays the same each time
        numerators

        ;; increment all the values in the denominator
        (map inc denominators)

        ;; new accumulator
        (conj accumulator (map
                            (fn [x y] (/ x y))
                            numerators
                            denominators))))))

;; simple tests
(game 8)
;; => [32]
(game 0)
;; => [0]
(game 1)
;; => [1 2]


;; Testing the solution
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; All Codewars tests pass, except the last tests
;; which times-out as its a very large board.

;; Reading the discussion on Codwars,
;; this cannot be solved with the brute-force approach



;; The mathematics approach
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; 1) multiply the board size by itself to get the total number of cells in a board

;; 2) multiple the total number of cells in a board by 1/2, and see if it produces a ratio type result
;; if its not a ratio, simply return the board size in a vector.
;; if it is a ratio, then return the individual numerator and denominator


;; Example with board game of 9

;; Total number of cells on the board divided by 1/2
(* 9 9 1/2)

;; There are functions for extracting numerator/denominator from a ratio
(denominator 81/2)
;; => 2
(numerator 81/2)
;; => 81


;; Define a function for the mathematics approach

(defn game [board-game]
  (let [x (* board-game board-game 1/2)]
    (if (ratio? x)
      [(numerator x) (denominator x)]
      [x])))


;; refactor using juxt

((juxt numerator denominator) 81/2)
;; => [81 2]


(defn game [board-game]
  (let [x (* board-game board-game 1/2)]
    (if (ratio? x) ((juxt numerator denominator) x) [x])))


;; Testing the new definition
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; simple tests
(game 8)
;; => [32N]
(game 0)
;; => [0N]
(game 1)
;; => [1 2]


;; Works with all Codewars tests,
;; even the very large board test.
