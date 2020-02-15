(ns practalli.simple-pig-latin-test
  (:require [clojure.test :refer [deftest is testing]]
            [practalli.simple-pig-latin :as SUT]))



(deftest pig-latin-example-test1
  (is (= (SUT/pig-it "Pig latin is cool") "igPay atinlay siay oolcay")))

(deftest pig-latin-example-test2
  (is (= (SUT/pig-it "This is my string") "hisTay siay ymay tringsay")))
