(ns matrix-conn.conn
  (:require [clojure.data.json :refer [write-str read-str]]
            [clj-http.client :as http]))

(defn post-no-json
  "returns response from homeserver after making a matrix API call via HTTP POST request with access_token as POST param and no json"
  [url access-token]
  (http/post (str url "?access_token=" access-token)))


(defn post-json-body
  "returns response from homeserver after making a matrix API call via HTTP POST request with json body"
  [json-body-hashmap url]
  (let [json-hashmap-http-response (http/post url {:body (write-str json-body-hashmap) :throw-exceptions false})]
    json-hashmap-http-response))

(defn post-json-header-body
  ""
  [json-header-hashmap json-body-hashmap url access-token]
  (http/post (str url "?access_token=" access-token) {:headers json-header-hashmap :body (write-str json-body-hashmap) :throw-exceptions false}))

