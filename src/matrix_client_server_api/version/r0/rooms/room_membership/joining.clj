(ns matrix-client-server-api.version.r0.rooms.room-membership.joining
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]
            [matrix-conn.conn :as conn]))

(defn get-body-chunk
  "returns a vector of hashmaps of available rooms on the homeserver"
  [public-rooms-hashmap]
  (get (json/read-str (:body public-rooms-hashmap)) "chunk"))

(defn get-all-names
  "returns a vector of strings of the rooms available on the homeserver"
  [public-rooms-hashmap]
  (mapv #(get % "name") (get-body-chunk public-rooms-hashmap)))

(defn valid?
  "returns true if supplied room name is available on the homeserver, false otherwise"
  [public-rooms-hashmap room-name]
  (seq (first (filter #(= % room-name) (get-all-names public-rooms-hashmap)))))

(defn get-id
  "returns hashmap with room name as key and associated room id as value"
  [public-rooms-hashmap room-name]
  {room-name (get (first (filter #(= (get % "name") room-name) (get-body-chunk public-rooms-hashmap))) "room_id")})

(defn join-id
  "returns response as hashmap from homeserver after making an API call to join a room by room id via HTTP POST request with no json"
  [url room-id access-token]
  (conn/post-no-json (str url "/" room-id "/join") access-token))
