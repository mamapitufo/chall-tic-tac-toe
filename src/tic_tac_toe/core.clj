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


(defn- render-row
  [row]
  (string/join "|" row))

(defn render
  "Renders a board for screen output."
  [board]
  (println (string/join "\n"
                        (map render-row (partition side board)))))

(defn -main
  [& args]
  (println "tic tac toe"))
