(ns friedpiper.shared.util)

(defn uid []
  (str "uuid" (.-uuid (random-uuid))))


(defn dispatch [*app-state data]
  (let [orbitdb (get @*app-state :orbitdb)]
    (println data)
    (.put orbitdb (clj->js data))))
