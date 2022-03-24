(ns bugger.core
  (:gen-class)
  (:require [clojure.tools.logging :refer :all]
            [bugger.utils :refer :all]
            [bugger.client :refer :all]
            [random-string.core :as rs]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def max-partitions 50)
(def write-number 10)
(def fetch-time 10)
(def hserver-url "192.168.1.170:7777")
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn write-with-producer
  "data: integer
   key:  string"
  ([producer data]
   (let [future (write-data producer {:data data})] (.join future)))
  ([producer data key]
   (let [future (write-data producer {:data data} key)] (.join future))))

(defn write
  [client stream data key]
  (try (let [producer (create-producer client stream)]
         (write-with-producer producer data key))
       (catch Exception e (println "=== " e))))

(defn -main
  [& args]
  (let [client (get-client hserver-url)
        stream (rs/string 10)
        producer (create-producer client stream)
        sub-id (rs/string 10)
        received-data-1 (ref [])
        received-data-2 (ref [])]
    (create-stream client stream)
    (subscribe client sub-id stream 600)
    (let [c1 (consume client sub-id (gen-collect-value-callback received-data-1))
          c2 (consume client sub-id (gen-collect-value-callback received-data-2))]
      (dorun (map #(write-with-producer producer %) (range write-number)))
      (Thread/sleep (* fetch-time 1000))
      (println "=== Read-1: " @received-data-1)
      (println "=== Read-2: " @received-data-2)
      (.awaitTerminated (.stopAsync c1))
      (.awaitTerminated (.stopAsync c2))
      (.close client))))
