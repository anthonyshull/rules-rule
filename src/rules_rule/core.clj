(ns rules-rule.core
  (:require [rules-rule.extract :as extract]
            [rules-rule.transform :as transform]
            [rules-rule.load :as load])
  (:gen-class))

(defn process
  "Extract, transform and load movements data."
  [file]
  (->>
    file
    extract/csv->movements
    transform/apply-rules
    load/movements->json))

(defn -main
  ""
  [& args]
  (->
    args
    first
    process
    println))
