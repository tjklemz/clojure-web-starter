(ns advent.main)
(use 'stencil.core)
(use 'compojure.core)
(use 'compojure.route)
(use 'ring.adapter.jetty)

(stencil.loader/set-cache (clojure.core.cache/ttl-cache-factory {} :ttl 0))

(def end-of-videos 25)

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

(def videos [{:name "Bob Harpford tells of a unique Santa Experience" :day "01" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :day "02" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :day "03" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :day "04" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :day "05" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :day "06" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :day "07" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :day "08" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :day "09" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :day "10" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :day "11" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :day "12" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :day "13" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :day "14" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :day "15" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :day "16" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :day "17" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :day "18" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :day "19" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :day "20" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :day "21" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Reindeer Experience" :day "22" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Relatives Experience" :day "23" :url "//www.youtube.com/embed/Ak--L4bYly0"}
             {:name "Bob Harpford tells of a unique Egg Nog Experience" :day "24" :url "//www.youtube.com/embed/Ak--L4bYly0"}])

(def featured (videos 0))

(def nonfeatured (rest videos))

(defn get-revealed []
  (split nonfeatured (- (get-day-of-month) 1)))

(defn get-tile-range []
  (range (+ (get-day-of-month) 1) end-of-videos))

(defn get-videos []
  {:featured (featured :url)
   :videos (if (> (get-year) 2013) nonfeatured
             (if (and (> (get-month) 10) (> (get-day-of-month) 1)) (get-revealed)
               nil))
   :upcoming (get-tile-range)
  })

(defn main-handler []
    (render-file "main" (get-videos)))

(def root (str (System/getProperty "user.dir") "/public"))

(defroutes my-app
    (compojure.route/resources "/")
    (compojure.route/files "/" (do (println root) {:root root}))
    (GET "/" [] (main-handler))
    (GET "/hi" [] "blah"))

;(defn -main [& args]
;    (run-jetty #'my-app {:port 3001}))
