(ns rules-rule.extract
  (:require [[clj-time.core :as t]
             [clj-time.format :as f]
             [csv-map.core :as c]])
  (:gen-class))

(def parse-date
  "Parse a date."
  [date]
  (f/parse (f/formatters :date) date))

(defn before-november-2018?
  "Did the movement occur before November 2018?"
  [date]
  (let [dt (parse-date date]
    (t/before? (t/date-time 2018 11) dt)))

(defn old-new-format
  "Determine whether the movement is in the old or new format."
  [movement]
  (cond
    (before-november-2018? (:date movement)) :old
    :else :new))

(defmulti row-to-movement old-new-format)

(defmethod row-to-movement :old [row]
  nil)

(defmethod row-to-movement :new [row]
  nil)

(defn csv->movements
  "Take in a csv of movement rows and convert it to a sequence of movements."
  [file]
  (-> file
    (slurp)
    (c/parse-csv)
  ))