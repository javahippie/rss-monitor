(ns rss-monitor.models.items
  (:require [clojure.xml :as xml]))

(defrecord Item [title link description date])

(defn get-content 
  [tags] 
  (map :content 
    (filter #(= :item (:tag %)) tags)))

(defn get-tag 
  [item tag] 
  (map #(first (:content %)) 
    (filter #(= tag (:tag %)) item)))

(defn get-item 
  [source]  
  (Item. 
    (first (get-tag source :title)) 
    (first (get-tag source :link)) 
    (first (get-tag source :description)) 
    (first (get-tag source :pubDate))))

(defn get-items 
  [source] 
  (map get-item source))

(defn get-data [uri]
  (get-items 
     (get-content 
        (xml-seq 
           (clojure.xml/parse uri)))))