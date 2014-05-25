(ns tic-tac-toe.core
  (:require [clojure.string :as string])
  (:gen-class))

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

(defn- move->index
  [[row col]]
  (if (and (pos? row) (<= row side)
           (pos? col) (<= col side))

    (+ (* (dec row) side)
       (dec col))

    -1))

(defn- valid-move?
  [move board]
  (let [index (move->index move)]

    (and (>= index 0)
         (< index (count board))
         (= \_ (board index)))))

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

