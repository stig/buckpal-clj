(ns buckpal.account.application.port.in.send-money-use-case-test
  (:require
   [buckpal.account.application.port.in.send-money-command :as smc]
   [buckpal.account.application.port.in.send-money-use-case :as smuc]
   [clojure.test :refer [deftest is testing]]
   [malli.generator :as mg]))

(deftest send-money
  (testing "it round-trips to implementing adapter"
    (let [activity (atom [])
          command (mg/generate smc/SendMoneyCommand)
          adapter (reify smuc/SendMoneyUseCase
                    (-send-money [_ cmd]
                      (swap! activity conj cmd)
                      true))]
      (smuc/send-money adapter command)
      (is (= [command] @activity)))))
