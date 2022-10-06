(ns buckpal.account.domain.activity-window
  (:require [buckpal.account.domain.activity :refer [Activity]]
            [buckpal.account.domain.money :refer [Money]]
            [buckpal.account.domain.account-id :refer [AccountId]]))

(def ActivityWindow
  [:sequential Activity])

(defn start-timestamp
  "Returns the earliest timestamp in an activity window.
  Throws if there's no activities in the window."
  {:malli/schema [:=> [:cat ActivityWindow] inst?]}
  [activities]
  (when-not (seq activities)
    (throw (IllegalStateException. "Cannot calculate start timestamp of empty activity window")))
  (->> activities
       (map :timestamp)
       sort
       first))

(defn end-timestamp
  "Returns the last timestamp in an activity window.
  Throws if there's no activities in the window."
  {:malli/schema [:=> [:cat ActivityWindow] inst?]}
  [activities]
  (when-not (seq activities)
    (throw (IllegalStateException. "Cannot calculate end timestamp of empty activity window")))
  (->> activities
       (map :timestamp)
       sort
       last))

(defn calculate-balance
  "Returns the balance for the account-id in the activity window"
  {:malli/schema [:=> [:cat ActivityWindow AccountId] Money]}
  [activities account-id]
  (let [deposits (->> activities
                      (filter #(= account-id (:target-account-id %)))
                      (map :money)
                      (reduce + 0))
        withdrawals (->> activities
                         (filter #(= account-id (:source-account-id %)))
                         (map :money)
                         (reduce + 0))]
    (- deposits withdrawals)))
