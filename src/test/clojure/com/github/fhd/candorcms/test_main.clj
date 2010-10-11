;; TODO: Write test cases for the following:
;; - Retrieve a page that includes snippets
;; - Retrieve a page that includes articles
;; - Retrieve the page for a specific article

(ns com.github.fhd.candorcms.test-main
  (:use clojure.test
	com.github.fhd.candorcms.main))

(deftype MockContentProvider []
  ContentProvider
  (get-page [this page]
    (str "The page is: " page)))

(defn mock-provider-fixture [f]
  (def provider (MockContentProvider.))
  (f))

(use-fixtures :once mock-provider-fixture)

(deftest test-get-index-page
  (is (= "The page is: index" (get-page provider "index"))))
