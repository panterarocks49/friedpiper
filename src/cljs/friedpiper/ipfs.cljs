(ns friedpiper.ipfs
  (:require
   [promesa.core :as p]
   [goog.object :as obj]
   [reagent.core :as r])
  (:require-macros
   [promesa.async-cljs :refer [async]]))


(def ipfs-options
  (clj->js
   {:repo "/fried/piper/data/share"
    :start true
    :EXPERIMENTAL
    {:pubsub true}
    :config
    {:Addresses
     {:Swarm [
              "/dns4/ws-star.discovery.libp2p.io/tcp/443/wss/p2p-websocket-star"
              ]}}}))


(def orbit-db-options
  (clj->js {:create true
            :overwrite true
            :localOnly false
            :type "docstore"
            :indexBy "hash"
            :write ["*"]}))


(def schema {})


(defn reset-conn! [*conn db-orbit type]
  (fn []
    (let [js-info    (.query db-orbit (fn [doc] true))
          info       (js->clj js-info :keywordize-keys true)]
      (println info)
      (if (and info true)
        (reset! *conn info)))))


(defn ipfs-ready [*db *conn new-ipfs]
  (fn []
    (p/alet
     [orbit-db  (js/OrbitDB. new-ipfs)
      ;; db (p/await (.docs orbit-db "friedpiperr" (clj->js {:write ["*"] :indexBy "hash"})))
      ;; _ (println (str (obj/get db "address")))
      db        (p/await (.open orbit-db "/orbitdb/Qme1kbqB8eoRNWha3NqoqMwcFc8AJBd3yfm9EWPiEqJRUj/friedpiperr" orbit-db-options))
      _         (p/await (.load db))
      db-events (obj/get db "events")]
     ((reset-conn! *conn db "init"))
     (reset! *db db)
     (.on db-events "load" (reset-conn! *conn db "load"))
     (.on db-events "ready" (reset-conn! *conn db "ready"))
     (.on db-events "replicated" (reset-conn! *conn db "repli"))
     (.on db-events "write" (reset-conn! *conn db "write"))
     (.on db-events "replicate.progress" (reset-conn! *conn db "repli.progress"))
     )
    true))


(defn init [*app-state]
  (let [*db      (r/cursor *app-state [:orbitdb])
        *conn    (r/cursor *app-state [:conn])
        new-ipfs (new js/Ipfs ipfs-options)]
    (.on new-ipfs "error" (fn [e] (js/console.log e)))
    (.on new-ipfs "ready" (ipfs-ready *db *conn new-ipfs))))
