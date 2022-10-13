(ns buckpal.account.application.port.out.load-account-port
  (:require [buckpal.account.domain.account-id :as account-id]
            [buckpal.account.domain.account :as account]))

(defprotocol LoadAccountPort
  (-load-account [this account-id baseline-date]
    "Load an account with activities since the baseline instant"))

(defn load-account
  {:malli/schema [:=> [:cat any? account-id/AccountId inst?] account/Account]}
  [this account-id instant]
  (or (.-load-account this account-id instant)
      (throw (Exception. "Account not found"))))
