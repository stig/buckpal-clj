(ns buckpal.account.application.port.in.account-balance-query-test
  (:require
   [buckpal.account.application.port.in.account-balance-query :as abq]
   [buckpal.account.domain.account-id :refer [AccountId]]
   [buckpal.account.domain.money :refer [Money]]
   [clojure.test :refer [deftest is testing]]
   [malli.generator :as mg]))

(deftest get-account-balance
  (testing "it round-trips to implementing adapter"
    (let [log (atom [])
          balance (mg/generate Money)
          account-id (mg/generate AccountId)
          adapter (reify abq/AccountBalanceQuery
                    (-get-account-balance [_ account-id]
                      (swap! log conj account-id)
                      balance))]
      (is (= balance (abq/get-account-balance adapter account-id)))
      (is (= [account-id] @log)))))
