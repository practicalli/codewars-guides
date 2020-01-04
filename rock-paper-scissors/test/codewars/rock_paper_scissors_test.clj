(ns codewars.rock-paper-scissors-test
  (:require [clojure.test :refer [are deftest testing]]
            [codewars.rock-paper-scissors :as SUT]))


;; Testing multiple assertions with `are`
;; https://clojuredocs.org/clojure.test/are

;; using are saves writing very similar `is` functions
;; that only vary by the data used
;; Example:

#_(are [x y] (= x y)
    2 (+ 1 1)
    4 (* 2 2))

;; Expands to:

#_(do (is (= 2 (+ 1 1)))
      (is (= 4 (* 2 2))))


(deftest rock-paper-scissors-test

  (testing "player 1 win"
    (are [player1 player2] (= "Player 1 won!"
                              (SUT/rock-paper-scissors-game player1 player2))
      "rock"     "scissors"
      "scissors" "paper"
      "paper"    "rock"))

  (testing "player 2 win"
    (are [player1 player2] (= "Player 2 won!"
                              (SUT/rock-paper-scissors-game player1 player2))
      "scissors" "rock"
      "paper"    "scissors"
      "rock"     "paper"))

  (testing "draw"
    (are [player1 player2] (= "Draw!"
                              (SUT/rock-paper-scissors-game player1 player2))
      "rock"     "rock"
      "scissors" "scissors"
      "paper"    "paper"))
  )



(deftest rock-paper-scisors-lizard-spock-game-test

  (testing "player 1 win"
    (are [player1 player2] (= "Player 1 won!"
                              (SUT/rock-paper-scisors-lizard-spock-game player1 player2))
      "rock"     "scissors"
      "rock"     "lizard"
      "scissors" "paper"
      "scissors" "lizard"
      "paper"    "rock"
      "paper"    "spock"
      "lizard"   "spock"
      "lizard"   "paper"
      "spock"    "scissors"
      "spock"    "rock"))


  (testing "player 2 win"
    (are [player1 player2] (= "Player 2 won!"
                              (SUT/rock-paper-scisors-lizard-spock-game player1 player2))
      "scissors" "rock"
      "scissors" "spock"
      "paper"    "scissors"
      "paper"    "lizard"
      "rock"     "spock"
      "rock"     "paper"
      "lizard"   "rock"
      "lizard"   "scissors"
      "spock"    "lizard"
      "spock"    "paper"))

  (testing "draw"
    (are [player1 player2] (= "Draw!"
                              (SUT/rock-paper-scisors-lizard-spock-game player1 player2))
      "rock"     "rock"
      "scissors" "scissors"
      "paper"    "paper"
      "lizard"   "lizard"
      "spock"    "spock"))
  )
