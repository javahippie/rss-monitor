(ns rss-monitor.views.welcome
  (:require [rss-monitor.views.common :as common])
  (:require [rss-monitor.models.items :as items])
  (:use [noir.core :only [defpage]]
        [hiccup.element :only [link-to]]
        [hiccup.form]))

(defn list-items [uri]
  [:ul
   (for [item (items/get-data uri)]
     [:li 
        [:div {:class "item"}          
          [:a  {:class "title" :href (:link item)} (:title item)]
          [:hr]
          [:p (:description item)]
          [:p {:class "footer"}(:date item)]
        ]
     ]
    )
  ]
) 


(defpage "/" []
  (common/layout
    (form-to [:post "/feed"]
             (text-field "adresse")
             (submit-button "Suchen"))))
  

(defpage [:post "/feed"] {:keys [adresse]} 
         (common/layout
           (list-items adresse)))
