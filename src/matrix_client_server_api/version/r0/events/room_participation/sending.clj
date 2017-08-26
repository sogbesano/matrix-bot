(ns matrix-client-server-api.version.r0.events.room-participation.sending
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]))

(def m-msg-event-types {:m-text "m.text" 
                             :m-emote "m.emote"
                             :m-notice "m.notice"
                             :m-image "m.image"
                             :m-file "m.file"
                             :m-location "m.location"
                             :m-video "m.video"
                             :m-audio "m.audio"})

(defn send-m-msg-event
  "returns response from homeserver as hashmap after making API call to send a message event to a room via HTTP PUT"
  [url room-id event-type txnid access-token content]
  (let [send-url (str url "/" room-id "/send/" event-type "/" txnid "?access_token=" access-token)] 
    (http/put send-url {:body (json/write-str content)})))

(defn m-msg-text-event
  "returns the json representing a message event of type m.text"
  [body]
  {:body body :msgtype (:m-text m-msg-event-types)})

(defn m-msg-image-event
  "returns a hashmap representing the json for a message event of the type m.image"
  [body mxc-uri]
  {:body body :msgtype (:m-image m-msg-event-types) :url mxc-uri})
