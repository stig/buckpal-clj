(ns buckpal.account.domain.activity-window-test
  (:require [clojure.test :refer [deftest testing is]]
            [buckpal.account.domain.activity :as a]
            [buckpal.account.domain.activity-window :as aw]
            [malli.generator :as mg]))

(def start-date #inst "2000")
(def in-between-date #inst "2010")
(def end-date #inst "2020")


(deftest activity-window
  (testing "it can be generated"
    (is (mg/generate aw/ActivityWindow))))

(deftest start-timestamp
  (testing "it throws when activity window is empty"
    (is (thrown? IllegalStateException (aw/start-timestamp []))))

  (testing "it calculates start timestamp"
    (let [[one two three] (mg/sample a/Activity)
          one (assoc one :timestamp start-date)
          two (assoc two :timestamp end-date)
          three (assoc three :timestamp in-between-date)]
      (is (= start-date (aw/start-timestamp [one two three]))))))

(deftest end-timestamp
  (testing "it throws when activity window is empty"
    (is (thrown? IllegalStateException (aw/end-timestamp []))))

  (testing "it calculates end timestamp"
    (let [[one two three] (mg/sample a/Activity)
          one (assoc one :timestamp start-date)
          two (assoc two :timestamp end-date)
          three (assoc three :timestamp in-between-date)]
      (is (= end-date (aw/end-timestamp [one two three]))))))

(deftest calculate-balance
  (testing "it calculates balance"
    (let [[one two three] (mg/sample a/Activity)
          one (assoc one :source-account-id 1 :target-account-id 2 :money 999)
          two (assoc two :source-account-id 1 :target-account-id 2 :money 1)
          three (assoc three  :source-account-id 2 :target-account-id 1 :money 500)
          activity-window [one two three]]
      (is (= -500 (aw/calculate-balance activity-window 1)))
      (is (= 500 (aw/calculate-balance activity-window 2))))))
