(ns advent.main)
(use 'stencil.core)
(use 'compojure.core)
(use 'ring.adapter.jetty)

(def videos {:videos [{:name "Princess Bride" :url "//www.youtube.com/embed/Awf45u6zrP0"}
                      {:name "Batman" :url "//www.youtube.com/embed/Awf45u6zrP0"}
                      {:name "Lord of the Rings: Fellowship of the Ring"}
                      {:name "Ferris Bueler" :class "current"}]})

(defn main-handler []
    (render-file "main" videos))

(defroutes my-app
    (GET "/" [] (main-handler))
    (GET "/hi" [] "blah"))

(defn -main [& args]
    (run-jetty #'my-app {:port 3001}))
