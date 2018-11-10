(ns rules-rule.load
  (:require [clojure.data.json :as json])
  (:gen-class))

(def movements->json json/write-str)
