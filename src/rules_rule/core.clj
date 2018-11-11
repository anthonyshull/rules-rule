(ns rules-rule.core
  (:require [rules-rule.extract :as e]
            [rules-rule.transform :as t]
            [rules-rule.load :as l])
  (:gen-class))

(defn process
  "Extract, transform and load movements data."
  [file]
  (->>
    file
    e/csv->movements
    t/apply-rules
    l/movements->json))

(defn -main
  ""
  [& args]
  (process (first args)))
