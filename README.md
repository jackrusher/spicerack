![The spicerack logo](https://cloud.githubusercontent.com/assets/220188/20091210/d20e41e6-a591-11e6-9411-94852705097b.png)

Sometimes, while making tasty computer programs, one needs a place to
store labeled containers of data. Spicerack is that kind of place.

This library is a Clojure wrapper for [MapDB](http://www.mapdb.org) —
a fast, disk-persistent data-structures library. Like many Clojure
wrappers around Java libraries, this one is incomplete. MapDB supports
several data structures (`Map`, `Set` and `List`, implemented with
trees and hashes), and can do fun things with them, like creating a
hash table that acts as a cache with automatic eviction and change
listeners. This wrapper doesn't support any of that. It just provides
an idiomatic way to store something like a Clojure `hash-map` on disk.

I plan to add the other data types over time, as I need them for my
own projects. In the meantime, these features are well-tested,
deployed in production, and quite useful.

## Usage 

``` clojure
[spicerack "0.1.6"]
```

There are only a handful of functions in this wrapper. It provides
`open-database` and `close` (though it's best to use clojure's
`with-open` macro to handle closing), `assoc!`, `dissoc!`, and
`update!` for mutation, on top of which `clojure.core`'s `get`
function can be used to access the value of a given key.

``` clojure
(require '[spicerack.core :refer [open-database open-hashmap assoc! update!]])

(with-open [db (open-database "./baking-db")]
  (let [ingredients (open-hashmap db "ingredient-hashmap")]
    (assoc! ingredients :apple-pie [:flour :butter :sugar :apples])
    ;;=> [:flour :butter :sugar :apples]
    (update! ingredients :apple-pie conj :cinnamon)))
    ;;=> [:flour :butter :sugar :apples :cinnamon]
```

In addition, Spicerack's `hash-map` implementation can be used like a
normal Clojure `hash-map` with sequence functions such as `map` and
`reduce`:

``` clojure
(with-open [db (open-database test-filename)]
  (let [hm (open-hashmap db "test-hashmap")]
    (doseq [[a b] (map vector (range 10) (range 0 1.0 0.1))]
      (assoc! hm a b))

    (get hm 1)
    ;;=> 0.1

    (get hm 47 :hi)
    ;;=> :hi

    (reduce (fn [acc [k v]] (+ acc v)) 0 hm)
    ;;=> 4.5

    (reduce (fn [acc [k v]] (+ acc k)) 0 hm)
    ;;=> 45

    (mapv (fn [[k v]]
            {:key k
             :val v})
          hm)
    ;;=>
    [{:key 0, :val 0}
     {:key 8, :val 0.7999999999999999}
     {:key 5, :val 0.5}
     {:key 3, :val 0.30000000000000004}
     {:key 2, :val 0.2}
     {:key 6, :val 0.6}
     {:key 7, :val 0.7}
     {:key 1, :val 0.1}
     {:key 9, :val 0.8999999999999999}
     {:key 4, :val 0.4}]))
```

Note that returning a lazy sequence from inside of a `with-open`
block, then trying to realize that sequence outside of the block, will
cause an exception to be thrown.

There are more examples in the test suite. In additon, there is
automatically generated [codox](https://github.com/weavejester/codox)
API documentation [here](https://jackrusher.github.io/spicerack/).

## License 

Copyright © 2016-2019 Jack Rusher

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
