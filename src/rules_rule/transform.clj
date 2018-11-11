(ns rules-rule.transform
  (:require [clara.rules :refer :all])
  (:gen-class))

(defrule negative-volume
  "If the volume is negative flip the origin and destination."
  []
  =>
  nil)

(defrule missing-location
  "If the destination is missing and the company is Sunoco the destination is Arlington."
  []
  =>
  nil)

(defrule regular-barrels
  "If the product is regular gasoline convert from barrels to gallons."
  []
  =>
  nil)

(defn apply-rules
  ""
  [movements]
  nil)