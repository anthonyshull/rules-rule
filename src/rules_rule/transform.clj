(ns rules-rule.transform
  (:require [clojure.string :as str]
            [clojure.math.numeric-tower :as math]
            [rules-rule.utils :as utils]
            [clara.rules :refer :all])
  (:import [rules_rule.movement Movement])
  (:gen-class))

(def mvmts 
  "An atom that tracks the state of rule applications on movements."
  (atom {}))

(defrule missing-destination
  "If the destination is missing and the company is Sunoco then the destination is Arlington."
  [?movement <- Movement (str/blank? destination) (= "sunoco" company)]
  =>
  (let [movement (get @mvmts (:uuid ?movement))
        new-destination "arlington"
        new-movement (assoc movement :destination new-destination)]
    (swap! mvmts #(assoc % (:uuid ?movement) new-movement))))

(defrule negative-volume
  "If the volume is negative flip the origin and destination and make the volume positive."
  [?movement <- Movement (neg? volume)]
  =>
  (let [movement (get @mvmts (:uuid ?movement))
        new-origin (:destination movement)
        new-destination (:origin movement)
        new-volume (math/abs (:volume movement))
        new-movement (assoc movement :origin new-origin :destination new-destination :volume new-volume)]
    (swap! mvmts #(assoc % (:uuid movement) new-movement))))

(defrule regular-gasoline
  "If the grade is regular gasoline, convert gallons to barrels."
  [?movement <- Movement (= "regular" grade)]
  =>
  (let [movement (get @mvmts (:uuid ?movement))
        new-volume (/ (:volume movement) 42)
        new-movement (assoc movement :volume new-volume)]
    (swap! mvmts #(assoc % (:uuid movement) new-movement))))

(defn apply-rules
  "Apply the rules in the namespace to the given movements."
  [movements]
  (reset! mvmts (utils/seq-to-map movements :uuid))
  (->
    movements
    (mk-session 'rules-rule.transform)
    (insert-all movements)
    (fire-rules))
  (vals @mvmts))
