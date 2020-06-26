(ns practicalli.human-readable-time-test
  (:require [clojure.test :refer [deftest is]]
            [practicalli.human-readable-time :as SUT]))


(deftest Tests
  (is (= (SUT/human-readable      0) "00:00:00"))
  (is (= (SUT/human-readable     59) "00:00:59"))
  (is (= (SUT/human-readable     60) "00:01:00"))
  (is (= (SUT/human-readable     90) "00:01:30"))
  (is (= (SUT/human-readable  86399) "23:59:59"))
  (is (= (SUT/human-readable 359999) "99:59:59")))
