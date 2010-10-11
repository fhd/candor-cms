(ns com.github.fhd.candorcms.main
  (:use compojure.core
	ring.util.servlet
	hiccup.core)
  (:gen-class
   :extends javax.servlet.http.HttpServlet))

(defprotocol ContentProvider
  (get-page [this page]))

(deftype FileSystemContentProvider []
  ContentProvider
  (get-page [this page]
    (html [:h1 "Welcome to " page])))

(let [provider (FileSystemContentProvider.)]
  (defroutes example  
    (GET "/" [] (get-page provider "index"))
    (GET "/:page" [page] (get-page provider page))))

(defservice example)
