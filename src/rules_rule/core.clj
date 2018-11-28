(ns rules-rule.core
  "Project demonstrating the use of Clara rules to ingest data."
  (:require [rules-rule.extract :as extract]
            [rules-rule.transform :as transform]
            [rules-rule.load :as load])
  (:gen-class))

(defn process
  "Given a `file` extract, transform, and load the movements data it represents."
  [file]
  (->> file
       extract/csv->movements
       transform/apply-rules
       load/movements->json))

(defn -main
  "Pass command-line `args` to the processor."
  [& args]
  (-> args
      first
      process
      println))
