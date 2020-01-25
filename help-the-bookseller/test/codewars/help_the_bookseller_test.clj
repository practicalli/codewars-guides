(ns codewars.help-the-bookseller-test
  (:require [clojure.test :refer :all]
            [codewars.help-the-bookseller :refer :all]))

(deftest a-test1
  (testing "Test 1"
    (def ur ["BBAR 150", "CDXE 515", "BKWR 250", "BTSQ 890", "DRTY 600"])
    (def vr ["A" "B" "C" "D"])
    (def res [["A" 0] ["B" 1290] ["C" 515] ["D" 600]])
    (is (= (stock-list ur vr) res))))


;; If the category, vr [ A, B, C, or D] is available in the stock, ur
;; then sum up all the stocks of that type
