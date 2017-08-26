(ns matrix-client-server-api.version.r0.client-auth.session-management.login
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]
            [clojure.string :as clj-str]
            [matrix-conn.conn :as conn]))

(def valid-types {:m-login-dummy "m.login.dummy" :m-login-password "m.login.password"})

(defn get-all-types-hashmaps
  "returns all the login types supported by the homeserver as a vector of hashmaps"
  [url]
  (-> (:body (http/get url)) json/read-str (get "flows")))

(defn get-all-types-strings
  "returns all the login types supported by the homeserver as a vector of strings"
  [login-url]
  (mapv #(get % "type") (get-all-types-hashmaps login-url)))

(defn is-valid-type
  "returns true if the specified login type is supported by the homeserver, false otherwise"
  [url login-type]
  (-> (filter #(= % login-type) (get-all-types-strings url)) seq))

(defn mk-m-password-request
  "returns HTTP response as hashmap after making a HTTP POST login request of the type m.login.password to the homeserver"
  [login-url user-id password]
  (-> {:type "m.login.password" :user user-id :password password} (conn/post-json-body login-url)))

(defn get-access-token
  "returns access token as string from HTTP response after making HTTP POST login request to the homeserver"
  [m-password-http-resp-hashmap]
  (-> m-password-http-resp-hashmap :body json/read-str (get "access_token")))

(defn get-user-id
  "returns user id as a string from HTTP response after making HTTP POST login request to the homeserver"
  [m-password-http-resp-hashmap]
  (-> m-password-http-resp-hashmap :body json/read-str (get "user_id")))

(defn get-device-id
  "returns device id as a string from HTTP response after making HTTP POST login request to the homeserver"
  [m-password-http-resp-hashmap]
  (-> m-password-http-resp-hashmap :body json/read-str (get "device_id")))
