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
* At some point I started moving some of the functions to their own
  modules (board related functions, screen rendering functions, etc),
  but then I thought it wasn't necessary for such a small program.
* I'm not too happy with the `cond` and `if`'s in the game loop, but
  couldn't come up with a different solution.

## License

Copyright © 2014 Alberto Brealey-Guzman

