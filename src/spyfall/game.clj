(ns spyfall.game
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.def :refer [defelem]]
            [hiccup.core :refer [html]]
            [ring.util.codec :refer [url-decode]]
            [clojure.string :as string]))

(defn layout [&page]
  (html5
    {:lang "en"}
    [:head
     [:title "Spyfall"]
     [:meta {:charset "UTF-8"}]
     [:link {:rel "icon", :href "data:,"}]
     (include-css "https://unpkg.com/tailwindcss@1.4.6/dist/tailwind.min.css")
     ]
    [:body &page]))

(defn view-name [s]
  (->> 
    (string/capitalize s)
    (str "Spyfall game: ")
    ((fn [sa] [:h1.text-blue-800.text-3xl.sm:text-5xl.font-bold.tracking-wide.text-center sa]))))

(defonce games (atom {}))

(defn view [game]
  (layout
    [:div (view-name (:name game))
     [:pre {} @games]]
    ))

(defn create-game [name]
  {:name name
   :players []})

(defn handler [request]
  (let [name (->> (:uri request)
                       url-decode
                       (remove #{\/})
                       (apply str))
        game (get @games name (create-game name))]
    (swap! games assoc name game)
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (html (spyfall.game/view game))}))
