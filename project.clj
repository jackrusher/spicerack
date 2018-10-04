(defproject spicerack "0.1.6"
  :description "A Clojure wrapper for MapDB, which is a fast disk-persistent data-structure library."
  :url "https://github.com/jackrusher/spicerack"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.eclipse.collections/eclipse-collections-api "7.1.1"]
                 [org.eclipse.collections/eclipse-collections "7.1.1"]
                 [org.eclipse.collections/eclipse-collections-forkjoin "7.1.1"]
                 [com.google.guava/guava "19.0"]
                 [org.jetbrains.kotlin/kotlin-stdlib "1.2.71"] ; see exclusion
                 [org.mapdb/mapdb "3.0.7"
                  :exclusions [org.eclipse.collections/eclipse-collections-api
                               org.eclipse.collections/eclipse-collections
                               org.eclipse.collections/eclipse-collections-forkjoin
                               com.google.guava/guava
                               ;; Use specific Kotlin version (above)
                               ;; instead of MapDB's version range:
                               org.jetbrains.kotlin/kotlin-stdlib]]]
  :profiles {:codox {:dependencies [[codox-theme-rdash "0.1.2"]]
                     :plugins [[lein-codox "0.10.3"]]
                     :codox {:project {:name "spicerack"}
                             :metadata {:doc/format :markdown}
                             :themes [:rdash]
                             :output-path "gh-pages"}}}
  :aliases {"codox" ["with-profile" "codox,dev" "codox"]}
  :deploy-repositories [["releases" :clojars]])
