(ns buckpal.account.application.port.in.send-money-use-case
  (:require [buckpal.account.application.port.in.send-money-command :refer [SendMoneyCommand]]))

(defprotocol SendMoneyUseCase
  (-send-money [this command] "Send money"))

(def SMUCProtocol
  [:fn (partial satisfies? SendMoneyUseCase)])

(defn send-money
  "Send money"
  {:malli/schema [:=> [:cat SMUCProtocol SendMoneyCommand] boolean?]}
  [this command]
  (.-send-money this command))
