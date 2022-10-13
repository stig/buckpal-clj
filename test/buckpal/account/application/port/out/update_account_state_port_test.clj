(ns buckpal.account.application.port.out.update-account-state-port-test
  (:require [buckpal.account.application.port.out.update-account-state-port :as uasp]
            [clojure.test :as t]
            [malli.generator :as mg]
            [buckpal.account.domain.account :as account]))

(t/deftest update-activities
  (t/testing "it forwards to implementing adapter"
    (let [log (atom [])
          adapter (reify uasp/UpdateAccountStatePort
                    (-update-activities [_ account]
                      (swap! log conj account)
                      nil))
          account (mg/generate account/Account)]
      (uasp/update-activities adapter account)
      (t/is (= [account] @log)))))
