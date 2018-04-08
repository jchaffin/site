(ns com.jacobchaffin.site.core
  (:require [goog.dom :as gdom]
            [om.next :as om :refer [defui]]
            [om.dom :as dom]))

(enable-console-print!)

(defn on-js-reload []
  ;; Uncomment to force rerendering
  ;; (swap! app-state update-in [:__figwheel_counter] (fnil inc 0))
  )

(defui RootComponent
  Object
  (render [this] (dom/div nil "The root of all things...")))

(def root-component (om/factory RootComponent))

(js/ReactDOM.render (root-component) (gdom/getElement "app"))





