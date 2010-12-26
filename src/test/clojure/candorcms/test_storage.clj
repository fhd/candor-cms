(ns candorcms.test-storage
  (:use clojure.test
        candorcms.storage)
  (:import (candorcms.storage Page Article)))

(def simple-site-dir "src/test/resources/simple-site")
(def header-test-site-dir "src/test/resources/header-test-site")
(def date-format "yyyy-MM-dd")

(deftest test-load-pages
  (is (= {:index (Page. "/index" "Index" "layout" "<p>Hello, World</p>")}
         (load-pages simple-site-dir))))

(deftest test-load-templates
  (is (= {:layout "<html><body>{{{content}}}</body></html>"}
         (load-templates simple-site-dir))))

(deftest test-load-articles
  (is (= {:article1 (Article. "/index/article1" "Article 1" "2010-12-26"
                              "<p>Article One.</p>")}
         (load-articles simple-site-dir "index" date-format))))

(deftest test-load-pages-without-header
  (is (= (Page. "/no-header" nil nil "Test")
         (:no-header (load-pages header-test-site-dir))))) 

(deftest test-load-pages-without-title
  (is (= (Page. "/no-title" nil "layout" "Test")
         (:no-title (load-pages header-test-site-dir)))))

(deftest test-load-pages-without-template
  (is (= (Page. "/no-template" "No Template" nil "Test")
         (:no-template (load-pages header-test-site-dir)))))

(deftest test-load-articles-without-header
  (is (= (Article. "/index/no-header" nil "2010-12-26" "Test")
         (:no-header (load-articles header-test-site-dir "index"
                      date-format)))))
