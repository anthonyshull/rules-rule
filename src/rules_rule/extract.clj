(ns rules-rule.extract
  "Extract movement data from a csv file."
  (:require [csv-map.core :as csv]
            [rules-rule.movement :as movement])
  (:gen-class))

(defn csv->maps
  "Parse a `csv` string into a sequence of maps with keywords as keys."
  [csv]
  (csv/parse-csv csv :key :keyword))

(defn csv->movements
  "Take a file in `csv` format of movement rows and convert it to a sequence of movement records."
  [csv]
  (->>
    csv
    slurp
    csv->maps
    (map movement/map->movement)))
