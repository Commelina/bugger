(ns bugger.write
  (:gen-class)
  (:require [clojure.tools.logging :refer :all]
            [bugger.utils :refer :all]
            [bugger.client :refer :all]
            [random-string.core :as rs]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def max-partitions 20)
(def write-number 5)
(def hserver-url "hstream://127.0.0.1:8888")
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn write-with-producer
  "data: integer
   key:  string"
  ([producer data]
   (let [future (write-data producer {:data data})] (.join future)))
  ([producer data key]
   (let [future (write-data producer {:data data} key)] (.join future))))

(defn -main
  [& args]
  (let [client (get-client hserver-url)
        stream "clj_test_stream_001"
        _ (create-stream client stream max-partitions)
        producer (create-producer client stream)
        ]
    (dorun (map #(write-with-producer producer % (str %)) (range write-number)))
    (.close client)))
