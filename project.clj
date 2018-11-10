(defproject rules_rule "0.1.0-SNAPSHOT"
  :description "Example data ingestion pipeline with clara-rules."
  :url "https://github.com/anthonyshull/rules-rule"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.csv "0.1.4"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-time "0.15.0"]
                 [csv-map "0.1.2"]
                 [com.cerner/clara-rules "0.19.0"]])