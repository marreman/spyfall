(ns spyfall.core
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.util.codec :refer [url-decode]]
            [hiccup.core :refer [html]]
            [spyfall.game]))

(defn spy [data] (prn data) data)

(def cards
  (->> (io/resource "cards.json")
       slurp
       json/read-str))

(defn request->game
  [req]
  (println req)
  {:name (->> (:uri req)
              url-decode
              (remove #{\/})
              (apply str))})

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (html (spyfall.game/view (request->game request)))})

(defn -main
  [port-number]
  (run-jetty
    handler
    {:port (Integer. port-number)}))

(defn -dev-main
  [port-number]
  (run-jetty
    (wrap-reload #'handler)
    {:port  (Integer. port-number)
     :join? false}))
