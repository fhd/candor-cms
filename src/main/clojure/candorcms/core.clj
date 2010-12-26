(ns candorcms.core
  "The core service of Candor CMS, responsible for retrieving the pages."
  (:use compojure.core
        ring.util.servlet
        hiccup.core
        clostache.parser
        candorcms.storage)
  (:import java.util.Properties)
  (:gen-class
   :extends javax.servlet.http.HttpServlet))

(defn load-properties
  "Loads all properties from the specified file."
  [file]
  (doto (Properties.)
    (.load (-> (Thread/currentThread)
               (.getContextClassLoader)
               (.getResourceAsStream file)))))

(defn- no-site-configured
  "Returns an error page explaining that no site was configured."
  []
  (html [:html
         [:head [:title "Candor CMS - No site configured"]]
         [:body
          [:p "No site has been configured for Candor CMS."]
          [:p "Please edit the file " [:em "candorcms.properties"]
           " inside the web application archive."]]]))

(defn- page-not-found
  "Returns the standard 404 page."
  []
  {:status 404
   :body (html [:html
                [:head [:title "Candor CMS - Page not found"]]
                [:body
                 [:h1 "Page not found"]
                 [:p "Move along, nothing to see here!"]]])})

(defn get-page
  "Returns the contents of the page. If article-name is given, only that single
article is shown."
  [page-name & article-name]
  (let [properties (load-properties "candorcms.properties")
        site-dir (.getProperty properties "site.dir")]
    (if (empty? site-dir)
      (no-site-configured)
      (let [pages (load-pages site-dir)
            page ((keyword page-name) pages)]
        (if (nil? page)
          (page-not-found)
          (let [templates (load-templates site-dir)
                template ((keyword (:template page)) templates)
                articles (load-articles site-dir page-name)
                selected-articles (if (nil? article-name)
                                    (vec (sort-by :date (vals articles)))
                                    [((keyword (first article-name))
                                      articles)])]
            (if (and (= (count selected-articles) 1)
                     (nil? (first selected-articles)))
              (page-not-found)
              (let [data {:pages (vec (vals pages))
                          :articles selected-articles
                          :title (:title page)}
                    content (.trim (render (:content page) data))]
                (render template (conj data {:content content}))))))))))

(defroutes main-routes
  (GET "/" [] (get-page "index"))
  (GET "/:page" [page] (get-page page))
  (GET "/:page/" [page] (get-page page))
  (GET "/:page/:article" [page article] (get-page page article))
  (GET "/:page/:article/" [page article] (get-page page article)))

(defservice main-routes)
