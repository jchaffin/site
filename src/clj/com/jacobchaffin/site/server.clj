(ns com.jacobchaffin.site.server
  (:require [com.jacobchaffin.site.html :refer [home-page]]
            [com.stuartsierra.component :as component]
            [ring.adapter.jetty9 :refer [run-jetty]] ; Change to default adapter if using java version <9
            [ring.middleware.defaults :refer :all]))

;; Webserver Component
(defrecord WebServer [port handler options]
  component/Lifecycle
  (start [component]
    (println ";; Starting Web Server...")
    (if (:server component)
      component
      (let [handler (if (fn? handler) handler (get component :handler))
            server (run-jetty handler (-> options
                                        (merge {:port port})
                                        (assoc :join? false)))]
        (assoc component :server server))))
  (stop [component]
    (println ";; Stopping WebServer...")
    (when-let [server (get component :server)]
      (.stop server)
      (assoc component :server nil))))

(defn new-webserver
  "Creates a new webserver instance. Takes up to three arguments:
  an integer port where the server should listen for clients, an options map,
  a custom handler function, and an options map which can be used to configure
  the Jetty instance. If options contains a port key, that value will be used unless
  explicit port value is also supplied, in which that value will take precedence."
  ([]
   (new-webserver nil nil {}))
  ([port]
   (new-webserver port nil {}))
  ([port handler]
   (new-webserver port handler {}))
  ([port handler options]
   (map->WebServer (into {} (filter second {:port port :handler handler :options options})))))
