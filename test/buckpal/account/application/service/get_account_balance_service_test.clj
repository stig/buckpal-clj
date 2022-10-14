(ns buckpal.account.application.service.get-account-balance-service-test
  (:require [buckpal.account.application.service.get-account-balance-service :as sut]
            [clojure.test :as t]
            [buckpal.account.application.port.out.load-account-port :as lap]))

(t/deftest make
  (t/testing "it returns a service when passed a LoadAccount adapter"
    (t/is (sut/make (reify lap/LoadAccountPort))))

  (t/testing "it throws when passed anything else"
    (t/is (thrown? Exception (sut/make {})))))
