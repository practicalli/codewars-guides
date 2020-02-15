(ns practalli.simple-pig-latin)

(defn pig-it [phrase]
  (clojure.string/split phrase #" ")
  )


(pig-it "hello world2")
;; => ["hello" "world"]


(map
  (fn [word] (str (rest word) (first word) "ay"))
  (pig-it "hello world2"))
;; => ("(\\e \\l \\l \\o)hay" "(\\o \\r \\l \\d \\2)way")

(map
  (fn [word] (str (reduce str (rest word)) (reduce str (first word)) "ay"))
  (pig-it "hello world2"))


(reduce str
        (rest "hello"))


(map
  (fn [word] (str (subs word 1 (count word)) (subs word 0 1) "ay"))
  (pig-it "hello world2"))

(subs "hello" 0 1)

(clojure.string/join " "
                     (map
                       (fn [word] (str (subs word 1 ) (subs word 0 1) "ay"))
                       (pig-it "hello world2"))                    )



(defn pig-it [phrase]
  (clojure.string/join
    " "
    (map
      (fn [word]
        (if (some #{"!" "?"} word)
          word
          (str (subs word 1) (subs word 0 1) "ay")))
      (clojure.string/split phrase #" "))))

#_(contains? "!" "!" )

(some #{"!" "?"} "hello")
(some #{(seq "!") "?"} "!")

(re-matches #"[!|?]" "!" )

(re-matches #"[!|?]" "?" )

(some #{2} (range 0 10))      ;;=> 2
(some #{6 2 4} (range 0 10))  ;;=> 2
(some #{2 4 6} (range 3 10))  ;;=> 4
(some #{200} (range 0 10))    ;;=> nil


(defn pig-it [phrase]
  (clojure.string/join
    " "
    (map
      (fn [word]
        (if (re-matches #"[!|?]" word )
          word
          (str (subs word 1) (subs word 0 1) "ay")))
      (clojure.string/split phrase #" "))))

(pig-it "Hello !")
