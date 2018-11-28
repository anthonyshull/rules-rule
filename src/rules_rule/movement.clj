(ns rules-rule.movement
  "Records, specs, and functions for movements."
  (:require [clojure.spec.alpha :as spec]
            [clj-time.core :as time]
            [clj-time.format :as format])
  (:gen-class))

; RECORDS
(defrecord Movement [uuid date origin destination company grade volume])

; SPECS
(def date-regex #"^\d{4}-\d{2}-\d{2}$")

(spec/def ::uuid string?)
(spec/def ::date (spec/and string? #(re-matches date-regex %)))
(spec/def ::origin string?)
(spec/def ::destination string?)
(spec/def ::company string?)
(spec/def ::grade string?)
(spec/def ::volume number?)

(spec/def ::movement
  (spec/keys :req-un
             [::uuid ::date ::origin ::destination ::company ::grade ::volume]))

; FUNCTIONS
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
  (let [volume (Integer/parseInt (:volume row))]
    (-> row
        (assoc :uuid (str (java.util.UUID/randomUUID)))
        (assoc :origin (:from row) :destination (:to row))
        (dissoc :from :to)
        (assoc :volume volume)
        map->Movement)))

(defmethod map->movement :new [row]
  (let [volume (Integer/parseInt (:volume row))]
    (-> row
        (assoc :uuid (str (java.util.UUID/randomUUID)))
        (assoc :volume volume)
        map->Movement)))
