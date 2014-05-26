(ns tic-tac-toe.core
  (:require [clojure.math.numeric-tower :refer [sqrt]]
            [clojure.string :as string])
  (:gen-class))

(defn next-player
  "Who's next?"
  [curr]
  ({\x \o, \o \x} curr))

(defn- invalid-side?
  [n]
  (not (and (pos? n)
            (integer? n))))

;;--- Board
(defn side
  "Returns the size of one side of a square board."
  [board]
  (sqrt (count board)))

(defn new-board
  "Generates a side * side empty board. side must be a positive integer."
  [side]
  (when (invalid-side? side)
    (throw (Exception. "side must be a positive integer.")))

  (vec (repeat (* side side) \_)))

;;--- Player Moves
(defn read-move
  "Reads a move from the console. Assumes that the move is correctly formatted
   as: \"row col\": both numeric, 1-based, indices."
  [player]
  (print (str "next move for " player ": "))
  (flush)

  (let [input (read-line)]
    (map #(Integer/parseInt %) (string/split input #"\s+"))))

(defn- valid-index?
  [[row col] board]
  (let [s (side board)]
    (and (pos? row) (<= row s)
         (pos? col) (<= col s))))

(defn- move->index
  [[row col] board]
  (+ (* (dec row) (side board))
     (dec col)))

(defn valid-move?
  "Returns true if the move references an empty square. Returns logical false
   if the indices are out of bounds or the square is already used."
  [move board]
  (and (valid-index? move board)
       (= \_ (board (move->index move board)))))

(defn- rows
  [board]
  (partition (side board) board))

(defn- columns
  [board]
  (let [rows (rows board)]
    (apply map vector rows)))

(defn- diagonals
  [board]
  (let [rows (rows board)]
    (vector (map-indexed #(nth %2 %1) rows)
            (map-indexed #(nth %2 %1) (reverse rows)))))

(defn empty-cells?
  "Returns true if there are empty squares in the coll of cells, logical false
   otherwise."
  [coll]
  (some #{\_} coll))

(defn winner?
  "Returns true if the boards contains a winning row or column."
  [board]
  (let [candidates (concat (rows board)
                           (columns board)
                           (diagonals board))
        non-empty (remove empty-cells? candidates)]

    (some #(apply = %) non-empty)))

;;-- Screen Render
(defn- format-empty
  [row]
  (string/replace row \_ \space))

(defn- render-row
  [row]
  (format-empty (string/join "|" row)))

(defn render
  "Renders a board for screen output."
  [board]
  (let [s (side board)
        rows (map render-row (rows board))
        sep (str "\n" (render-row (repeat s \-)) "\n")]
    (println (string/join sep rows))))

;;--- Game Loop
(defn step
  "Applies move by player to board and returns the new board."
  [move player board]
  (let [index (move->index move board)]
    (assoc board index player)))

(defn game-loop
  "Runs the game loop, alternating between players until one wins or there
   are no more moves available."
  [board player]
  (render board)
  (cond
    (winner? board)
    (println (str "Player " (next-player player) " wins!"))

    (empty-cells? board)
    (let [move (read-move player)]
      (if (valid-move? move board)

        (recur (step move player board)
               (next-player player))

        (do
          (println "Invalid move!")
          (recur board player))))

    :else (println "It's a tie.")))



(defn -main
  [& args]
  (println "tic tac toe")
  (let [initial-board (new-board 3)
        initial-player \x]
    (game-loop initial-board initial-player)))

