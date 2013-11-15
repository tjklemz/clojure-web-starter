(ns advent.main)
(use 'stencil.core)
(use 'compojure.core)
(use 'ring.adapter.jetty)

(defn split [l n]
  (if (and (> n 0) (> (count l) 0))
    (cons (first l) (split (rest l) (- n 1)))))

(defn convert-date-from-format [format]
  (Integer. (.format (java.text.SimpleDateFormat. format) (java.util.Date.))))

(defn get-year []
  (convert-date-from-format "yyyy"))

(defn get-month []
  (convert-date-from-format "MM"))

(defn get-day-of-month []
  (convert-date-from-format "dd"))

(def videos [{:name "Bob Harpford tells of a unique Santa Experience" :url "//www.youtube.com/embed/Awf45u6zrP0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :url "//www.youtube.com/embed/Awf45u6zrP0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :url "//www.youtube.com/embed/Awf45u6zrP0"}
             {:name "Bob Harpford tells of a unique Egg Nog Experience" :url "//www.youtube.com/embed/Awf45u6zrP0"}])

(defn get-videos []
  {:videos (if (> (get-year) 2013) videos
             (if (> (get-month) 11) (split videos (get-day-of-month))
               nil))})

(defn main-handler []
    (render-file "main" (get-videos)))

(defroutes my-app
    (GET "/" [] (main-handler))
    (GET "/hi" [] "blah"))

(defn -main [& args]
    (run-jetty #'my-app {:port 3001}))
