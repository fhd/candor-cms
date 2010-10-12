(ns com.github.fhd.candorcms.core
  "The core service of Candor CMS, responsible for retrieving the pages."
  (:use compojure.core
	ring.util.servlet
	hiccup.core)
  (:gen-class
   :extends javax.servlet.http.HttpServlet))

(defn load-properties
  "Loads all properties from the specified file."
  [file]
  (doto (java.util.Properties.)
    (.load (-> (Thread/currentThread)
	       (.getContextClassLoader)
	       (.getResourceAsStream file)))))

(defn find-page-file
  "Finds the file for the supplied page in the supplied site directory."
  [site-dir page]
  (let [pages-dir (str site-dir "/pages")
	dir (java.io.File. pages-dir)
	files (.listFiles dir)]
    (loop [file (first files)
	   files (rest files)]
      (let [file-name (.getName file)]
	(if (.startsWith file-name page)
	  (str pages-dir "/" file-name)
	  (if (not (empty? files))
	    (recur (first files) (rest files))
	    nil))))))

(defn get-page
  "Returns the contents of the supplied page."
  [page]
  (let [site-dir (.getProperty (load-properties "candorcms.properties")
			       "site.dir")]
    (if (empty? site-dir)
      (html [:html
	     [:head [:title "Candor CMS - No site configured"]]
	     [:body
	      [:p "No site has been configured for Candor CMS."]
	      [:p "Please edit the file " [:em "candorcms.properties"]
	       " inside the web application archive."]]])
      (slurp (find-page-file site-dir page)))))

(defroutes all
  (GET "/" [] (get-page "index"))
  (GET "/:page" [page] (get-page page)))

(defservice all)
