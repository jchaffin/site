(ns com.jacobchaffin.site.system
  (:require [com.stuartsierra.component :as component]
            [com.jacobchaffin.site.application :refer [app-routes app]]
            [com.jacobchaffin.site.html :refer [home-page]]
            [com.jacobchaffin.site.server :refer [new-webserver]]
            [environ.core :refer [env]]
            [figwheel-sidecar.system :as fw-sys]
            [figwheel-sidecar.config :as fw-config]
            [ring.middleware.defaults :refer :all]))

(def figwheel-config (fw-config/fetch-config))

(defn dev-system
  "System component map for development environment."
  []
  (component/system-map
    :port (-> env :web-port read-string int)
    :handler (-> (home-page "Welcome") app-routes app)
    :figwheel-system (fw-sys/figwheel-system figwheel-config)
    :css-watch (fw-sys/css-watcher {:watch-paths (get-in figwheel-config [:data :figwheel-options :css-dirs])})
    :app (component/using
           (new-webserver)
           [:port :handler])))

(defn prod-system
  "System component map for production environment."
  [config-options])
