(ns rules-rule.utils)

(defn add-to-map
  ""
  [k m i]
  (assoc m (k i) i))

(defn seq-to-map
  "Convert a sequence to a map with the given..."
  [s k]
  (reduce (partial add-to-map k) {} s))