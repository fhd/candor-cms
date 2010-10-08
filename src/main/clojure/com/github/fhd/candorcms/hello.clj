(ns com.github.fhd.candorcms.hello
  (:gen-class
   :extends javax.servlet.http.HttpServlet))

(defn -doGet [_ request response]
  (.setContentType response "text/plain")
  (let [w (.getWriter response)]
    (.println w "Hello, World!")))

(defn -doPost [_ request response]
  (-doGet nil request response))