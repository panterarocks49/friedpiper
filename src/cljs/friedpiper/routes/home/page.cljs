(ns friedpiper.routes.home.page
  (:require
   [reagent.core :as r]
   [goog.object :as obj]))


(defn uid []
  (str "uuid" (.-uuid (random-uuid))))


(defn dispatch [*app-state data]
  (let [orbitdb (get @*app-state :orbitdb)]
    (println data)
    (.put orbitdb (clj->js data))))


(defn page [*app-state]
  (r/with-let [*conn (r/cursor *app-state [:conn])
               *var (r/atom 1)]
    [:div
     [:div (pr-str @*conn)]
     [:button
      {:on-click #(do
                    (dispatch *app-state {})
                    (swap! *var + 1))}
      "change data"]]))


(defn main [*app-state]
  (let [conn    (get @*app-state :conn)
        orbitdb (get @*app-state :orbitdb)]
    (if (and conn orbitdb)
      [page *app-state]
      [:div "loading"])))

