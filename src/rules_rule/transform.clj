(ns rules-rule.transform
  (:require [clojure.string :as str]
            [clara.rules :refer :all])
  (:import [rules_rule.movement Movement])
  (:gen-class))

(def alerts (atom []))

(defrule missing-destination
  "If the destination is missing and the company is Sunoco then the destination is Arlington."
  [Movement (str/blank? destination) (= "sunoco" company)]
  =>
  (swap! alerts #(conj % "missing destination")))

(defrule negative-volume
  "If the volume is negative flip the origin and destination."
  [Movement (neg? volume)]
  =>
  (swap! alerts #(conj % "negative volume")))

(defrule regular-gasoline
  "If the grade is regular gasoline, convert gallons to barrels."
  [Movement (= "regular" grade)]
  =>
  (swap! alerts #(conj % "regular gasoline")))

(defn apply-rules
  ""
  [movements]
  (->
    (mk-session 'rules-rule.transform)
    (insert-all movements)
    (fire-rules))
  (println alerts))
