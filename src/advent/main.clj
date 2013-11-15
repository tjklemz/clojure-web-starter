(ns advent.main)
(use 'stencil.core)
(use 'compojure.core)
(use 'ring.adapter.jetty)

(defn split [l n]
  (if (> n 0) (cons (first l) (split (rest l) (- n 1)))))

(defn get-month []
  (Integer. (.format (java.text.SimpleDateFormat. "dd") (java.util.Date.))))

(def videos [{:name "Bob Harpford tells of a unique Santa Experience" :url "//www.youtube.com/embed/Awf45u6zrP0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :url "//www.youtube.com/embed/Awf45u6zrP0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :url "//www.youtube.com/embed/Awf45u6zrP0"}
             {:name "Bob Harpford tells of a unique Egg Nog Experience" :url "//www.youtube.com/embed/Awf45u6zrP0"}])

(defn get-videos []
  {:videos (split videos 3)})

(defn main-handler []
    (render-file "main" (get-videos)))

(defroutes my-app
    (GET "/" [] (main-handler))
    (GET "/hi" [] "blah"))

(defn -main [& args]
    (run-jetty #'my-app {:port 3001}))
