(ns codewars.rock-paper-scissors-test
  (:require [clojure.test :refer :all]
            [codewars.rock-paper-scissors :as SUT]))


;; Testing multiple assertions with `are`
;; https://clojuredocs.org/clojure.test/are

;; using are saves writing very similar `is` functions
;; that only vary by the data used
;; Example:

(are [x y] (= x y)
  2 (+ 1 1)
  4 (* 2 2))

;; Expands to:

(do (is (= 2 (+ 1 1)))
    (is (= 4 (* 2 2))))


(deftest rock-paper-scissors-tests

  (testing "player 1 win"
    (are [player1 player2] (= "Player 1 won!" (SUT/rock-paper-scissors-game player1 player2))
      "rock"     "scissors"
      "scissors" "paper"
      "paper"    "rock"))

  (testing "player 2 win"
    (are [player1 player2] (= "Player 2 won!" (SUT/rock-paper-scissors-game player1 player2))
      "scissors" "rock"
      "paper"    "scissors"
      "rock"     "paper"))

  (testing "draw"
    (are [player1 player2] (= "Draw!" (SUT/rock-paper-scissors-game player1 player2))
      "rock"     "rock"
      "scissors" "scissors"
      "paper"    "paper"))
  )



(deftest rock-paper-scissors-lizard-spock-tests

  (testing "player 1 win"
    (are [player1 player2] (= "Player 1 won!" (SUT/rock-paper-scissors-game player1 player2))
      "rock"     "scissors"
      "scissors" "paper"
      "paper"    "rock"))

  (testing "player 2 win"
    (are [player1 player2] (= "Player 2 won!" (SUT/rock-paper-scissors-game player1 player2))
      "scissors" "rock"
      "paper"    "scissors"
      "rock"     "paper"))

  (testing "draw"
    (are [player1 player2] (= "Draw!" (SUT/rock-paper-scissors-game player1 player2))
      "rock"     "rock"
      "scissors" "scissors"
      "paper"    "paper"))
  )
