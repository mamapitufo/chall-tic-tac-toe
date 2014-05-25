(ns tic-tac-toe.test-data)

(def in-progress
  [\_ \x \_
   \o \x \_
   \_ \o \_])

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

(def tied-game
  [\x \o \o
   \o \o \x
   \x \x \o])

(def empty-board
  [\_ \_ \_
   \_ \_ \_
   \_ \_ \_])

