Candor CMS
==========

A simple CMS system written in Clojure.

It's still in early development.

Planned features
----------------

### File-based

There will be no database, everything will be stored in text files on
the filesystem. Naturally, these files can be edited with a text
editor or managed by a version control system.

### Web interface

The full functionality of the CMS system will be availble through a
simple web interface.

### Template language

There will be a template language either based on something like
[Mustache](http://mustache.github.com/) or a self-written one similar
to Radius from [Radiant CMS](http://radiantcms.org/).

### Plugins

It will be possible to extend Candor CMS with additional functionality
by writing Clojure scripts.

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
