(ns buckpal.account.application.port.in.account-balance-query
  (:require [buckpal.account.domain.account-id :refer [AccountId]]
            [buckpal.account.domain.money :refer [Money]]))

(defprotocol AccountBalanceQuery
  (-get-account-balance [this account-id]
    "Return the account balance for an account"))

(def ABQProtocol
  [:fn (partial satisfies? AccountBalanceQuery)])

(defn get-account-balance
  "Get balance for an account"
  {:malli/schema [:=> [:cat ABQProtocol AccountId] Money]}
  [this account-id]
  (.-get-account-balance this account-id))
