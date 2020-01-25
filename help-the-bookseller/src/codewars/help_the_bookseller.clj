;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Codewars - Help the bookseller
;; Calculate the stock total based on category of books
;; https://www.codewars.com/kata/54dc6f5a224c26032800005c/
;; Level: 6kyu
;;
;; Warning: this kata has issues, and the solution does
;; not pass all the tests as some edge cases fail.
;; See discussion
;; https://www.codewars.com/kata/54dc6f5a224c26032800005c/discuss
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Description of the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; for each category in list of categories
;; check to see if that category is in the list of books (first character matches)
;; then sum up the number of books for each category that matches
;; return that value with its category
;; if the category is not there or is less than zero, return the category with zero

(ns codewars.help-the-bookseller
  (:require [clojure.string :as string]))


;; Data
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Data taken from the sample test in the kata

(def sample-stock
  ["BBAR 150", "CDXE 515", "BKWR 250", "BTSQ 890", "DRTY 600"])

(def sample-categories
  ["A" "B" "C" "D"])

;; Helper function
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn stock-level
  ""
  [stock category]
  (->> stock
       ;; Get the book stocks matching the category
       (filter #(= category (str (first %))) ,,, )

       ;; Separate the book code and number of stock
       (map #(string/split % #" ") ,,,)

       ;; Get the stock value
       (map second ,,,)

       ;; Convert the stock value to an integer so it can be added up
       (map #(Integer/parseInt %) ,,,)

       ;; sum the total stock for all books in the matching category
       (apply + ,,,)

       ;; return the result as a vector of category and total sum
       (vector category ,,,)))



;; Main function
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn stock-list [list-of-books list-of-cat]
  (map #(stock-level list-of-books %) list-of-cat))


;; call the function with sample data.
(stock-list sample-stock sample-categories)
;; => (["A" 0] ["B" 1290] ["C" 515] ["D" 600])




;; Design journal
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Naughty hack to make the first test work
;; [["A" 0] ["B" 1290] ["C" 515] ["D" 600]]


;; finding the matching stock levels
;; we can get the first character from each string

(first "BBAR 150")
;; => \B

;; as the categories are strings,
;; then we convert the first character back to a string
(str (first "BBAR 150"))

;; filter to return just those stocks that match the category

(filter #(= "A" (str (first %)))  sample-stock)
;; => ()

(filter #(= "B" (str (first %)))  sample-stock)
;; => ("BBAR 150" "BKWR 250" "BTSQ 890")

;; split the string to get the value
(string/split "BBAR 150" #" ")

;; apply the spit over all the strings that represent book stocks
(map
  #(string/split % #" ")
  ["BBAR 150" "BKWR 250" "BTSQ 890"])
;; => (["BBAR" "150"] ["BKWR" "250"] ["BTSQ" "890"])


;; then get the second value from each split string, the stock level
(map second [["BBAR" "150"] ["BKWR" "250"] ["BTSQ" "890"]])
;; => ("150" "250" "890")

;; Now to add up those values
;; applying the sum function to each value in the collection

(apply +
       [150 250 890])
;; => 1290

(apply +
       (map #(Integer/parseInt %)
            ["150" "250" "890"]))
;; => 1290

(apply +
       (map #(read-string %)
            ["150" "250" "890"]))
;; => 1290

;; read-string on unknown values could be a security problem
;; using Integer/parseInt is probably the most appropriate choice.

;; Starting to put all the code together
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Now we create a pipeline for all those functions
;; using the threading macro
;; The ,,, show where the result from the first expression
;; is passed as an argument to the next expression

(->> sample-stock
     (filter #(= "B" (str (first %))) ,,, )
     (map #(string/split % #" ") ,,,)
     (map second ,,,)
     (map #(read-string %) ,,,)
     (apply +) ,,,)

;; creating a helper function

(defn stock-level-helper
  [sample-stock category]
  (->> sample-stock
       (filter #(= category (str (first %))) ,,, )
       (map #(string/split % #" ") ,,,)
       (map second ,,,)
       (map #(read-string %) ,,,)
       (apply +,,,) ))

(map #(stock-level-helper sample-stock %) sample-categories)
;; => (0 1290 515 600)

;; We have the right totals, but not in the right format.

;; We could use a for macro to iterate over the collection of categories
;; and produce the result in the right format

(for [category sample-categories
      :let     [result [category (stock-level sample-stock sample-categories)]]]
  result)

;; Or as stated by the audience, we can format the value in the helper function
;; before returning the value

(defn stock-level-helper
  [sample-stock category]
  (->> sample-stock
       (filter #(= category (str (first %))) ,,, )
       (map #(string/split % #" ") ,,,)
       (map second ,,,)
       (map #(read-string %) ,,,)
       (apply + ,,,)
       (vector category ,,,)))


;; now all we need is to map the helper function
;; over the collection of categories

(map #(stock-level-helper sample-stock %) sample-categories)
;; => (["A" 0] ["B" 1290] ["C" 515] ["D" 600])

;; We have our result.

;; NOTE: This result passes most of the tests on Codewars
;; however there are two edge cases that are not described
;; and as the tests are not shown,
;; its hard to understand how to make them pass.
