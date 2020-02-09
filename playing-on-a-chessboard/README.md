# playing-on-a-chessboard

CodeWars: Playing on a Chessboard
https://www.codewars.com/kata/55ab4f980f2d576c070000f4/

This kata times out when solving with a brute-force approach.  There is a mathematical approach to solving this which is not mentioned in the kata.

### Mathematical approach
1. multiply the board size by itself to get the total number of cells in a board

2. multiple the total number of cells in a board by 1/2, and see if it produces a ratio type result
- if its not a ratio, simply return the board size in a vector.
- if it is a ratio, then return the individual numerator and denominator

## Usage

Open the project in your favourite Clojure editor and start a REPL.
Explore the code from the top downwards.

## License

Copyright © 2020 Practicalli

Distributed under the Creative Commons Attribution Share-Alike 4.0 International
