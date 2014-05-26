(ns tic-tac-toe.persist)

(def base "saved")

(defn save-game
  "Saves the current state of the game on a text file in the 'saved'
   directory."
  [filename player board]
  (let [file (java.io.File. (str base "/" filename ".edn"))
        parent-dir (.getParent file)
        game {:player player :board board}]

    (.mkdirs (java.io.File. parent-dir))
    (spit file (prn-str game))))

