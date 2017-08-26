(ns matrix-bot.core
  (:require [matrix-client-server-api.version.r0.client-auth.session-management.login :as login]
            [matrix-client-server-api.version.r0.rooms.room-discovery.listing :as listing]
            [matrix-client-server-api.version.r0.events.room-participation.syncing :as syncing]
            [matrix-client-server-api.version.r0.events.room-participation.sending :as sending]
            [matrix-client-server-api.version.r0.rooms.room-membership.joining :as joining]
            [matrix-client-server-api.version.r0.modules.content-repo.media.client-behaviour :as behaviour]
            [matrix-url.url :as url]
            [clj-http.client :as http]
            [clojure.data.json :refer [write-str read-str]])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (format "login url:\n%s" url/login))
  (println (format "public rooms url:\n%s" url/public-rooms))
  (println (format "sync url:\n%s" url/synchronise))
  (println (format "media url:\n%s" url/media))
  (println (format "get all login types hashmaps:\n%s" (login/get-all-types-hashmaps url/login)))
  (println (format "get all login types strings:\n%s" (login/get-all-types-strings url/login)))
  (let 
    [m-password-resp (login/mk-m-password-request url/login "user123" "pass123")
     access-token (login/get-access-token m-password-resp)
     user-id (login/get-user-id m-password-resp)
     device-id (login/get-device-id m-password-resp)
     public-rooms-hashmap (listing/list-public url/public-rooms)
     room-id-hashmap (joining/get-id public-rooms-hashmap "beepboop")
     upload-mxc-uri-hashmap (behaviour/upload "application/json" (behaviour/read-file "/home/olu/pics/funny/fu.png") url/media access-token)
     mxc-content-uri (get (read-str (:body upload-mxc-uri-hashmap)) "content_uri")]
     ;;(println (format "m.login.password resp:\n%s" m-password-resp))
     ;;(println (format "is valid login type:\n%s" (login/is-valid-type url/login "m.login.password")))
     ;;(println (format "access token:\n%s" access-token))
     ;;(println (format "user id:\n%s" user-id))
     ;;(println (format "device id:\n%s" device-id)) 
     ;;(println (format "list public rooms:\n%s" public-rooms-hashmap))
     ;;(println (format "get all room names:\n%s" (joining/get-all-names public-rooms-hashmap)))
     ;;(println (format "is valid room name:\n%s" (joining/valid? public-rooms-hashmap "beepboop")))
     ;;(println (format "room id:\n%s" room-id-hashmap))
     ;;(println (format "join:\n%s" (joining/join-id url/rooms (get room-id-hashmap "beepboop") access-token)))
     ;;(println (format "sync:\n%s" (syncing/synchronise url/synchronise access-token)))
     ;;(println (format "m.text:\n%s" (sending/send-m-msg-event url/rooms (get room-id-hashmap "beepboop") "m.room.message" 0420 access-token (sending/m-msg-text-event "reeeeeeeeeeeeeeee"))))
     (println (format "upload:\n%s" upload-mxc-uri-hashmap))
     (println (format "content uri:\n%s" mxc-content-uri))
     (println (format "m.image:\n%s" (sending/send-m-msg-event url/rooms (get room-id-hashmap "beepboop") "m.room.message" 1234 access-token (sending/m-msg-image-event "crotch.png" mxc-content-uri))))
    ))
