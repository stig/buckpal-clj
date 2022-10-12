(ns buckpal.account.application.port.out.account-locking
  (:require [buckpal.account.domain.account-id :as account-id]))

(defprotocol AccountLocking
  (-lock-account [this account-id] "Lock an account for updates")
  (-unlock-account [this account-id] "Unlock account to allow updates"))

(defn lock-account
  {:malli/schema [:=> [:cat any? account-id/AccountId] :nil]}
  [this account-id]
  (.-lock-account this account-id))

(defn unlock-account
  {:malli/schema [:=> [:cat any? account-id/AccountId] :nil]}
  [this account-id]
  (.-unlock-account this account-id))
