(ns matrix-url.url
  (:require [clojure.string :refer [join replace]]))

(def matrix-api "_matrix")

(def client-api "client")

(def client-api-version "r0")

(def client-api-register-call "register")

(def client-api-login-call "login")

(def client-api-public-rooms-call "publicRooms")

(def client-api-sync-call "sync")

(def client-api-rooms-call "rooms")

(def client-api-media-call "media")

(def identity-api "identity/api")

(def identity-api-version "v1")

(def request-token-api-call "validate/email/requestToken")

(def identity-service "vector.im")

(defn mk-url
  "returns a matrix URL"
  ([protocol homeserver port api api-version api-call] (mk-url protocol homeserver port api api-version api-call nil))
  ([protocol homeserver port api api-version api-call params]
  (let [url (str protocol homeserver ":" port "/" matrix-api "/" api "/" api-version "/" api-call (if (= params nil) "" params))]
    url)))

(defn add-post-params
  "returns a URL with HTTP POST parameters added"
  [url post-params-hashmap]
  (let [post-param-pairs (map vector (keys post-params-hashmap) (vals post-params-hashmap))
        mk-post-param-function (fn [pair] (str (first pair) "=" (second pair) "&"))
        post-params-str (replace (join "" (drop-last (join "" (map mk-post-param-function post-param-pairs)))) #":" "")
        url-with-post-params (str url "?" post-params-str)]
    url-with-post-params)) 

(def https-protocol "https://")

(def register (mk-url https-protocol "skrem.space" 8448 client-api client-api-version client-api-register-call))

(def login (mk-url https-protocol "skrem.space" 8448 client-api client-api-version client-api-login-call))

(def request-token (mk-url https-protocol identity-service 8090 identity-api identity-api-version request-token-api-call))

(def public-rooms (mk-url https-protocol "skrem.space" 8448 client-api client-api-version client-api-public-rooms-call))

(def synchronise (mk-url https-protocol "skrem.space" 8448 client-api client-api-version client-api-sync-call))

(def rooms (mk-url https-protocol "skrem.space" 8448 client-api client-api-version client-api-rooms-call))

(def media (mk-url https-protocol "skrem.space" 8448 client-api-media-call client-api-version "upload"))
