 (defproject com.jacobchaffin/site "0.1.0-SNAPSHOT"
   :description "source code for personal website"
   :url "https://github.com/jacobchaffin/site"
   :license {:name "Eclipse Public License"
             :url "https://www.eclipse.org/legal/epl-v10.html"}
   :dependencies
   [ ;; https://github.com/clojure/clojure
    [org.clojure/clojure "1.9.0-RC1"]
    ;; https://github.com/clojure/clojurescript
    [org.clojure/clojurescript "1.9.946"]
    ;; https://github.com/clojure/tools.namespace
    [org.clojure/tools.namespace "0.3.0-alpha4"]
    ;; https://github.com/clojure/core.async
    [org.clojure/core.async "0.4.474"]
    ;; https://github.com/clojure/test.check
    [org.clojure/test.check "0.10.0-alpha2"]
    ;; https://github.com/stuartsierra/component
    [com.stuartsierra/component "0.3.2"]
    ;; https://github.com/noprompt/garden
    [garden "1.3.3"]
    ;; https://github.com/weavejester/hiccup
    [hiccup "1.0.5"]
    ;; https://github.com/weavejester/environ
    [environ "1.1.0"]
    ;; https://my.datomic.com
    [com.datomic/datomic-pro "0.9.5697"
     :exclusions [com.google.guava/guava]]
    ;; https://github.com/google/guava
    [com.google.guava/guava "21.0"]
    ;; https://github.com/cognitect/transit-clj
    [com.cognitect/transit-clj "0.8.303"]
    ;; https://github.com/tonsky/datascript
    [datascript "0.16.4"]
    ;; https://github.com/weavejester/compojure/
    [compojure "1.6.0"]
    ;; https://github.com/ring-clojure/ring
    [ring "1.6.3"]
    ;; https://github.com/ring-clojure/ring-defaults
    [ring/ring-defaults "0.3.1"]
    ;; https://github.com/bertrandk/ring-gzip
    [bk/ring-gzip "0.2.1"]
    ;; https://github.com/pjlegato/ring.middleware.logger
    [radicalzephyr/ring.middleware.logger "0.6.0"]
    ;; https://github.com/sunng87/ring-jetty9-adapter
    [info.sunng/ring-jetty9-adapter "0.10.0"]
    ;; https://github.com/omcljs/om
    [org.omcljs/om "1.0.0-beta3"]
    ;; https://github.com/cemerick/pomegranate
    [com.cemerick/pomegranate "1.0.0"]]

   :plugins
   [ ;; https://github.com/emezeske/lein-cljsbuild
    [lein-cljsbuild "1.1.7" :exclusions [org.clojure/clojure]]
    ;; https://github.com/weavejester/environ/tree/master/lein-environ
    [lein-environ "1.1.0"]]

   :repositories [["my.datomic.com" {:url "https://my.datomic.com/repo" :creds :gpg}]]

   :jvm-opts ["--add-modules" "java.xml.bind"]

   :source-paths ["src/clj" "src/cljc"]

   :clean-targets ^{:protect false}
   [:target-path "resources/public/js/"]

   :jar-name "%s.jar"

   :uberjar-name "%s-standalone.jar"

   :cljsbuild
   {:builds
    {:min
     {:source-paths ["src/cljs" "src/cljc"]
      :compiler {:output-to "resources/public/js/site.min.js"
                 :main com.jacobchaffin.site.core
                 :output-dir "target"
                 :source-map-timestamp true
                 :optimizations :advanced
                 :pretty-print false}}
     :dev
     {:source-paths ["src/cljs" "src/cljc" "dev"]
      :figwheel {:websocket-host :js-client-host
                 :on-jsload com.jacobchaffin.site.core/on-js-reload
                 :auto-jump-to-source-on-error true}
      :compiler {:main com.jacobchaffin.site.core
                 :asset-path "js/compiled/out"
                 :output-to "resources/public/js/site.js"
                 :output-dir "resources/public/js/compiled/out"
                 :source-map-timestamp true
                 :optimizations :none
                 :verbose true}}}}

   :figwheel {:http-server-root "public"
              :server-ip "0.0.0.0"
              :server-port 3449
              :server-logfile "log/figwheel.log"
              :nrepl-port 7002
              :css-dirs ["resources/public/css"]
              :open-file-command "openin-emacs"}

   :profiles {:dev
              {:repl-options
               {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
               :dependencies 
               [ ;; https://github.com/clojure-emacs/piggieback
                [com.cemerick/piggieback "0.2.2"]
                ;; https://github.com/ring-clojure/ring/tree/master/ring-devel
                [ring/ring-devel "1.6.2"]
                ;; https://github.com/pallet/alembic
                [alembic "0.3.2"]
                ;; https://github.com/bhauman/lein-figwheel/tree/master/sidecar
                [figwheel-sidecar "0.5.15"]
                ;; https://github.com/weavejester/reloaded.repl
                [reloaded.repl "0.2.4"]
                ;; https://github.com/clojure/tools.nrepl
                [org.clojure/tools.nrepl "0.2.12"]]
               
               :plugins
               [ ;; https://github.com/bhauman/lein-figwheel
                [lein-figwheel "0.5.15"]
                ;; https://github.com/clojure-emacs/cider-nrepl
                [cider/cider-nrepl "0.17.0-SNAPSHOT"]
                ;; https://github.com/clojure-emacs/refactor-nrepl
                [refactor-nrepl "2.4.0-SNAPSHOT"
                 :exclusions [org.clojure/clojure]]]

               :source-paths ["dev"]
               :env {:environment "dev"
                     :web-port "3000"}}

              :uberjar
              {:source-paths ^:replace ["src/clj" "src/cljc"]
               :prep-tasks ["compile"
                            ["cljsbuild" "once" "min"]]
               :omit-source true
               :aot :all}})

