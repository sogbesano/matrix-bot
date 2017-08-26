(ns matrix-client-server-api.version.r0.modules.content-repo.media.client-behaviour
  (:require [clojure.data.json :as json]
            [matrix-conn.conn :as conn]
            [clj-http.client :as http]
            [clojure.java.io :as io]
            [clojure.string :refer [join]]))

(defn read-file2
  ""
  [filepath]
  (let [f (io/file filepath) f-byte-arr (byte-array (.length f)) is (io/input-stream f)]
    (.read is f-byte-arr)
    (.close is)
    (String. f-byte-arr)))

(defn read-file
  ""
  [filepath]
  (let [f (io/file filepath) f-byte-arr (byte-array (.length f)) is (io/input-stream f)]
    (.read is f-byte-arr)
    (.close is)
    f-byte-arr))


(defn read-file3
  ""
  [filepath]
  (let [f (io/file filepath) f-byte-arr (byte-array (.length f)) is (io/input-stream f)]
    (.read is f-byte-arr)
    (.close is)
    (str "[" (join "," (map byte f-byte-arr)) "]")))
  
(defn upload
  ""
  [content-type content url access-token]
  (http/post (str url "?access_token="access-token) {:headers {"Content-Type" content-type} :body (json/write-str {"<content>" content}) :throw-exceptions false}))

;;(defn upload2
;;  ""
;;  [content-type content url access-token]
;;  (http/post (str url "?access_token=" access-token) {:headers {"Content-Type" content-type} :body (json/write-str {:content content}) :headers {"Content-Type" content-type}}))

;;(defn upload
;;  "returns the MXC URI after making a matrix API call to upload some content to the content repository"
;;  [body content-type url access-token]
;;  (http/post (str url "?access_token=" access-token) {:headers {"Content-Type" content-type} :body (json/write-str {:content {:body body :msgtype "m.image" :url url } })} ))
  
