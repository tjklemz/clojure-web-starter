(ns advent.main)
(use 'stencil.core)
(use 'compojure.core)
(use 'ring.adapter.jetty)

(defn main-handler []
    render-string "Hi, there")

(defroutes my-app
    (GET "/" [] (main-handler)))

(defn -main [& args]
    (run-jetty #'my-app {:port 3001}))

