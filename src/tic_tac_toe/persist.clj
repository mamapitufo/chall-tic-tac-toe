(ns tic-tac-toe.persist)

(def base "saved")

(defn- path
  [filename]
  (str base "/" filename ".edn"))

(defn save-game
  "Saves the current state of the game on a text file in the 'saved'
   directory."
  [filename player board]
  (let [file (java.io.File. (path filename))
        parent-dir (.getParent file)
        game {:player player :board board}]

    (.mkdirs (java.io.File. parent-dir))
    (spit file (prn-str game))))

(defn load-game
  "Loads a game saved in filename. Returns the player and the state of the
   board."
  [filename]
  (let [{:keys [player board]} (read-string (slurp (path filename)))]
    (list player board)))

