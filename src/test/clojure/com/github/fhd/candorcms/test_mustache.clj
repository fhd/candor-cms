(ns com.github.fhd.candorcms.test-mustache
  (:use clojure.test
        com.github.fhd.candorcms.mustache))

;; TODO: Write tests for the following
;; - Escaped HTML
;; - Unescaped HTML
;; - Lists
;; - Functions

(deftest test-render-simple
  (is (= "Hello, Felix" (render "Hello, {{name}}" {:name "Felix"}))))
