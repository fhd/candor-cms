(ns com.github.fhd.candorcms.core
  "The core service of Candor CMS, responsible for retrieving the pages."
  (:use compojure.core
        ring.util.servlet
        hiccup.core
        com.github.fhd.candorcms.mustache)
  (:import (java.util Properties)
           (java.io File))
  (:gen-class
   :extends javax.servlet.http.HttpServlet))

(def index-page-name "index")

(defstruct page :url :title :template :content)

(defn- load-properties
  "Loads all properties from the specified file."
  [file]
  (doto (Properties.)
    (.load (-> (Thread/currentThread)
               (.getContextClassLoader)
               (.getResourceAsStream file)))))

(defn- parse-header
  "Parses the page header format into a map."
  [header]
  (into {} (for [line (.split header "\n")]
             (if (not (empty? line))
               (let [key-value (.split line "=")
                     key (aget key-value 0)
                     value (aget key-value 1)]
                 [(keyword key) value])))))

(defn- load-pages
  "Loads all available pages."
  [site-dir]
  (let [pages-dir (str site-dir "/pages")]
    (into {} (for [file (.listFiles (File. pages-dir))]
               (let [name (.getName file)
                     simple-name (.substring name 0 (.indexOf name "."))
                     url (str "/" (if (not (= simple-name index-page-name))
                                    simple-name))
                     content (slurp (str pages-dir "/" name))
                     header-end (.indexOf content "-->")
                     header (parse-header (.substring content 4 header-end))
                     body (.trim (.substring content (+ header-end 3)))]
                 [(keyword simple-name)
                  (struct page
                          url
                          (:title header)
                          (:template header)
                          body)])))))

(defn- load-templates
  "Loads all available templates."
  [site-dir]
  (let [templates-dir (str site-dir "/templates")]
    (into {} (for [file (.listFiles (File. templates-dir))]
               (let [name (.getName file)
                     simple-name (.substring name 0 (.indexOf name "."))
                     content (slurp (str templates-dir "/" name))]
                 [(keyword simple-name) content])))))

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
            templates (load-templates site-dir)
            template ((keyword (:template page)) templates)]
        (render template
                {:title (:title page)
                 :pages pages
                 :content (:content page)})))))

(defroutes all
  (GET "/" [] (get-page index-page-name))
  (GET "/:page" [page] (get-page page)))

(defservice all)
