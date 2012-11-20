(ns candorcms.storage
  "Retrieval of site files stored on the filesystem."
  (:import java.io.File))

(defn- parse-header
  "Parses the header section format into a map."
  [header]
  (into {} (for [line (.split header "\n")]
             (if (not (empty? line))
               (let [key-value (.split line "=")
                     key (aget key-value 0)
                     value (aget key-value 1)]
                 [(keyword key) (.trim value)])))))

(defrecord Header [data end])

(defn- extract-header
  "Extracts the header section from the content."
  [content]
  (let [end (.indexOf content "-->")]
    (if (> end 0)
      (let [data (parse-header (.substring content 4 end))]
        (Header. data (+ end 3)))
      (Header. {} 0))))

(defrecord Page [url title template content])

(defn load-pages
  "Loads all available pages from site-dir."
  [site-dir]
  (let [pages-dir (str site-dir "/pages")]
    (into {} (for [file (.listFiles (File. pages-dir))]
               (let [name (.getName file)
                     simple-name (.substring name 0 (.indexOf name "."))
                     url (str "/" simple-name)
                     content (slurp (str pages-dir "/" name))
                     header (extract-header content)
                     header-data (:data header)
                     body (.trim (.substring content (:end header)))]
                 [(keyword simple-name)
                  (Page. url
                         (:title header-data)
                         (:template header-data)
                         body)])))))

(defn load-templates
  "Loads all available templates from site-dir."
  [site-dir]
  (let [templates-dir (str site-dir "/templates")]
    (into {} (for [file (.listFiles (File. templates-dir))]
               (let [name (.getName file)
                     simple-name (.substring name 0 (.indexOf name "."))
                     content (slurp (str templates-dir "/" name))]
                 [(keyword simple-name) content])))))

(defrecord Article
    [url title date content])

(defn load-articles
  "Loads all articles for the page from site-dir."
  [site-dir page]
  (let [articles-dir (str site-dir "/articles/" page)]
    (into {} (for [file (.listFiles (File. articles-dir))]
               (let [name (.getName file)
                     simple-name (.substring name 0 (.indexOf name "."))
                     url (str "/" page "/" simple-name)
                     content (slurp (str articles-dir "/" name))
                     header (extract-header content)
                     header-data (:data header)
                     body (.trim (.substring content (:end header)))]
                 [(keyword simple-name)
                  (Article. url
                            (:title header-data)
                            (:date header-data)
                            body)])))))
