(ns buckpal.kaocha.hooks
  (:require
   [malli.dev.pretty :as pretty]
   [malli.instrument :as mi]))

(defn post-load [test-plan]
  (mi/collect! {:ns (all-ns)})
  (mi/instrument! {:report (pretty/thrower)})
  test-plan)
