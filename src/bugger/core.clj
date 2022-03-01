(ns bugger.core
  (:gen-class)
  (:require [clojure.tools.logging :refer :all]
            [bugger.utils :refer :all]
            [bugger.client :refer :all]
            [random-string.core :as rs]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def max-partitions 50)
(def write-number 1000)
(def fetch-time 10)
(def hserver-url "192.168.1.170:7777")
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn write-with-producer
  "data: integer
   key:  string"
  [producer data key]
  (let [future (write-data producer {:data data} key)] (.join future)))

(defn write
  [client stream data key]
  (let [producer (create-producer client stream)]
    (write-with-producer producer data key)))

(defn -main
  [& args]
  (let [client (get-client hserver-url)
        stream (rs/string 10)
        sub-id (rs/string 10)
        producer (create-producer client stream)
        received-data (ref [])]
    (create-stream client stream)
    (subscribe client sub-id stream 600)
    (dorun (map #(write-with-producer producer % (str (mod % max-partitions)))
             (range write-number)))
    (consume client sub-id (gen-collect-value-callback received-data))
    (Thread/sleep (* fetch-time 1000))
    (println "=== Read: " @received-data)))
