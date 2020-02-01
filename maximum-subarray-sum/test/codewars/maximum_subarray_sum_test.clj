(ns codewars.maximum-subarray-sum-test
  (:require [clojure.test :refer [deftest is testing]]
            [codewars.maximum-subarray-sum :refer [max-sequence]]
            [codewars.maximum-subarray-sum-test-data :refer [really-big-sequence]]))


(deftest simple
  (testing "Codewars Sample Tests"

    (is (= (max-sequence  [-2, 1, -3, 4, -1, 2, 1, -5, 4]) 6))))

(deftest max-sequence-test
  (testing "All negative numbers"
    (is (= 0 (max-sequence [-1])))
    (is (= 0 (max-sequence [-1 -2 -3 -4]))))

  (testing "All positive numbers"
    (is (= 1 (max-sequence [1])))
    (is (= 2 (max-sequence [1 1])))
    (is (= 3 (max-sequence [1 2])))
    (is (= 45 (max-sequence [1 2 3 4 5 6 7 8 9])))
    )

  (testing "Additional CodeWars tests"
    (is (= 11603 (max-sequence really-big-sequence))))
  )
