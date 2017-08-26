(ns matrix-client-server-api.version.r0.rooms.room-discovery.listing
  (:require [clj-http.client :as http]))

(defn list-public
  "returns paginated responses of all public rooms available on the homeserver"
  [url]
  (http/get url))
    
