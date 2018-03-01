(ns spicerack.core-test
  (:refer-clojure :exclude [assoc! dissoc!])
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
  (testing "catches bad param values"
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
          ;; assoc! returns the new value on success
          (is (= :there (assoc! hm :hi :there)))
          (is (= :there-mom (update! hm :hi #(keyword (str (name %) "-mom")))))
          ;; how about a clojure datastructure as value?
          (is (= {:a 1 :b 2} (assoc! hm :test-map {:a 1 :b 2})))
          (is (= {:a 1 :b 2} (get hm :test-map)))
          (is (= {:a 1 :b 2
                  :c 3 :d 4} (update! hm :test-map #(merge % {:c 3 :d 4}))))
          ;; is it still there?
          (is (= {:a 1 :b 2
                  :c 3 :d 4} (get hm :test-map)))
          ;; remove returns the removed value
          (is (= {:a 1 :b 2
                  :c 3 :d 4} (dissoc! hm :test-map)))
          ;; is it still gone?
          (is (= nil (get hm :test-map)))
          ;; how about a vector?
          (is (= test-vector (assoc! hm :test-vector test-vector)))
          ;; still there?
          (is (= test-vector (get hm :test-vector)))
          ;; update! accepts partial function arguments
          (is (= {} (assoc! hm :numbers {})))
          (is (= {:x 5} (update! hm :numbers assoc :x 5)))))
      ;; now the db is closed, re-open it read-only and read
      (with-open [db (open-database test-filename :read-only? true)]
        (let [hm (open-hashmap db "test-hashmap" :read-only? true)]
          ;; still there?
          (is (= test-vector (get hm :test-vector))))))
    ;; test-db file is really there to delete
    (is (= true (cleanup)))))

(deftest database-options
  (testing "By default read-only? is false"
    (with-open [db (open-database test-filename)]
      (is (not (.isReadOnly (.getStore db))))))

  (testing "Setting read-only? to true"
    (with-open [db (open-database test-filename :read-only? true)]
      (is (.isReadOnly (.getStore db))))))
