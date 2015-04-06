(ns introclojure.test-runner
  (:require
   [cljs.test :refer-macros [run-tests]]
   [introclojure.core-test]))

(enable-console-print!)

(defn runner []
  (if (cljs.test/successful?
       (run-tests
        'introclojure.core-test))
    0
    1))
