0.1
===
* Move page/article/template retrieval to a separate namespace.
* Make the page/article comment header optional and define default values.
* Sort articles on pages by date.
* Implement Markdown support for articles (identify it based on the
  file extension like GitHub).
* Deal with trailing slashes in URLs.
* Add article pagination.
* Add article archives.
* Make it possible to define custom 404 pages.

0.2
===
* Implement a basic web interface to create, list, edit and delete
  articles, pages and templates.
* Implement a StackOverflow-like live preview for articles.
* Add an optional WYSIWYG HTML editor for articles.

0.3
===
* Make it possible to add functionality using Clojure.
* Create an extension to display an image gallery.
* Make it possible to add functionality using Groovy.

Ideas
=====
* Add multi-user support.
* Add XML-RPC support.
* Add Git support to the web interface.
* Implement an additional back-end storing everything in a database.
* Set up a demo at Google App Engine.
* Add support for additional markup languages (Textile, OrgMode, ...)
* Add syntax highlighting to the web interface.
* Improve performance, add caching etc.
* Add support for more JVM languages, e.g. Scala, Jython or JRuby.
