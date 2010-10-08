(ns com.github.fhd.candorcms.hello
  (:use compojure.core
	ring.util.servlet
	hiccup.core)
  (:gen-class
   :extends javax.servlet.http.HttpServlet))

(defservice
    (GET "/*" [] (html [:h1 "Hello, World!"])))
