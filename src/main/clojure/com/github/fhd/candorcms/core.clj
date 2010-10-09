(ns com.github.fhd.candorcms.core
  (:use compojure.core
	ring.util.servlet
	hiccup.core)
  (:gen-class
   :extends javax.servlet.http.HttpServlet))

(defroutes example
  (GET "/*" [] (html [:h1 "Hello, World!"])))

(defservice example)
