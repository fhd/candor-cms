(ns candorcms.test-storage
  (:use clojure.test
        candorcms.storage)
  (:import (candorcms.storage Page Article)))

(def simple-site-dir "src/test/resources/simple-site")

(deftest test-load-pages
  (is (= {:index (Page. "/index" "Index" "layout" "<p>Hello, World</p>")}
         (load-pages simple-site-dir))))

(deftest test-load-templates
  (is (= {:layout "<html><body>{{{content}}}</body></html>"}
         (load-templates simple-site-dir))))

(deftest test-load-articles
  (is (= {:article1 (Article. "/index/article1" "Article 1" "2010-12-21"
                              "<p>Article One.</p>")}
         (load-articles simple-site-dir "index" "yyyy-MM-dd"))))
