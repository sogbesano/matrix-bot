(ns matrix-client-server-api.version.r0.events.room-participation.syncing
  (:require [clj-http.client :as http]))

(defn synchronise
  "API endpoint call sync, returns snapshot of state on homeserver and synchronises the clients state with the latest state on the homeserver"
  [url access-token]
  (http/get (str url "?access_token=" access-token)))
