(ns user
  (:require [com.stuartsierra.component :as component]
            [com.jacobchaffin.site.system :refer [dev-system]]
            [figwheel-sidecar.system :as fw-sys]))

(declare system)

(defn init []
  (alter-var-root #'system
    (constantly (dev-system))))

(defn start []
  (when (not (empty? system))
    (alter-var-root #'system component/start)))

(defn stop []
  (when (not (empty? system))
    (alter-var-root #'system component/stop)))

(defn go []
  (init)
  (start))

(defn reset []
  (stop)
  (alter-var-root #'system {})
)

(defn cljs-repl []
  (fw-sys/cljs-repl (:figwheel-system system)))
