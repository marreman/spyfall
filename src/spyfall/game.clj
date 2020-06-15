(ns spyfall.game
  (:require [hiccup.page :refer [html5 include-css]]
            [clojure.string :as string]))

(defn layout [page]
  (html5
    {:lang "en"}
    [:head
     [:title "Spyfall"]
     [:meta {:charset "UTF-8"}]
     (include-css "https://unpkg.com/tailwindcss@%5E1.0/dist/tailwind.min.css")
     ]
    [:body page]))

(defn view-name [s]
  (->> 
    (string/capitalize s)
    (str "Spyfall game: ")
    ((fn [sa] [:h1.text-blue-800.text-3xl.sm:text-5xl.font-bold.tracking-wide.text-center sa]))))

(defn view [game]
  (layout
    (view-name (:name game))))
