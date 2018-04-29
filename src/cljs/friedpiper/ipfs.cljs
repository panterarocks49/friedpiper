(ns friedpiper.ipfs
  (:require
   ;; ["orbit-db" :as orbitDB]
   [promesa.core :as p]
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


(defn init [app-state]
  (let [*ipfs     (r/cursor app-state [:ipfs])
        *orbit-db (r/cursor app-state [:orbit-db])
        new-ipfs  (new js/Ipfs ipfs-options)]
    (.on new-ipfs "error" (fn [e] (js/console.log e)))
    (.on new-ipfs "ready"
         (fn []
           (p/alet
            [orbit-db (new js/OrbitDB new-ipfs)
             ;; access   (clj->js
             ;; {:write ["*"]})
             db       (p/await (.docs orbit-db "first-db"))
             hash     (p/await (.put db (clj->js {:_id "friedpiperdata" :value [{:db/id 1} {:db/id 2} {:db/id 3}]})))
             js-info  (.get db "friedpiperdata")
             info     (js->clj js-info :keywordize-keys true)
             ]
            ;; (reset! *ipfs new-ipfs)
            ;; (reset! *orbit-db orbit-db)
            (js/console.log orbit-db)
            (js/console.log db)
            (js/console.log hash)
            (println info)
            )
           true)))
  )
