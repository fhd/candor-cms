(ns com.github.fhd.candorcms.mustache
  "A parser for the Mustache template language."
  (:import (java.io BufferedReader StringReader)))

(defn- replace-all
  "Applies all replacements from the replacement list on the string."
  [string replacements]
  (reduce (fn [str [from to]]
            (.replaceAll str from to)) string replacements))

(defn- escape
  "Replaces angle brackets with the respective HTML entities."
  [string]
  (replace-all string [["<" "&lt;"] [">" "&gt;"]]))

(defn- create-replacements
  "Creates pairs of replacements from the data."
  [data]
  (apply concat
         (for [k (keys data)]
           (let [tag-name (name k)
                 value (k data)]
             (if (instance? String value)
               [[(str "\\{\\{\\{" tag-name "\\}\\}\\}") value]
                [(str "\\{\\{" tag-name "\\}\\}") (escape value)]])))))

(defn render
  "Renders the template with the data."
  [template data]
  (let [reader (BufferedReader. (StringReader. template))
        builder (StringBuilder.)
        lines (line-seq reader)
        replacements (create-replacements data)]
    (doseq [line lines]
      (.append builder (str (replace-all line replacements)
                            (if (not (= line (last lines))) "\n"))))
    (.toString builder)))
