(ns spyfall.dev
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [spyfall.game]))

(comment

  (def server
    (future
      (run-jetty
        (wrap-reload #'spyfall.game/handler)
        {:port 3000})))

  (reset! spyfall.game/games {})
  )
