(ns com.jacobchaffin.site.html
  (:require [clojure.string :as string]
            [environ.core :refer [env]]
            [hiccup.def :refer [defhtml]]
            [hiccup.element :refer [javascript-tag]]
            [hiccup.page :refer [html5 include-css include-js]]))


(defn -head [page-title & stylesheets]
  `[:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1"}]
   [:title ~page-title]
   ~@stylesheets])

(defn html-head [page-title]
  (-head page-title
    (include-css (if (= (:environment env) "dev")
                   "/css/site.css"
                   "/css/site.min.css"))))

(defn page-template [js-file & [page-title system-init-fn]]
  (html5
    (html-head page-title)
    [:body
     [:div#app]
     (include-js js-file)
     (when system-init-fn
       (apply #'javascript-tag system-init-fn))]))

(defn home-page
  ([page-title]
   (partial #'page-template "/js/site.js" page-title))
  ([page-title js-file]
   (partial #'page-template js-file page-title)))



