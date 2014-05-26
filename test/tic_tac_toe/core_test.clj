(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.test-data :as data]))

;;----- new board
(deftest new-board-3-by-3
  (testing "(new-board 3) returns an empty board, size 3x3."
    (is (= data/empty-board
           (new-board 3)))))

(deftest new-board-with-invalid-throws
  (testing "new-board with invalid side should throw an exception."
    (are [n] (thrown? Exception (new-board n))
         0 -1 1.5)))


;;----- valid-move?
(deftest correctly-detect-valid-move
  (testing "valid-move? should allow a valid move."
    (are [move] (true? (valid-move? move data/in-progress))
         [1 1]
         [3 3]
         [2 3])))

(deftest correctly-detect-invalid-move
  (testing "valid-move? should return logical false for invalid-moves."
    (are [move] (false? (boolean (valid-move? move data/in-progress)))
         [1 2]
         [1 5]
         [0 0]
         [7 2]
         [2 2]
         [-3 2])))

;;----- empty-cells?
(deftest identify-full-board
  (testing "empty-cells? should correctly identify a full board."
    (is (false? (boolean (empty-cells? data/tied-game))))))

(deftest identify-available-moves
  (testing "empty-cells? should correclty identify a board with available moves."
    (are [board] (true? (boolean (empty-cells? board)))
         data/in-progress
         data/winning-row-o
         data/empty-board)))

;;----- winner?
(deftest identify-board-with-winner
  (testing "winner? should identify a board with a winner."
    (are [board] (true? (boolean (winner? board)))
         data/winning-row-o
         data/winning-row-x
         data/winning-col-o
         data/winning-col-x
         data/winning-diag-x
         data/winning-diag-o)))

(deftest dont-detect-a-winner-when-theres-none
  (testing "winner? should not identify a winner if there is none."
    (are [board] (false? (boolean (winner? board)))
         data/empty-board
         data/in-progress
         data/tied-game)))

;;----- step move player board
(deftest generate-next-step
  (testing "step generates the correct board."
    (are [move player curr next] (= next (step move player curr))
         [1 1] \x data/in-progress data/in-progress-x-11
         [2 3] \x data/in-progress data/in-progress-x-23)))

