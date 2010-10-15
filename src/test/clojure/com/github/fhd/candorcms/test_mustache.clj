(ns com.github.fhd.candorcms.test-mustache
  (:use clojure.test
        com.github.fhd.candorcms.mustache))

;; TODO: Write tests for the following
;; - Lists
;; - Escaped HTML
;; - Unescaped HTML

(deftest test-expand-simple
  (is (= "Hello, Felix" (expand "Hello, {{name}}" {:name "Felix"}))))
