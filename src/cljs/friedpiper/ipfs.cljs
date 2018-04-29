(ns friedpiper.ipfs
  (:require
   ["orbit-db" :as orbitDB]
   [promesa.core :as p]
   [reagent.core :as r])
  (:require-macros
   [promesa.async-cljs :refer [async]]))


(def ipfs-options
  (clj->js
   {:EXPERIMENTAL
    {:pubsub true}}))


(defn init [app-state]

  ;; (let [*ipfs     (r/cursor app-state [:ipfs])
  ;;       *orbit-db (r/cursor app-state [:orbit-db])
  ;;       new-ipfs  (new js/Ipfs ipfs-options)]
  ;;   (.on new-ipfs "error" (fn [e] (js/console.log e)))
  ;;   (.on new-ipfs "ready"
  ;;        (fn []
  ;;          (p/alet
  ;;           [orbit-db (new orbitDB new-ipfs)
  ;;            ;; access   (clj->js
  ;;            ;; {:write ["*"]})
  ;;            _ (js/console.log orbit-db)
  ;;            db       #_(p/await) #_(p/delay 20000000) (.keyvalue orbit-db "first-db")
  ;;            ]
  ;;           ;; (reset! *ipfs new-ipfs)
  ;;           ;; (reset! *orbit-db orbit-db)
  ;;           (js/console.log db)
  ;;           )
  ;;          true)))

  )
