(ns rules-rule.load
  (:require [clojure.data.json :as json]
            [rules-rule.movement :as m])
  (:gen-class))

(def movements->json json/write-str)
