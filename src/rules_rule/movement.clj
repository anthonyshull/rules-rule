(ns rules-rule.movement
  (:require [clj-time.core :as t]
            [clj-time.format :as f])
  (:gen-class))

(defrecord Movement [date origin destination company grade volume])

(defn before-november-2018?
  "Did the movement occur before November 2018?"
  [date]
  (let [dt (f/parse (f/formatters :date) date)]
    (t/before? dt (t/date-time 2018 11 1))))

(defn old-new-format
  "Determine whether the movement row is in the old or new format."
  [row]
  (cond
    (before-november-2018? (:date row)) :old
    :else :new))

(defmulti map->movement old-new-format)

(defmethod map->movement :old [row]
  (map->Movement
    {:date (:date row)
     :origin (:from row)
     :destination (:to row)
     :company (:company row)
     :grade (:grade row)
     :volume (:volume row)}))

(defmethod map->movement :new [row]
  (map->Movement row))
