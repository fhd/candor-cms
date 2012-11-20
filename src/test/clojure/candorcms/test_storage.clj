(ns candorcms.test-storage
  (:use clojure.test
        candorcms.storage)
  (:import (candorcms.storage Page Article)))

(def simple-site-dir "src/test/resources/simple-site")
(def header-test-site-dir "src/test/resources/header-test-site")

(deftest test-load-pages
  (is (= {:index (Page. "/index" "Index" "layout" "<p>Hello, World</p>")}
         (load-pages simple-site-dir))))

(deftest test-load-templates
  (is (= {:layout "<html><body>{{{content}}}</body></html>\n"}
         (load-templates simple-site-dir))))

(deftest test-load-articles
  (is (= {:article1 (Article. "/index/article1" "Article 1" "2010-01-01"
                              "<p>Article One.</p>")}
         (load-articles simple-site-dir "index"))))

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
  (is (= (Article. "/index/no-header" nil nil "Test")
         (:no-header (load-articles header-test-site-dir "index")))))

(deftest test-load-articles-without-title
  (is (= (Article. "/index/no-title" nil "2010-01-01" "Test")
         (:no-title (load-articles header-test-site-dir "index")))))

(deftest test-load-articles-without-date
  (is (= (Article. "/index/no-date" "No Date" nil "Test")
         (:no-date (load-articles header-test-site-dir "index")))))
