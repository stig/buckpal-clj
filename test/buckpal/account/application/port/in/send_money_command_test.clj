(ns buckpal.account.application.port.in.send-money-command-test
  (:require
   [buckpal.account.application.port.in.send-money-command :as sut]
   [clojure.test :refer [deftest is testing]]))

(deftest make
  (testing "it accepts valid parameters"
    (is (sut/make 1 2 3)))

  (testing "it rejects source and target being identical"
    (is (thrown? IllegalArgumentException (sut/make 1 1 3))))

  (testing "it rejects negative money"
    (is (thrown? IllegalArgumentException (sut/make 1 2 -3))))

  (testing "it rejects incorrect types"
    (is (thrown? IllegalArgumentException (sut/make "1" 2 3)))
    (is (thrown? IllegalArgumentException (sut/make 1 "2" 3)))
    (is (thrown? IllegalArgumentException (sut/make 1 2 "3")))
    (is (thrown? IllegalArgumentException (sut/make 1.0 2 3)))
    (is (thrown? IllegalArgumentException (sut/make 1 2.0 3)))
    (is (thrown? IllegalArgumentException (sut/make 1 2 3.0)))))
