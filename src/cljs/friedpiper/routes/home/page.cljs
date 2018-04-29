(ns friedpiper.routes.home.page
  (:require
   [friedpiper.routes.home.body :as body]
   [friedpiper.shared.util :as u]
   [reagent.core :as r]
   [re-com.core :as rc]
   [goog.object :as obj]))


(defn nav [*app-state]
  (r/with-let [*menu (r/cursor *app-state [:menu])]
    [rc/h-box
     :class "pt-menu"
     :min-width "100vw"
     :style {:padding "5px"
             :border-bottom "1px solid lightgray"}
     :children
     [[:span.pt-menu-item.pt-icon-search
       {:on-click #(reset! *menu :search)}]
      [:span.pt-menu-item.pt-icon-add
       {:on-click #(reset! *menu :add)}]]]))


(defn page [*app-state]
  (r/with-let [*conn (r/cursor *app-state [:conn])]
    [rc/v-box
     :align :center
     :justify :start
     :min-height "100vh"
     :min-width "100vw"
     :children
     [[nav *app-state]
      [body/main *app-state]]]))


(defn main [*app-state]
  (let [conn    (get @*app-state :conn)
        orbitdb (get @*app-state :orbitdb)]
    (if (and conn orbitdb)
      [page *app-state]
      [:div "loading"])))

