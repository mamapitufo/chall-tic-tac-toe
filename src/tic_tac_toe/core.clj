(ns tic-tac-toe.core
  (:require [clojure.string :as string])
  (:gen-class))

(defn next-player
  "Receives the current player and returns who's next."
  [curr]
  ({\x \o, \o \x} curr))

(def side 3)
(defn new-board
  "Generates a side * side board.
   The board is represented as a one dimension vector."
  [side]
  (vec (repeat (* side side) \_)))

;;--- moves
;; read-move assumes a properly formatted move was entered, throws an exception
;; otherwise.
(defn- read-move
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

(defn- valid-move?
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
  [board]
  (some #{\_} board))

(defn winner?
  "Returns true if there are 3 of the same kind on a row. TODO: diagonals."
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
  "Returns a new board after applying move by player."
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
  (println "tic tac toe"))


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

