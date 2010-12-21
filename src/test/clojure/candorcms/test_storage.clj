(ns candorcms.test-storage
  (:use clojure.test
        candorcms.storage)
  (:import (candorcms.storage Page Article)))

(def simple-site-dir "src/test/resources/simple-site")
(def no-headers-site-dir "src/test/resources/no-headers-site")
(def date-format "yyyy-MM-dd")

(deftest test-load-pages
  (is (= {:index (Page. "/index" "Index" "layout" "<p>Hello, World</p>")}
         (load-pages simple-site-dir))))

(deftest test-load-templates
  (is (= {:layout "<html><body>{{{content}}}</body></html>"}
         (load-templates simple-site-dir))))

(deftest test-load-articles
  (is (= {:article1 (Article. "/index/article1" "Article 1" "2010-12-21"
                              "<p>Article One.</p>")}
         (load-articles simple-site-dir "index" date-format))))

(deftest test-load-pages-without-header
  (is (= {:index (Page. "/index" nil nil "<p>Hello, World</p>")}
         (load-pages no-headers-site-dir))))

(deftest test-load-articles-without-header
  (is (= {:article1 (Article. "/index/article1" nil "2010-12-21"
                              "<p>Article One.</p>")}
         (load-articles no-headers-site-dir "index" date-format))))