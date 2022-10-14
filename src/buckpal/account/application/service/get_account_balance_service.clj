(ns buckpal.account.application.service.get-account-balance-service
  (:require [buckpal.account.application.port.in.account-balance-query :as abq]
            [buckpal.account.application.port.out.load-account-port :as lap]
            [buckpal.account.domain.account :as account])
  (:import (java.util Date)))


(defrecord GetAccountBalanceService [load-account-adapter]
  abq/AccountBalanceQuery
  (-get-account-balance [_ account-id]
    (-> (lap/load-account load-account-adapter account-id (Date.))
        account/calculate-balance)))

(def GABS [:fn (partial satisfies? GetAccountBalanceService)])

(defn make
  {:malli/schema [:=> [:cat lap/LAPProtocol] GABS]}
  [load-account-adapter]
  (GetAccountBalanceService. load-account-adapter))
