(ns spicerack.core-test
  (:require [clojure.test :refer :all]
            [spicerack.core :refer :all]))

(def test-filename "./test-db")

(defn cleanup
  "Removes the test database. To be used before and after testing."
  []
  (let [test-db (java.io.File. test-filename)]
    (when (.exists test-db)
      (.delete test-db))))

(cleanup)

(deftest parameter-checks
  (testing "catche bad param values"
    (is (= :asserted)
        (try
          (open-database test-filename :x)
          (catch java.lang.AssertionError e :asserted)))
    (is (= :asserted)
        (try
          (open-hashmap :a :b :c)
          (catch java.lang.AssertionError e :asserted)))))

(deftest file-backed-hashmap
  (testing "basic operations on file-backed hashmap"
    (let [test-vector (mapv vector (range 1 10) (range 100 110))]
      (with-open [db (open-database test-filename)]
        (let [hm (open-hashmap db "test-hashmap")]
          ;; non-existent keys are nil
          (is (= nil (get hm :a-nonexistent-key)))
          ;; put! returns the new value on success
          (is (= :there (put! hm :hi :there)))
          (is (= :there-mom (update! hm :hi #(keyword (str (name %) "-mom")))))
          ;; how about a clojure datastructure as value?
          (is (= {:a 1 :b 2} (put! hm :test-map {:a 1 :b 2})))
          (is (= {:a 1 :b 2} (get hm :test-map)))
          (is (= {:a 1 :b 2
                  :c 3 :d 4} (update! hm :test-map #(merge % {:c 3 :d 4}))))
          ;; is it still there?
          (is (= {:a 1 :b 2
                  :c 3 :d 4} (get hm :test-map)))
          ;; remove returns the removed value
          (is (= {:a 1 :b 2
                  :c 3 :d 4} (remove! hm :test-map)))
          ;; is it still gone?
          (is (= nil (get hm :test-map)))
          ;; how about a vector?
          (is (= test-vector (put! hm :test-vector test-vector)))
          ;; still there?
          (is (= test-vector (get hm :test-vector)))))
      ;; now the db is closed, re-open it read-only and read
      (with-open [db (open-database test-filename :read-only? true)]
        (let [hm (open-hashmap db "test-hashmap" :read-only? true)]
          ;; still there?
          (is (= test-vector (get hm :test-vector))))))
    ;; test-db file is really there to delete
    (is (= true (cleanup)))))


