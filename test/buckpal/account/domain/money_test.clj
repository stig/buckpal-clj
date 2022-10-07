(ns buckpal.account.domain.money-test
  (:require
   [buckpal.account.domain.money :as m]
   [clojure.test :refer [deftest is testing]]
   [malli.generator :as mg]))

(deftest Money
  (testing "it can be generated"
    (is (mg/generate m/Money))))
