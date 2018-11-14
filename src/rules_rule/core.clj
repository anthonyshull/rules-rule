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
    transform/apply-rules))

(defn -main
  ""
  [& args]
  (process (first args)))
