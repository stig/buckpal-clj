(ns buckpal.account.domain.activity
  (:require [buckpal.account.domain.account-id :refer [AccountId]]
            [buckpal.account.domain.money :refer [Money]]))

(def ActivityId [:int {:min 1}])

(defn- validate-owner
  [{:keys [owner-account-id source-account-id target-account-id]}]
   (or (= owner-account-id source-account-id)
       (= owner-account-id target-account-id)))

(def Activity
  [:map
   [:id [:or :nil ActivityId]]
   [:owner-account-id AccountId]
   [:source-account-id AccountId]
   [:target-account-id AccountId]
   [:timestamp inst?]
   [:money Money]])

(defn activity
  "Create a new activity"
  {:malli/schema [:=> [:cat [:* [:cat :keyword [:or AccountId Money]]]] Activity]}
  [& {:keys [owner-account-id source-account-id target-account-id money] :as activity}]
  (when-not (validate-owner activity)
    (throw (IllegalStateException. "Owner account id must match either source or target")))
  {:id nil
   :timestamp (java.util.Date.)
   :owner-account-id owner-account-id
   :source-account-id source-account-id
   :target-account-id target-account-id
   :money money})
