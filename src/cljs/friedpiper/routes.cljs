(ns friedpiper.routes
  (:require
   [bidi.bidi :as bidi]
   [pushy.core :as pushy]))


(def routes ["/" {"" :home
                  }])


(defn parse-url [url]
  (bidi/match-route routes url))


(defn dispatch-route [app-state matched-route]
  (let [page (:handler matched-route)]
    (swap! app-state assoc :page page)))


(defn app-routes [app-state]
  (pushy/start!
   (pushy/pushy (partial dispatch-route app-state)
                parse-url)))


(def url-for (partial bidi/path-for routes))
