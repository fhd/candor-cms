(ns com.github.fhd.candorcms.test-mustache
  (:use clojure.test
        com.github.fhd.candorcms.mustache))

(deftest test-render-simple
  (is (= "Hello, Felix" (render "Hello, {{name}}" {:name "Felix"}))))

(deftest test-render-multi-line
  (is (= "Hello\nFelix" (render "Hello\n{{name}}" {:name "Felix"}))))

(deftest test-render-html-unescaped
  (is (= "<h1>Heading</h1>"
         (render "{{{heading}}}" {:heading "<h1>Heading</h1>"}))))

(deftest test-render-html-escaped
  (is (= "&lt;h1&gt;Heading&lt;/h1&gt;"
         (render "{{heading}}" {:heading "<h1>Heading</h1>"}))))

(deftest test-render-simple-list
  (is (= "Hello, Felix, Jenny!" (render "Hello{{#names}}, {{name}}{{/names}}!"
                                        {:names [{:name "Felix"}
                                                 {:name "Jenny"}]}))))