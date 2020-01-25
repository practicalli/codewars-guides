(ns codewars.printer-errors-test
  (:require [clojure.test :refer :all]
            [codewars.printer-errors :refer :all]))

(defn test-assert [act exp]
  (is (= act exp)))

(deftest a-test1
  (testing "printer-error"
    (def u "aaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbmmmmmmmmmmmmmmmmmmmxyz")
    (test-assert (printer-error u) "3/56")
    ))
