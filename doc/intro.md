# Introduction to spicerack

There are only a handful of functions in this wrapper. It provides
`open-database` and `close` (though it's best to use clojure's
`with-open` macro to handle closing), `put!`, `remove!`, and
`update!`.  Getting is done with `clojure.core`'s `get` function.

``` clojure
(require '[spicerack.core :refer [open-database open-hashmap put! update!]])

(with-open [db (open-database "./baking-db")]
  (let [ingredients (open-hashmap db "ingredient-hashmap")]
    (put! ingredients :apple-pie [:flour :butter :sugar :apples])
    ;;=> [:flour :butter :sugar :apples]
    (update! ingredients :apple-pie conj :cinnamon)))
    ;;=> [:flour :butter :sugar :apples :cinnamon]
```
