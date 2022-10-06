(ns user
  (:require
   [clojure.tools.namespace.repl :refer [refresh]]
   [malli.core :as m]
   [malli.dev :as dev]
   [malli.dev.pretty :as pretty]
   [malli.generator :as mg]
   [malli.util :as mu]))

(defn start! []
  (refresh)
  (dev/start! {:report (pretty/thrower)}))
