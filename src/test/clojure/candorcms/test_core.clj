(ns candorcms.test-core
  (:use clojure.test
        candorcms.core
        candorcms.storage)
  (:import (candorcms.storage Page Article)))

(defn simple-site-fixture [f]
  (binding [load-properties (fn [file] (proxy [java.util.Properties] []
                                         (getProperty [property] " ")))
            load-pages (fn [site-dir]
                         {:index
                          (Page. "/" "Index" "none" "Hello, World")
                          :single-article
                          (Page. "/single-article" "Single Article" "none"
                                 "{{#articles}}

<p><a href=\"{{url}}\">{{title}}</a></p>
<p>{{date}}</p>
<p>{{{content}}}</p>{{/articles}}")
                          :multiple-articles
                          (Page. "/multiple-articles" "Multiple Articles"
                                 "none" "{{#articles}}

<p>{{{content}}}</p>{{/articles}}")})
            load-templates (fn [site-dir] {:none "{{{content}}}"})
            load-articles (fn [site-dir page-name]
                            (case page-name
                                  "single-article"
                                  {:article1 (Article. "/" "Article 1"
                                                       "2010-10-26"
                                                       "Article One")}
                                  "multiple-articles"
                                  {:article1 (Article. "/" "Article 1"
                                                       "2010-10-26"
                                                       "Article One")
                                   :article2 (Article. "/" "Article 2"
                                                       "2010-10-27"
                                                       "Article Two")}
                                  {}))]
    (f)))

(use-fixtures :once simple-site-fixture)

(deftest test-simple-page
  (is (= "Hello, World" (get-page "index"))))

(deftest test-single-article
  (is (= "<p><a href=\"/\">Article 1</a></p>
<p>2010-10-26</p>
<p>Article One</p>"
         (get-page "single-article"))))

(deftest test-multiple-articles
  (is (= "<p>Article One</p>
<p>Article Two</p>"
         (get-page "multiple-articles"))))

(deftest test-page-not-found
  (is (= 404 (:status (get-page "doesnt-exist")))))
