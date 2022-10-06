(ns buckpal.account.domain.account-test
  (:require [clojure.test :refer [deftest testing is]]
            [buckpal.account.domain.account :as a]
            [buckpal.account.domain.activity :as act]
            [malli.generator :as mg]))

(deftest account
  (testing "it can be generated"
    (is (mg/generate a/Account))))

(defn- activity [src target money]
  (act/activity :owner-account-id 1 :source-account-id src :target-account-id target :money money))

(deftest calculate-balance
  (testing "it can calculate balance of account with no activity"
    (is (= 0 (a/calculate-balance {:id 1 :baseline-balance 0 :activity-window []})))

    (testing "it takes baseline into account"
      (is (= 10 (a/calculate-balance {:id 1 :baseline-balance 10 :activity-window []})))))

  (testing "it uses activity if present"
    (is (= 10 (a/calculate-balance {:id 1 :baseline-balance 0 :activity-window [(activity 2 1 10)]})))))

