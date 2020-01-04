(ns codewars.rock-paper-scissors)
;; Rock Paper Scissors Game
;; https://en.wikipedia.org/wiki/Rock_paper_scissors
;; Let's play! You have to return which player won! In case of a draw return Draw!.
;; Examples:

;; rock-paper-scisors('scissors','paper') // Player 1 won!
;; rock-paper-scisors('scissors','rock') // Player 2 won!
;; rock-paper-scisors('paper','paper') // Draw!


;; Analysing the problem
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; A simultaneous zero sum game.
;; Only two possible outcomes
;; 1) a draw
;; 2) one player wins, the other looses

;; Basic algorithm
;; Each player chooses a shape
;; The shapes are compared against the game rules
;; The result is returned

;; Taking a proceedural approach you could easily end up with nested if statements

;; If player1 has rock and player2 has scissors...
;;   And If player1 has rock and player2 has paper...

;; This could get very long winded...

;; Using a cond statement would remove the nesting,
;; but you may still end up explicitly checking each combination

(fn [player1 player2]
  (cond
    (= player1 player2)                             "Draw!"
    (and (= player1 "rock") (= player2 "scissors")) "Player 1 won!"
    (and (= player1 "rock") (= player2 "paper"))    "Player 2 won!"
    ,,,  ;; and so on for every combination
    ))


;; Defining rules as a mapping
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; In the past we have used a hash-map as a dictionary lookup,
;; in some language a hash-map is referred to as a dictionary.
;; E.g. The Clacks alphabet - a simple encoder / decoder

(def alphabet
  {"a" "10011001"})

;; We can use a hash-map to define the winning choices,
;; as we know if one player wins, the other looses.
;; If no player wins, then we know its a draw.

;; Winning states can be defined with the key as the choice of one player,
;; the value the choice of the other player

#_{"rock"     "scissors"
   "scissors" "paper"
   "paper"    "rock"}

;; If a player chooses rock and the other has scissors, that player wins
;; and so on.

;; Practicalli solution
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Define the winner states look-up

(def winner-states
  "Mapping to define the winning conditions of the game"
  {"rock"     "scissors"
   "scissors" "paper"
   "paper"    "rock"})


;; Define a function to find the winner

(defn rock-paper-scissors-game
  "Find the winner of the rock paper scissors game.
  Arguments: player choices as strings
  Return: The result as a string"
  [player1 player2]
  (cond
    (= player1 player2)                     "Draw!"
    (= (get winner-states player1) player2) "Player 1 won!"
    :else                                   "Player 2 won!"))

;; assuming that if its not a draw and player1 doesn't win,
;; then player2 must have won.

(rock-paper-scissors-game "rock" "scissors")

#_(defn rock-paper-scissors-game
    "Find the winner of the rock paper scissors game.
  Arguments: player choices as strings
  Return: The result as a string"
    [player1 player2]
    (cond
      (= player1 player2)                     "Draw!"
      (= (get winner-states player1) player2) "Player 1 won!"
      (= (get winner-states player2) player1) "Player 2 won!"
      :else                                   "Are you playing rock paper scissors lizard spock?"))


;; Rock Paper Scissors Lizard Spock
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; https://en.wikipedia.org/wiki/Rock_paper_scissors#/media/File:Rock_Paper_Scissors_Lizard_Spock_en.svg

;; In the game we still have two players,
;; however, we have more choices of object to use.


;; Winning states for the game
;; There are now two objects each object can beat.

(def winner-states-extended
  {"rock"     ["lizard" "scissors"]
   "paper"    ["spock" "rock"]
   "scissors" ["paper" "lizard"]
   "lizard"   ["spock" "paper"]
   "spock"    ["scissors" "rock"]})


;; The same function works from the previous game,
;; with just a sligt modification to check in the sequence

(defn rock-paper-scisors-lizard-spock-game
  "Find the winner of the rock paper scissors game.
  Arguments: player choices as strings
  Return: The result as a string"
  [player1 player2]
  (cond
    (= player1 player2)                                    "Draw!"
    (some #{player2} (get winner-states-extended player1)) "Player 1 won!"
    :else                                                  "Player 2 won!"))


;; `contains?` when used with a vector looks to see if there is an index
;; rather than looking for the value.
#_(contains? [1 2 3 4] 4)
;; => false

;; `some` is used to check the contents of the vector returned
;; from the winner states.
;; `some` must be a function, so we can wrap player2 string in a collection
;; then we can use that as a function
;; A set works.  A vector tries to use the index, so does not work.

(some #{"spock"} ["spock" "paper"])
;; => "spock"

;; an inline function can also be used
(some #(= "spock" %) ["spock" "paper"])
;; => true

(rock-paper-scisors-lizard-spock-game "rock" "lizard")
(rock-paper-scisors-lizard-spock-game "rock" "spock")


;; Alternative solutions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; A variation on the practicalli solution
;; This map also contains the outcomes

#_(def rps-game-map {"scissors" {"paper" "Player 1 won!"
                                 "rock"  "Player 2 won!"}
                     "paper"    {"scissors" "Player 2 won!"
                                 "rock"     "Player 1 won!"}
                     "rock"     {"scissors" "Player 1 won!"
                                 "paper"    "Player 2 won!"}})


;; call rps-game-map to get the hashmap matching player 1's choice
;; Use the returned hash-map as a function with player2 as an argument
;; the map function call wil return the matching value from the player 2 choice as a key
;; If player 2 key is not in the map, then the result must be a draw
;; as player 1 and player2 have the same choice.

#_(defn rock-paper-scisors [player1 player2]
    ((rps-game-map player1) player2 "Draw!"))






;; Using a set
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



(defn rock-paper-scisors [player1 player2]
  (cond
    (= player1 player2)                                                              "Draw!"
    (#{["paper" "rock"] ["scissors" "paper"] ["rock" "scissors"]} [player1 player2]) "Player 1 won!"
    :else                                                                            "Player 2 won!"
    )
  )
