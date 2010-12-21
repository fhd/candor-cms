(ns candorcms.test-storage
  (:use clojure.test
        candorcms.storage)
  (:import candorcms.storage.Page))

(deftest test-load-pages
  (is (= {:index (Page. "/index" "Index" "none" "<p>Hello, World</p>")}
         (load-pages "src/test/resources/simple-site"))))

;; TODO: Write test cases for load-templates and load-articles

