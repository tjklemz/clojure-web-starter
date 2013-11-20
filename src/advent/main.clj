(ns advent.main)
(use 'stencil.core)
(use 'compojure.core)
(use 'compojure.route)
(use 'ring.adapter.jetty)

(stencil.loader/set-cache (clojure.core.cache/ttl-cache-factory {} :ttl 0))

(use 'advent.data)

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

(defn featured []
  (videos 0))

(defn nonfeatured []
  (rest videos))

(defn get-revealed-for-month []
  (split (nonfeatured) (- (get-day-of-month) 1)))

(defn get-revealed []
  (if (> (get-year) 2013) (nonfeatured)
    (if (and (>= (get-month) start-month) (> (get-day-of-month) 1)) (get-revealed-for-month)
      nil)))

(defn get-upcoming-days [l n]
  (if (< n (count l))
    (cons (:day (l n)) (get-upcoming-days l (inc n)))))

(defn get-upcoming-range []
  (get-upcoming-days videos (inc (count (get-revealed)))))

(defn get-videos []
  {:featured ((featured) :url)
   :videos (get-revealed)
   :upcoming (get-upcoming-range)
  })

(defn main-handler []
    (render-file "main" (get-videos)))

(def root (str (System/getProperty "user.dir") "/public"))

(defroutes my-app
    (compojure.route/resources "/")
    (compojure.route/files "/" (do (println root) {:root root}))
    (GET "/" [] (main-handler)))
