(ns buckpal.account.application.port.out.account-locking-test
  (:require
   [buckpal.account.application.port.out.account-locking :as al]
   [buckpal.account.domain.account-id :as account-id]
   [clojure.test :as t]
   [malli.generator :as mg]))

(t/deftest lock-account
  (t/testing "it round-trips to implementing adapter"
    (let [log (atom {})
          adapter (reify al/AccountLocking
                    (-lock-account [_ account-id]
                      (swap! log assoc account-id :locked)
                      nil))
          account-id (mg/generate account-id/AccountId)]
      (al/lock-account adapter account-id)
      (t/is (= {account-id :locked} @log)))))

(t/deftest unlock-account
  (t/testing "it round-trips to implementing adapter"
    (let [log (atom {})
          adapter (reify al/AccountLocking
                    (-unlock-account [_ account-id]
                      (swap! log assoc account-id :unlocked)
                      nil))
          account-id (mg/generate account-id/AccountId)]
      (al/unlock-account adapter account-id)
      (t/is (= {account-id :unlocked} @log)))))
