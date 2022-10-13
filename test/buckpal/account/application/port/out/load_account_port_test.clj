(ns buckpal.account.application.port.out.load-account-port-test
  (:require [buckpal.account.application.port.out.load-account-port :as lap]
            [clojure.test :as t]
            [malli.generator :as mg]
            [buckpal.account.domain.account :as account]
            [buckpal.account.domain.account-id :as account-id]))

(t/deftest load-account
  (let [account-id (mg/generate account-id/AccountId)
        account (mg/generate account/Account)
        baseline-instant (mg/generate inst?)
        accounts {account-id account}
        adapter (reify lap/LoadAccountPort
                  (-load-account [_ account-id baseline]
                    (t/is (= baseline-instant baseline))
                    (accounts account-id)))]
    (t/testing "it round-trips to implementing adapter"
      (t/is (= account (lap/load-account adapter account-id baseline-instant))))

    (t/testing "it throws if no account is found"
      (t/is (thrown-with-msg? Exception #"Account not found"
                              (lap/load-account adapter (inc account-id) baseline-instant))))))
