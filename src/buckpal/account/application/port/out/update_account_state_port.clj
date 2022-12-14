(ns buckpal.account.application.port.out.update-account-state-port
  (:require [buckpal.account.domain.account :as account]))

(defprotocol UpdateAccountStatePort
  (-update-activities [this account]
    "Ensure activities are persisted if they are not already"))

(def UASPProtocol
  [:fn (partial satisfies? UpdateAccountStatePort)])

(defn update-activities
  {:malli/schema [:=> [:cat UASPProtocol account/Account] :nil]}
  [this account]
  (.-update-activities this account))
