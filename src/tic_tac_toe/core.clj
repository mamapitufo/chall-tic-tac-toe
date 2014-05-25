(ns tic-tac-toe.core
  (:require [clojure.math.numeric-tower :refer [sqrt]]
            [clojure.string :as string])
  (:gen-class))

(defn next-player
  "Who's next?"
  [curr]
  ({\x \o, \o \x} curr))

(defn side
  "Returns the size of one side of a square board."
  [board]
  (sqrt (count board)))

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

(defn- columns
  [board]
  (let [s (side board)
        indices (range (* s s))
        col-indices (map second
                         (group-by #(mod % s) indices))]

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
  (let [s (side board)
        rows (partition s board)
        cols (columns board)
        diagonals ()

        non-empty (remove #(and (apply = %)
                                (remaining? %))
                          (concat rows cols diagonals))]

    (some #(apply = %) non-empty)))

;;-- render
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
        rows (map render-row (partition s board))
        sep (str "\n" (render-row (repeat s \-)) "\n")]
    (println (string/join sep rows))))

;;--- step
(defn step
  "Applies move by player to board and returns the new board."
  [move player board]
  (let [index (move->index move board)]
    (assoc board index player)))

(defn -main
  [& args]
  (println "tic tac toe")
  (let [initial-board (new-board 3)
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

