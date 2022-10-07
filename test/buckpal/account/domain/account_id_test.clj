(ns buckpal.account.domain.account-id-test
  (:require [buckpal.account.domain.account-id :as a]
            [clojure.test :refer [deftest testing is]]
               [malli.generator :as mg]))

(deftest AccountId
  (testing "it can be generated"
    (is (mg/generate a/AccountId)))

  (testing "it is always positive"
    (is (every? pos-int? (mg/sample a/AccountId)))))
