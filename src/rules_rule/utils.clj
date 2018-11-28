(ns rules-rule.utils)

(defn add-to-map
  "Add the element `e` in the sequence to the map `m` with the key `k`."
  [k m e]
  (assoc m (k e) e))

(defn seq-to-map
  "Convert a sequence `s` to a map with the given keyword `k` as the key in the new map."
  [s k]
  (reduce (partial add-to-map k) {} s))