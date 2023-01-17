(ns bugger.read
  (:gen-class)
  (:require [clojure.tools.logging :refer :all]
            [bugger.utils :refer :all]
            [bugger.client :refer :all]
            [random-string.core :as rs]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def fetch-time 10)
(def hserver-url "hstream://127.0.0.1:8888")
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn -main
  [& args]
  (let [client (get-client hserver-url)
        stream "clj_test_stream_001"
        sub-id (rs/string 10)
        ]

    (subscribe client sub-id stream 2 io.hstream.Subscription$SubscriptionOffset/EARLIEST)
    (let [c1 (consume client sub-id example-hrecord-callback)
          ]
      (Thread/sleep (* fetch-time 1000))
      (.awaitTerminated (.stopAsync c1))
    (.close client))))
