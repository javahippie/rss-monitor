(ns rss-monitor.models.items
  (:require [clojure.xml :as xml]))

(defrecord Item [title link description date])

(defn get-content [tags] (for [x tags
                               :when (= :item (:tag x))]
                           (:content x)))

(defn get-tag [item tag] (for [x item
                               :when (= tag (:tag x))]
                           (first (:content x))))

(defn get-items [source] (for [x source]
                           (Item. (first (get-tag x :title ))
                             (first (get-tag x :link ))
                             (first (get-tag x :description ))
                             (first (get-tag x :pubDate )))
                           )
  )

(defn get-data [uri]
  (get-items (get-content (xml-seq (xml/parse uri)))))