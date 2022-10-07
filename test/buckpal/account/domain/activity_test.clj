(ns buckpal.account.domain.activity-test
  (:require [clojure.test :refer [deftest testing is]]
            [buckpal.account.domain.activity :as a]
            [malli.generator :as mg]
            [malli.core :as m]))

(deftest Activity
  (testing "it can be generated"
    (is (mg/generate a/Activity))))

(deftest activity
  (testing "it can be constructed"
    (is (m/validate a/Activity (a/activity 1 2 1 300)))))
