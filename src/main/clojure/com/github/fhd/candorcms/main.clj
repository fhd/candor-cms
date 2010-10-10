(ns com.github.fhd.candorcms.main
  (:use compojure.core
	ring.util.servlet
	hiccup.core)
  (:gen-class
   :extends javax.servlet.http.HttpServlet))

(defroutes example
  (GET "/*" [] (html [:h1 "Hello, World!"])))

(defservice example)
