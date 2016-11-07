# Spicerack

A Clojure wrapper for [MapDB](http://www.mapdb.org) — a fast,
disk-persistent data-structures library.

Like many wrappers around Java libraries, this one is woefully
incomplete. MapDB supports a number of data structures (HashMap, Set,
List, and so on), and can do some fun things with them, like creating
a hash table that acts as a cache with automatic eviction and change
listeners, and so on. This wrapper doesn't do any of that. It's just
provides an idiomatic way to store a `hash-map` on disk.

I plan to add the other data types over time, as I need them for my
own projects.

## Usage

There are only a handful of functions in this wrapper. It provides
`open-database` and `close` (though it's best to use clojure's
`with-open` macro), `put!`, `remove!`, `update!` and getting is done
by `clojure.core`'s `get` function.

``` clojure
(with-open [db (open-database "./baking-db")]
  (let [hm (open-hashmap db "ingredient-hashmap")]
    (put! hm :pie-ingredients [:flour :butter :sugar :apples])
    ;;=> [:flour :butter :sugar :apples]
    (update! hm :pie-ingredients #(conj % :cinnamon))))
    ;;=> [:flour :butter :sugar :apples :cinnamon]
```

See the tests for more examples!

## License

Copyright © 2016 Jack Rusher

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
