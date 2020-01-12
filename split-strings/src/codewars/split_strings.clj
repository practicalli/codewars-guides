(ns codewars.split-strings)

;; Kata: Split Strings
;; Level: 6kyu
;; URL: https://www.codewars.com/kata/515de9ae9dcfc28eb6000001

;; Instructions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Splits strings into pairs of two characters.
;; If the string contains an odd number of characters
;; then it should replace the missing second character
;; of the final pair with an underscore ('_').

;; Examples:

;; (solution "abc") ; => ["ab" "c_"]
;; (solution "abcd") ; => ["ab" "cd"]



;; Analysis
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Write a function that takes a string of variable size
;; Split that string at every two characters
;; check the final string for a sigle character and add an underscore if true.


;; Ideas
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; clojure.string/split uses regular expression to split,
;; nothing obvious in that library to split by an index.

;; In clojure.core, there is the partition family of functions
;; partition
;; partition-all

;; `partition` will split the string into characters

(partition 2 "cdabefg")
;; => ((\c \d) (\a \b) (\e \f))

;; `partition` discards any trailing values that do not fit into a full partition
;; `partition-all` will keep all the characters though

(partition-all 2 "cdabefg")
;; => ((\c \d) (\a \b) (\e \f) (\g))


;; convert back to strings
;; watch out for lazy sequences...

(map str
     (partition-all 2 "cdabefg"))
;; => ("clojure.lang.LazySeq@1022" "clojure.lang.LazySeq@fe2" "clojure.lang.LazySeq@1062" "clojure.lang.LazySeq@86")

(map #(str %)
     (partition-all 2 "cdabefg"))
;; => ("clojure.lang.LazySeq@1022" "clojure.lang.LazySeq@fe2" "clojure.lang.LazySeq@1062" "clojure.lang.LazySeq@86")

(doall
  (map str
       (partition-all 2 "cdabefg")))
;; => ("clojure.lang.LazySeq@1022" "clojure.lang.LazySeq@fe2" "clojure.lang.LazySeq@1062" "clojure.lang.LazySeq@86")

(apply str
       (partition-all 2 "cdabefg") )
;; => "clojure.lang.LazySeq@1022clojure.lang.LazySeq@fe2clojure.lang.LazySeq@1062clojure.lang.LazySeq@86"

(apply map str
       (partition-all 2 "cdabefg"))
;; => ("caeg")
;; this seems to make a string out of the first element of every partition...

;; putting the
(def string-split
  (comp #(map str %) #(partition-all 2 %)))
;; => #'codewars.split-strings/string-split

(string-split "cdabefg")
;; => ("clojure.lang.LazySeq@1022" "clojure.lang.LazySeq@fe2" "clojure.lang.LazySeq@1062" "clojure.lang.LazySeq@86")


;; simplify the problem and create a string from one sequence

(apply str '(\a \b))
;; => "ab"

;; Using this expression as an inline function,
;; we can map over the sequence of partitions

(map #(apply str %)
     (partition-all 2 "cdabefg") )
;; => ("cd" "ab" "ef" "g")

;; put this into a function definition

(defn solution
  [xs]
  (map #(if (= 1 (count %))
          (str % "_")
          %)
       (map #(apply str %)
            (partition-all 2 xs))))

(solution "cdabefg")
;; => ("cd" "ab" "ef" "g_")



;; refinement - use odd to see if we need to add _
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; replace the (= 1 (count %))
;; with the `odd?` function

(defn solution
  [xs]
  (map #(if (odd? (count %))
          (str % "_")
          %)
       (map #(apply str %)
            (partition-all 2 xs))))

(solution "cdabefg")
;; => ("cd" "ab" "ef" "g_")


;; refinement - add _ before we partition
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; its probably more efficient to check if the whole string is odd
;; and if so then add the _ character at the end.

;; Then we can use just `partition` on the whole string,
;; as we know it will always be even and therefor not loose any characters.


(defn solution
  [xs]
  (map #(apply str %)
       (partition 2 (if (odd? (count xs))
                      (str xs "_")
                      xs))))

(solution "abcde")
;; => ("ab" "cd" "e_")



;; refinement - partial
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; as the arguments to the inline function are simple
;; then we can simply use partial

(map (partial apply str)
     (partition-all 2 "cdabefg") )
;; => ("cd" "ab" "ef" "g")

(defn solution
  [xs]
  (map (partial apply str)
       (partition 2 (if (odd? (count xs))
                      (str xs "_")
                      xs))))
