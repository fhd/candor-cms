(ns com.github.fhd.candorcms.core
  "The core service of Candor CMS, responsible for retrieving the pages."
  (:use compojure.core
        ring.util.servlet
        hiccup.core
        com.github.fhd.clostache.parser)
  (:import (java.util Properties)
           (java.io File))
  (:gen-class
   :extends javax.servlet.http.HttpServlet))

(def index-page-name "index")

(defrecord Page [url title template content])
(defrecord Article [title content])
(defrecord Header [data end])

(defn- load-properties
  "Loads all properties from the specified file."
  [file]
  (doto (Properties.)
    (.load (-> (Thread/currentThread)
               (.getContextClassLoader)
               (.getResourceAsStream file)))))

(defn- parse-header
  "Parses the header section format into a map."
  [header]
  (into {} (for [line (.split header "\n")]
             (if (not (empty? line))
               (let [key-value (.split line "=")
                     key (aget key-value 0)
                     value (aget key-value 1)]
                 [(keyword key) (.trim value)])))))

(defn- extract-header
  "Extracts the header section from the content."
  [content]
  (let [end (.indexOf content "-->")
        header (parse-header (.substring content 4 end))]
    (Header. header (+ end 3))))

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
                     header (extract-header content)
                     header-data (:data header)
                     body (.trim (.substring content (:end header)))]
                 [(keyword simple-name)
                  (Page. url
                         (:title header-data)
                         (:template header-data) body)])))))

(defn- load-templates
  "Loads all available templates."
  [site-dir]
  (let [templates-dir (str site-dir "/templates")]
    (into {} (for [file (.listFiles (File. templates-dir))]
               (let [name (.getName file)
                     simple-name (.substring name 0 (.indexOf name "."))
                     content (slurp (str templates-dir "/" name))]
                 [(keyword simple-name) content])))))

(defn- load-articles
  "Loads all articles for the page."
  [site-dir page]
  (let [articles-dir (str site-dir "/articles/" page)]
    (vec (for [file (.listFiles (File. articles-dir))]
           (let [name (.getName file)
                 content (slurp (str articles-dir "/" name))
                 header (extract-header content)
                 body (.trim (.substring content (:end header)))]
             (Article. (:title (:data header)) body))))))

(defn get-page
  "Returns the contents of the page."
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
            page ((keyword page-name) pages)]
        (if (nil? page)
          {:status 404
           :body (html [:html
                        [:head [:title "Candor CMS - Page not found"]]
                        [:body
                         [:h1 "Page not found"]
                         [:p "Move along, nothing to see here!"]]])}
          (let [templates (load-templates site-dir)
                template ((keyword (:template page)) templates)
                articles (load-articles site-dir page-name)
                data {:pages (vec (vals pages))
                      :articles articles
                      :title (:title page)}
                content (render (:content page) data)]
            (render template (conj data {:content content}))))))))

(defroutes main-routes
  (GET "/" [] (get-page index-page-name))
  (GET "/:page" [page] (get-page page)))

(defservice main-routes)
