(ns rules-rule.movement
  (:require [clj-time.core :as time]
            [clj-time.format :as format])
  (:gen-class))

(defrecord Movement [date origin destination company grade volume])

(defn before-november-2018?
  "Did the movement occur before November 2018?"
  [date]
  (let [dt (format/parse (format/formatters :date) date)]
    (time/before? dt (time/date-time 2018 11 1))))

(defn old-new-format
  "Determine whether the movement row is in the old or new format."
  [row]
  (cond
    (before-november-2018? (:date row)) :old
    :else :new))

(defmulti map->movement
  "Convert a map representing a movement to a Movement record."
  old-new-format)

(defmethod map->movement :old [row]
  ""
  (let [volume (Integer/parseInt (:volume row))]
    (->
      row
      (assoc :origin (:from row))
      (dissoc :origin)
      (assoc :destination (:to row))
      (dissoc :destination)
      (assoc :volume volume)
      map->Movement)))

(defmethod map->movement :new [row]
  ""
  (let [volume (Integer/parseInt (:volume row))]
    (map->Movement (assoc row :volume volume))))
