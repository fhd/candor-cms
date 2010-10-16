Candor CMS
==========

A simple CMS system written in Clojure.

It's still in early development. Have a look at the
[demo](http://github.com/fhd/candor-cms-demo) to see its present
capabilities.

Candor CMS is heavily inspired by [Radiant](http://radiantcms.org/)
and [Textpattern](http://textpattern.com/), two simple and powerful
CMS systems that just didn't quite meet my needs.

[![Flattr this](http://api.flattr.com/button/button-compact-static-100x17.png "Flattr this")](http://flattr.com/thing/72693/Candor-CMS)

Features
--------

### File-based

There is no database, everything is stored in text files on the
file system. Naturally, these can be edited with a text editor or
managed by a version control system.

### Established template language

Candor CMS uses the [mustache](http://mustache.github.com/) template
language - no weird XML dialect.

Planned features
----------------

### Web interface

The full functionality of the CMS system will be available through a
simple web interface.

### Polyglot extensions

I believe in choice, thus it will be possible to extend Candor CMS
with additional functionality using several dynamic JVM languages. The
first stable release will at least support Clojure and Groovy, but in
the future there will probably be support for Scala, Jython, JRuby
etc.

Concepts
--------

A Candor CMS website consists of pages, templates and articles. The
system is completely file-based, so each of these are simple files
placed in respectively named directories.

### Pages

All files in the *pages* directory represent distinct pages of the
website, each mapping to a different URL. These files can either be
requested by their full name (e.g. http://example.org/index.html) or
by their basename (e.g. http://example.org/index).

### Templates

All files in the *templates* directory provide a way to reuse code
among pages using the [mustache](http://mustache.github.com/) template
language.

### Articles

All files in the *articles* directory provide a way to add additional
content to pages. Files are organised into subdirectories, which is
named like the basename of the page where the contained articles
should appear. Pages and templates can list and display articles.

Building
--------

Candor CMS sites are stored in the file system, not within the web
application archive or a database, so the first thing you need to do
is configure the absolute path to your site in the file
*src/main/resources/candorcms.properties*. If you don't have a site
yet, download the [demo](http://github.com/fhd/candor-cms-demo).

In order to build and run Candor CMS, you have to get
[Apache Maven](http://maven.apache.org/).

To run Candor CMS quickly in a local server, execute:

	mvn jetty:run
	
And navigate to [http://localhost:8080](http://localhost:8080).

To create a WAR archive that can be deployed on a Java application
server like [Apache Tomcat](http://tomcat.apache.org/), execute:

	mvn package

License
-------

Copyright (C) 2010 Felix H. Dahlke

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; see the file COPYING. If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
Boston, MA 02110-1301, USA.
