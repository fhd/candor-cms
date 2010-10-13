(ns com.github.fhd.candorcms.core
  "The core service of Candor CMS, responsible for retrieving the pages."
  (:use compojure.core
	ring.util.servlet
	hiccup.core)
  (:gen-class
   :extends javax.servlet.http.HttpServlet))

(def index-page-name "index")

(defstruct page :url :title :template :content)

(defn load-properties
  "Loads all properties from the specified file."
  [file]
  (doto (java.util.Properties.)
    (.load (-> (Thread/currentThread)
	       (.getContextClassLoader)
	       (.getResourceAsStream file)))))

(defn load-pages
  "Loads all available pages."
  [site-dir]
  (let [pages-dir (str site-dir "/pages")
	dir (java.io.File. pages-dir)
	files (.listFiles dir)]
    (into {} (for [file files]
	       (let [name (.getName file)
		     simple-name (.substring name 0 (.indexOf name "."))
		     content (slurp (str pages-dir "/" name))]
		 [(keyword simple-name)
		  (struct page
			  (str "/" (if (not (= simple-name index-page-name))
				     simple-name))
			  "Home" ;; TODO: Read from the header.
			  "layout" ;; TODO: Read from the header.
			  content)])))))

(defn load-templates
  "Loads all available templates."
  [site-dir]
  (let [templates-dir (str site-dir "/templates")
	dir (java.io.File. templates-dir)
	files (.listFiles dir)]
    (into {} (for [file files]
	       (let [name (.getName file)
		     simple-name (.substring name 0 (.indexOf name "."))
		     content (slurp (str templates-dir "/" name))]
		 [(keyword simple-name) content])))))

(defn expand-template
  "Expands (i.e. applies) the template of the supplied page."
  [template data]
  (:content data)) ;; TODO: Actually apply the template.

(defn get-page
  "Returns the contents of the supplied page."
  [page-name]
  (let [site-dir (.getProperty (load-properties "candorcms.properties")
			       "site.dir")]
    (if (empty? site-dir)
      (html [:html
	     [:head [:title "Candor CMS - No site configured"]]
	     [:body
	      [:p "No site has been configured for Candor CMS."]
	      [:p "Please edit the file " [:em "candorcms.properties"]
	       " inside the web application archive."]]])
      (let [pages (load-pages site-dir)
	    page ((keyword page-name) pages)
	    templates (load-templates site-dir)]
	(expand-template (:content ((keyword (:template page)) templates))
			 {:title (:title page)
			  :pages pages
			  :content (:content page)})))))

(defroutes all
  (GET "/" [] (get-page index-page-name))
  (GET "/:page" [page] (get-page page)))

(defservice all)
