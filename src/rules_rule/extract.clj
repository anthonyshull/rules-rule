(ns rules-rule.extract
  (:require [csv-map.core :as csv]
            [rules-rule.movement :as movement])
  (:gen-class))

(defn csv->maps
  "Parse a csv into a sequence of maps with keywords as keys."
  [csv]
  (csv/parse-csv csv :key :keyword))

(defn csv->movements
  "Take in a csv of movement rows and convert it to a sequence of movement records."
  [file]
  (->>
    file
    slurp
    csv->maps
    (map movement/map->movement)))
