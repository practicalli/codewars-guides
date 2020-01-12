(ns codewars.split-strings-test
  (:require [clojure.test :refer :all]
            [codewars.split-strings :as SUT]))

(def ^:private sample-tests
  [{:tested-str "cdabefg"
    :result     ["cd" "ab" "ef" "g_"]}
   {:tested-str "cdabefgh"
    :result     ["cd" "ab" "ef" "gh"]}
   {:tested-str "abcd"
    :result     ["ab" "cd"]}])

(doseq [{:keys [tested-str result]} sample-tests]
  (deftest sample-test
    (is (= result (SUT/solution tested-str)))))
