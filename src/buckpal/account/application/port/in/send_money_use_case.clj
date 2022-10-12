(ns buckpal.account.application.port.in.send-money-use-case
  (:require [buckpal.account.application.port.in.send-money-command :refer [SendMoneyCommand]]))

(defprotocol SendMoneyUseCase
  (-send-money [this command] "Send money"))

(defn send-money
  "Send money"
  {:malli/schema [:=> [:cat any? SendMoneyCommand] boolean?]}
  [this command]
  (.-send-money this command))
