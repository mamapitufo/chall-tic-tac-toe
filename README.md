# tic-tac-toe

Simple tic-tac-toe game for the console, written in Clojure.

Allows 2 players to play against each other.

## Usage

You need Leiningen to run it:

    $ lein run

## Technical Notes

* I started with a flat collection to represent the board thinking that
  it would be easier to manage the lookups and change the dimensions of
  the board. I'm happy with some aspects, but I don't like the `side`
  function or the `rows` helper.

## License

Copyright © 2014 Alberto Brealey-Guzman

