(ns com.jacobchaffin.site.application
  (:require [compojure.route :as route]
            [compojure.core :refer [GET routes]]
            [compojure.response :refer [render]]
            [ring.middleware.defaults :refer :all]))

;; Note the use of the `compojure.core.routes` function,
;; as oppposed to the static defoutes macro. This is because
;; application state is assumed to be received as an argument in
;; the reloaded workflow.
(defn app-routes
  "Application-level compojure handler for routing requests."
  [app-component]
  (routes
    (GET "/" [req] (render app-component req))
    (route/not-found "Page Not Found")))

(defn app
  "Middleware stack for rendering the site."
  [handler]
  (-> handler
    (wrap-defaults site-defaults)))
