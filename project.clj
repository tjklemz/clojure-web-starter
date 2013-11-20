(defproject Advent "1.0.0"
  :dependencies [[stencil "0.3.2"]
                 [compojure "1.1.6"]
                 [ring "1.2.1"]
                 [org.clojure/clojure "1.5.1"]]
  :plugins [[lein-ring "0.8.8"]]
  :ring {:handler advent.main/my-app}
  :resource-paths ["templates"]
  :uberjar-name "lsAdvent.jar"
  :min-lein-version "2.0.0")

