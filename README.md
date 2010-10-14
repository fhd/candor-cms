Candor CMS
==========

A simple CMS system written in Clojure.

It's still in early development. Have a look at the
[demo](http://github.com/fhd/candor-cms-demo) to see its present
capabilities.

[![Flattr this](http://api.flattr.com/button/button-compact-static-100x17.png "Flattr this")](http://flattr.com/thing/72693/Candor-CMS)

Features
--------

### File-based

There is no database, everything is stored in text files on the
filesystem. Naturally, these can be edited with a text editor or
managed by a version control system.

### Established template language

Candor CMS uses a template language based on
[Mustache](http://mustache.github.com/) - no weird XML dialect.

Planned features
----------------

### Web interface

The full functionality of the CMS system will be availble through a
simple web interface.

### Plugins

It will be possible to extend Candor CMS with additional functionality
by writing Clojure scripts.

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

Templates provide a way to reuse code among pages.

### Articles

*Not yet implemented.*

While pages and templates fulfil technical purposes, articles will
hold the actual content of the website. Pages and templates will be
able to list and display articles.

Building
--------

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
