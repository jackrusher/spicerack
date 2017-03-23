![The spicerack logo](https://cloud.githubusercontent.com/assets/220188/20091210/d20e41e6-a591-11e6-9411-94852705097b.png)

Sometimes, while making tasty computer programs, one needs a place to
store labeled containers of data. Spicerack is that kind of place.

This is a Clojure wrapper for [MapDB](http://www.mapdb.org) — a fast,
disk-persistent data-structures library. Like many wrappers around
Java libraries, this one is woefully incomplete. MapDB supports
several data structures (Map, Set and List, implemented with trees and
hashes), and can do fun things with them, like creating a hash table
that acts as a cache with automatic eviction and change
listeners. This wrapper doesn't support any of that. It just provides
an idiomatic way to store something like a Clojure `hash-map` on disk.

I plan to add the other data types over time, as I need them for my
own projects. In the meantime, these bits are well-tested and already
very useful.

## Usage 

``` clojure
[spicerack "0.1.3"]
```

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

See the tests for more examples!

## License 

Copyright © 2016-2017 Jack Rusher

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
