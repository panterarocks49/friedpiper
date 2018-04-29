(ns friedpiper.routes.home.page
  (:require
   [reagent.core :as r]
   [goog.object :as obj]))


(defn main2 [*app-state]
  (let [ipfs  (get @*app-state :ipfs)
        files (obj/get ipfs "files")
        got   (.get files "QmSPZJdYwoGTfVXWupwkbjYNXRZqT4tBLEbHBYXJAzNA2u" (fn [err files]
                                                                             (js/console.log "inside get")
                                                                             (js/console.log files)
                                                                             (js/console.log err)))
        ;; got-content (obj/get got "content")
        ;; got-string (.toString got-content "utf8")
        ]
    (js/console.log ipfs)
    (js/console.log files)
    (js/console.log got)
    ;; (js/console.log got-content)
    ;; (js/console.log got-string)
    [:div "adsf"]))



(defn main [*app-state]
  (r/with-let [*ipfs (r/cursor *app-state [:ipfs])]
    (if @*ipfs
      [main2 *app-state])))
