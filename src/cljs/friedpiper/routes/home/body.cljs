(ns friedpiper.routes.home.body
  (:require
   [friedpiper.shared.util :as u]
   [reagent.core :as r]
   [re-com.core :as rc]
   [goog.object :as obj]))



(defn search-hashes [*app-state]
  (r/with-let [*conn (r/cursor *app-state [:conn])]
    [:div (pr-str @*conn)]))



(defn enter-title [*title]
  [rc/input-text
   :placeholder "Enter Title"
   :model *title
   :on-change (fn [val] (reset! *title val))
   ])


(defn enter-hash [*hash]
  [rc/input-text
   :placeholder "Enter Hash"
   :model *hash
   :on-change (fn [val] (reset! *hash val))
   ])


(defn add-hash [*app-state]
  (r/with-let [*title (r/atom "")
               *hash  (r/atom "")]
    [rc/v-box
     :align :center
     :children
     [[enter-title *title]
      [enter-hash *hash]
      [:button
       {:on-click #(u/dispatch *app-state {:title @*title
                                           :hash @*hash})}
       "confirm"]]]))


(defn main [*app-state]
  (r/with-let [*menu (r/cursor *app-state [:menu])
               _     (reset! *menu :search)]
    (condp = @*menu
      :search [search-hashes *app-state]
      :add    [add-hash *app-state]
      :else   [:div "not found"])))
