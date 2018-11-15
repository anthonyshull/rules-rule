(ns rules-rule.movement-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as spec]
            [rules-rule.movement :refer :all]))

; SPECS
(spec/def ::date string?)
(spec/def ::origin string?)
(spec/def ::destination string?)
(spec/def ::company string?)
(spec/def ::grade string?)
(spec/def ::volume number?)

(spec/def ::movement
  (spec/keys :req-un 
    [::date ::origin ::destination ::company ::grade ::volume]))

(def mvmt-old
  {:date "2018-10-31" :from "houston" :to "dallas" :company "valero" :grade "premium" :volume "100"})

(def mvmt-new
  {:date "2018-11-01" :origin "houston" :destination "dallas" :company "valero" :grade "premium" :volume "100"})

(deftest map->movement-test
  (let [movement-old (map->movement mvmt-old)
        movement-new (map->movement mvmt-new)]
    (testing "map->movement-old"
      (is (spec/valid? ::movement movement-old)))
    (testing "map->movement-new"
      (is (spec/valid? ::movement movement-new)))))
