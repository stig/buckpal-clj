(ns buckpal.account.domain.account-test
  (:require [clojure.test :refer [deftest testing is]]
            [buckpal.account.domain.account :as a]
            [buckpal.account.domain.activity :as act]
            [malli.generator :as mg]))

(deftest Account
  (testing "it can be generated"
    (is (mg/generate a/Account))))

(defn- activity [src target money]
  (act/activity :owner-account-id 1 :source-account-id src :target-account-id target :money money))

(deftest calculate-balance
  (testing "it calculates balance"
    (is (= 1555 (a/calculate-balance (a/account 1 555 [(activity 2 1 999)
                                                       (activity 2 1 1)]))))))

(deftest withdraw
  (let [account (a/account 1 555 [(activity 2 1 999)
                                  (activity 2 1 1)])]
    (testing "it can withdraw money"
      (let [withdrawn (a/withdraw account 555 99)]
        (is (some? withdrawn))
        (is (= 3 (count (:activity-window withdrawn))))
        (is (= 1000 (a/calculate-balance withdrawn)))))

    (testing "it can fail to withdraw money"
      (is (nil? (a/withdraw account 1556 99))))))

(deftest deposit
  (let [account (a/account 1 555 [(activity 2 1 999)
                                  (activity 2 1 1)])]
    (testing "it can deposit money"
      (let [deposited (a/deposit account 445 99)]
        (is (some? deposited))
        (is (= 3 (count (:activity-window deposited))))
        (is (= 2000 (a/calculate-balance deposited)))))))
