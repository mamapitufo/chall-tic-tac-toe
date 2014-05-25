(ns tic-tac-toe.core
  (:require [clojure.string :as string])
  (:gen-class))

(defn next-player
  "Receives the current player and returns who's next."
  [curr]
  ({\x \o, \o \x} curr))

(def side 3)
(defn new-board
  "Generates a side * side empty board."
  [side]
  (vec (repeat (* side side) \_)))

;;--- moves
(defn read-move
  "Reads a move from the console. Assumes that the move is correctly formatted
   as: \"row col\", both numeric, 1-based, indices."
  []
  (let [input (read-line)]
    (map #(Integer/parseInt %) (string/split input #"\s+"))))

(defn- valid-index?
  [[row col]]
  (and (pos? row) (<= row side)
       (pos? col) (<= col side)))

(defn- move->index
  [[row col]]
  (+ (* (dec row) side)
     (dec col)))

(defn valid-move?
  "Returns true if the move references an empty square. Returns logical false
   if the indices are out of bounds or the square is already used."
  [move board]
  (and (valid-index? move)
       (= \_ (board (move->index move)))))

(defn- columns
  [board]
  (let [indices (range (* side side))
        col-indices (map second
                         (group-by #(mod % side) indices))]

    (reduce (fn [cols i]
              (cons (map board i) cols))
              ()
              col-indices)))

(defn remaining?
  "Returns true if there are empty squares in the board, logical false
   otherwise."
  [board]
  (some #{\_} board))

(defn winner?
  "Returns true if the boards contains a winning row or column.

  TODO: diagonals."
  [board]
  (let [rows (partition side board)
        cols (columns board)
        diagonals ()
        non-empty (remove #(and (apply = %)
                                (remaining? %))
                          (concat rows cols diagonals))]

    (some #(apply = %) non-empty)))

;;--- step
(defn step
  "Applies move by player to board and returns the new board."
  [move player board]
  (let [index (move->index move)]
    (assoc board index player)))

;;--- render
(defn- format-empty
  [row]
  (string/replace row \_ \space))

(defn- render-row
  [row]
  (format-empty (string/join "|" row)))

(defn render
  "Renders a board for screen output."
  [board]
  (let [rows (map render-row (partition side board))
        sep (str "\n" (render-row (repeat side \-)) "\n")]
    (println (string/join sep rows))))


(defn -main
  [& args]
  (println "tic tac toe")
  (let [initial-board (new-board side)
        initial-player \x]
    (loop [board initial-board
           player initial-player]
      (render board)
      (cond
        ;; we have a winner
        (winner? board)
        (println (str "Player '" (next-player player) "' wins!"))

        ;; some moves are still available
        (remaining? board)
        (let [move (read-move)]
          (if (valid-move? move board)
            (recur (step move player board)
                   (next-player player))
            (do
              (println "Invalid move!")
              (recur board
                     player))))

        :else (println "It's a tie.")))))

;;------------------------- ex data
(def numeric-board
  [1 2 3
   4 5 6
   7 8 9])

(def mixed-board
  [\_ \x \_
   \o \x \_
   \_ \o \_])

(def win-row
  [\_ \x \o
   \x \x \x
   \o \o \_])

(def win-col
  [\x \o \_
   \_ \o \x
   \x \o \x])

(def finished
  [\x \o \o
   \o \o \x
   \x \x \o])

