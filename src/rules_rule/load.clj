(ns rules-rule.load
  "Convert a vector of movements to JSON."
  (:require [clojure.data.json :as json]
            [rules-rule.movement :as m])
  (:gen-class))

(def movements->json json/write-str)
