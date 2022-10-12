(ns buckpal.account.application.port.in.send-money-command
  (:require [buckpal.account.domain.account-id :refer [AccountId]]
            [buckpal.account.domain.money :refer [Money]]
            [malli.core :as m]))

(def SendMoneyCommand
  [:and
   [:map
    [:source-account-id AccountId]
    [:target-account-id AccountId]
    [:money [:and Money [:> 0]]]]
   [:fn (fn target-differs-from-source [{:keys [source-account-id target-account-id]}]
          (not (= source-account-id target-account-id)))]])

(defn make [source-account-id target-account-id money]
  (let [smc {:source-account-id source-account-id
             :target-account-id target-account-id
             :money money}]
    (when-not (m/validate SendMoneyCommand smc)
      (throw (IllegalArgumentException. "SendMoneyCommand failed validation")))
    smc))
