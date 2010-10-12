0.1
===
* Implement snippets that can be included from pages using a template
  system like [Mustache](http://mustache.github.com) or something like
  Radius from [Radiant CMS](http://radiantcms.org).
* Add a way to include articles from pages and snippets.
* Implement Markdown support for articles (identify it based on the
  file extension like GitHub).
* Add doc strings

0.2
===
* Look at [HtmlUnit](http://htmlunit.sourceforge.net/) and see if it
  makes sense for test-driving the web interface.
* Implement a basic web interface to create, list, edit and delete
  articles, pages and snippets.
* Implement a StackOverflow-like preview for articles.
* Implement an optional WYSIWYG HTML editor for articles.

0.3
===
* Make it possible to add functionality using Clojure scripts.
* Create an extension to display an image gallery.

Ideas
=====
* Add RSS/Atom support.
* Add multi-user support.
* Add XML-RPC support.
* Add Git support to the web interface.
* Implement an additional back-end storing everything in a database.
* Set up a demo at Google App Engine.
* Add support for additional markup languages (Textile, OrgMode, ...)
* Add syntax highlighting to the web interface.
