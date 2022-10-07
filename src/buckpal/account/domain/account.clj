(ns buckpal.account.domain.account
  (:require [buckpal.account.domain.money :refer [Money]]
            [buckpal.account.domain.account-id :refer [AccountId]]
            [buckpal.account.domain.activity :as a]
            [buckpal.account.domain.activity-window :as aw]))

(def Account
  [:map
   [:id [:or :nil AccountId]]
   [:baseline-balance Money]
   [:activity-window aw/ActivityWindow]])

(defn account
  {:malli/schema [:function
                  [:=> [:cat Money aw/ActivityWindow] Account]
                  [:=> [:cat [:or :nil AccountId] Money aw/ActivityWindow] Account]]}
  ([baseline-balance activity-window]
   (account nil baseline-balance activity-window))
  ([id baseline-balance activity-window]
   {:id id
    :baseline-balance baseline-balance
    :activity-window activity-window}))

(defn calculate-balance
  "Calculates the balance for an account"
  {:malli/schema [:=> [:cat Account] Money]}
  [account]
  (+ (:baseline-balance account)
     (aw/calculate-balance (:activity-window account)
                           (:id account))))

(defn- may-withdraw?
  [account money]
  (nat-int? (- (calculate-balance account) money)))

(defn withdraw
  [account money target-account-id]
  (when-not (:id account)
    (throw (IllegalStateException. "Cannot withdraw when missing account-id")))
  (when (may-withdraw? account money)
    (update account :activity-window conj (a/activity :owner-account-id (:id account)
                                                      :source-account-id (:id account)
                                                      :target-account-id target-account-id
                                                      :money money))))
