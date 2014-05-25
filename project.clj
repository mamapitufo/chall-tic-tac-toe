(defproject tic-tac-toe "0.1.0-SNAPSHOT"
  :description "tic-tac-toe: a coding challenge"
  :url "http://github.com/mamapitufo/chall-tic-tac-toe"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/math.numeric-tower "0.0.4"]]
  :main ^:skip-aot tic-tac-toe.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
