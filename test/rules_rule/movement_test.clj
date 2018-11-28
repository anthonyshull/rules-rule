(ns rules-rule.movement-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as spec]
            [rules-rule.movement :refer :all]))

(def mvmt-old
  {:date "2018-10-31" :from "houston" :to "dallas" :company "valero" :grade "premium" :volume "100"})

(def mvmt-new
  {:date "2018-11-01" :origin "houston" :destination "dallas" :company "valero" :grade "premium" :volume "100"})

(deftest map->movement-test
  (let [movement-old (map->movement mvmt-old)
        movement-new (map->movement mvmt-new)]
    (testing "map->movement-old"
      (is (spec/valid? :rules-rule.movement/movement movement-old)))
    (testing "map->movement-new"
      (is (spec/valid? :rules-rule.movement/movement movement-new)))))
