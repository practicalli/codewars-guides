(ns practicalli.playing-on-a-chessboard-test
  (:require [clojure.test :refer [deftest is testing]]
            [practicalli.playing-on-a-chessboard :as SUT]))


(deftest a-test1
  (testing "test1"
    (is (= (SUT/game 0) [0]))))

(deftest a-test2
  (testing "test2"
    (is (= (SUT/game 1) [1 2]))))

(deftest a-test3
  (testing "test3"
    (is (= (SUT/game 8) [32]))))
