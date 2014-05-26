(ns tic-tac-toe.test-data)

(def in-progress
  [\_ \x \_
   \o \x \_
   \_ \o \_])

(def tied-game
  [\x \o \o
   \o \o \x
   \x \x \o])

(def winning-row-x
  [\_ \x \o
   \x \x \x
   \o \o \_])

(def winning-row-o
  [\o \o \o
   \x \_ \x
   \x \_ \_])

(def winning-col-o
  [\x \o \_
   \_ \o \x
   \x \o \x])

(def winning-col-x
  [\x \o \_
   \x \o \o
   \x \_ \x])

(def winning-diag-x
  [\x \_ \o
   \x \o \_
   \x \o \x])

(def winning-diag-o
  [\x \x \o
   \_ \o \_
   \o \_ \x])

(def empty-board
  [\_ \_ \_
   \_ \_ \_
   \_ \_ \_])

