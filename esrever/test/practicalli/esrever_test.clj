(ns practicalli.esrever-test
  (:require [clojure.test :refer :all]
            [practicalli.esrever :as SUT]))


(deftest test-reverse-function
  (let [input1 [1 9 13 1 99 9 9 13]
        input2 (repeatedly (+ 5 (rand-int 10)) #(rand-int 100))
        input3 "\"There are only two kinds of languages: the ones people complain about and the ones nobody uses\" - Bjarne Stroustrup"]

    (testing (str input1)
      (is (= (SUT/reverse input1) (reverse input1))))

    (testing (str input2)
      (is (= (SUT/reverse input2) (reverse input2))))

    (testing (str "String: " (prn-str input3))
      (is (= (SUT/reverse input3) (reverse input3))))

    (testing "[]"
      (is (= (SUT/reverse []) [])))

    (testing "[1]"
      (is (= (SUT/reverse [1]) [1])))))
