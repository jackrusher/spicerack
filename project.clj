(defproject spicerack "0.1.1"
  :description "A Clojure wrapper for MapDB, which is a fast, disk-persistent data-structure library."
  :url "https://github.com/jackrusher/spicerack"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.eclipse.collections/eclipse-collections-api "7.1.0"]
                 [org.eclipse.collections/eclipse-collections "7.1.0"]
                 [org.eclipse.collections/eclipse-collections-forkjoin "7.1.0"]
                 [com.google.guava/guava "19.0"]
                 [org.mapdb/mapdb "3.0.2"
                  :exclusions [org.eclipse.collections/eclipse-collections-api
                               org.eclipse.collections/eclipse-collections
                               org.eclipse.collections/eclipse-collections-forkjoin
                               com.google.guava/guava]]]
  :deploy-repositories [["releases" :clojars]])
