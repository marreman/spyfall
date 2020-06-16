(ns spyfall.core
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]
            [ring.adapter.jetty :refer [run-jetty]]
            [spyfall.game]))

(def cards
  (->> (io/resource "cards.json")
       slurp
       json/read-str))

(defn -main
  [port-number]
  (run-jetty
    spyfall.game/handler
    {:port (Integer. port-number)}))
