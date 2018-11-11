(ns rules-rule.extract
  (:require [csv-map.core :as c]
            [rules-rule.movement :as m])
  (:gen-class))

(defn csv->maps
  "Parse a csv into a sequence of maps with keywords as keys."
  [csv]
  (c/parse-csv csv :key :keyword))

(defn csv->movements
  "Take in a csv of movement rows and convert it to a sequence of movement records."
  [file]
  (->>
    file
    slurp
    csv->maps
    (pmap m/map->movement)))
