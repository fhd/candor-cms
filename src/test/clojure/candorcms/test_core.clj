(ns candorcms.test-core
  (:use clojure.test
        candorcms.core))

(defrecord Page
    [url title template content])

(defn simple-site-fixture [f]
  (binding [load-properties (fn [file] (proxy [java.util.Properties] []
                                         (getProperty [property] " ")))
            load-pages (fn [site-dir] {:index (Page. "/"
                                                     "Index"
                                                     "none"
                                                     "Hello")})
            load-templates (fn [site-dir] {:none "{{content}}"})
            load-articles (fn [site-dir page-name date-format] {})]
    (f)))

(use-fixtures :once simple-site-fixture)

(deftest test-simple-page
  (is (= "Hello" (get-page "index"))))