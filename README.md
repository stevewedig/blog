Steve Wedig's Libraries for Java 7+
=============

(GitHub has a rendered version of this readme: https://github.com/stevewedig/blog/blob/master/README.md)

We all build up a pile of util code over time. This project is my effort to share the most reusable parts of my codebase. I'll be describing the features on my [blog](http://stevewedig.com) and shared the code via GitHub & Maven.

I build on the core libraries of [Google Guava](https://code.google.com/p/guava-libraries/), so you can think of these libraries as an extension of Guava, which itself is an extension of Java's standard library.  (I think pretty much every Java developers should use Guava because it provides fundamental tools including [Optional](https://code.google.com/p/guava-libraries/wiki/UsingAndAvoidingNullExplained), [immutable collections](https://code.google.com/p/guava-libraries/wiki/ImmutableCollectionsExplained), [important new collections (Multiset/Bag, Multimap, Bimap)](https://code.google.com/p/guava-libraries/wiki/NewCollectionTypesExplained), [collection manipulation utilities](https://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained), [functional idioms](https://code.google.com/p/guava-libraries/wiki/FunctionalExplained), and [a bunch more](https://code.google.com/p/guava-libraries/wiki/GuavaExplained).)

This project is compatable with Java 7+ and all code can be compiled to JavaScript by GWT 2.6+ ([Google Web Toolkit](https://code.google.com/p/guava-libraries/wiki/GuavaExplained)). The only runtime dependency is Guava.

* **GitHub Repo**: https://github.com/stevewedig/blog
* **License**: This project is in the public domain via [Unlicense](http://unlicense.org).

## Features

#### Features explained via blog posts

Pending posts have code that is in use, but it will take a while to get all this published...

* [Value Objects in Java & Python](http://stevewedig.com)
* Pending: Typesafe Heterogenous Containers in Java
* Pending: Directed-Acyclic Graphs (DAGs) in Java
* Pending: Framework Free Dependency Injection in Java
* Pending: Fallible: Avoiding Exceptions in Java
* Pending: Framework Free URL Routing in Java
* Pending: Asynchronous Cached Downloads in Java
* Pending: Asynchronous Task Workflows in Java
* Pending: View Flows: A Lightweight UI Architecture Pattern

#### Utilities not explained via blog posts

These utilities should be fairly self explanatory...

* [StrLib](https://github.com/stevewedig/blog/tree/master/src/main/java/com/stevewedig/blog/util/StrLib.java): Utility functions for manipulating strings.
* [LambdaLib](https://github.com/stevewedig/blog/tree/master/src/main/java/com/stevewedig/blog/util/LambdaLib.java): Interfaces for creating anonymous lambdas (callbacks). Presumably I can get rid of this horrible syntax when I switch to Java 8.

#### Other related blog posts

* [Dev Machine Setup: Java, Maven, Git](http://stevewedig.com)
* [Why & How I Write Java](http://stevewedig.com/2014/02/17/why-and-how-i-write-java/)
* [A Software Developer's Reading List](http://stevewedig.com/2014/02/03/software-developers-reading-list/)

## Get Code, Create Javadocs, Run Tests

Use [Git](http://en.wikipedia.org/wiki/Git_(software)) to get the project code:

    cd <PROJECT_ROOT_PARENT>
    git clone https://github.com/stevewedig/blog.git

Use [Maven](http://en.wikipedia.org/wiki/Apache_Maven) to generate the site, including the [Javadocs](http://en.wikipedia.org/wiki/Javadoc):

    cd <PROJECT_ROOT>
    mvn site
    # open target/site/index.html

Use Maven to run the tests:
    
    cd <PROJECT_ROOT>
    mvn test

## Project Organization

The root directory is also a project for the [Eclipse IDE](http://en.wikipedia.org/wiki/Eclipse_(software)). If you are using Eclipse, you can import the project under "Import > General > Existing Projects into Workspace". If you are not using Eclipse, you can disregard or delete the Eclipse project metadata files: .classpath, .project, .settings.

* **Library code**: src/main/java/com/stevewedig/pure/util/value_objects
* **Test code**: src/test/java/com/stevewedig/pure/util/value_objects
* **Test file with an example**: src/test/java/com/stevewedig/pure/util/value_objects/TestValueObjectExample.java
* **Directory built by Maven**: target/
* **Project's site root**: target/site/index.html (Javadocs are linked to under "Project Reports")
* **Project's Javadoc root**: target/site/apidocs/index.html

More about the [standard layout of Maven projects](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html).

## Using blog

#### Maven Dependency Snippet

The easiest way to use this library is add it your dependency list in your Maven [pom.xml](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html) file:

    <dependencies>
        <dependency>
            <groupId>com.stevewedig</groupId>
            <artifactId>blog</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>

#### Other Build Tool Dependency Snippets

The [Maven Central artifact page](http://search.maven.org/#artifactdetails%7Ccom.stevewedig%7Cblog%7C1.0.0%7Cjar) has snippets for other tools like Buildr, Ivy, and SBT.

#### Inheriting blog in your GWT module file

If you're using GWT, in addition to getting the code via Maven or other mechanism, add this line to your .gwt.xml file:

    <inherits name="com.stevewedig.blog.blog" />


