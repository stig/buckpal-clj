(ns user
  (:require
   [buckpal.account.domain.account :as a]
   [buckpal.account.domain.activity :as act]
   [buckpal.account.domain.activity-window :as aw]
   [clojure.tools.namespace.repl :refer [refresh]]
   [malli.core :as m]
   [malli.dev :as dev]
   [malli.dev.pretty :as pretty]
   [malli.generator :as mg]
   [malli.util :as mu]))

(defn start! []
  (refresh)
  (dev/start! {:report (pretty/thrower)}))
