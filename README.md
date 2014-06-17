Foundation Libraries for Java 7+
=============

(GitHub has a rendered version of this readme: https://github.com/stevewedig/foundation/blob/master/README.md)

We all build up a pile of util code over time. Foundation is my effort to pull out the most useful and generally applicable pieces of my util codebase, sharing them via GitHub & Maven, and explaining them on my [blog](http://stevewedig.com). I build on the core libraries of [Google Guava](https://code.google.com/p/guava-libraries/), so you can think of Foundation as an extension of Guava, which is an extension of Java's standard library. (FWIW, I think pretty much everyone should use Guava because provides fundamental tools including [Optional](https://code.google.com/p/guava-libraries/wiki/UsingAndAvoidingNullExplained), [immutable collections](https://code.google.com/p/guava-libraries/wiki/ImmutableCollectionsExplained), [important new collections (Multiset/Bag, Multimap, Bimap)](https://code.google.com/p/guava-libraries/wiki/NewCollectionTypesExplained), [collection manipulation utilities](https://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained), [functional idioms](https://code.google.com/p/guava-libraries/wiki/FunctionalExplained), and [a bunch more](https://code.google.com/p/guava-libraries/wiki/GuavaExplained).)

Foundation is compatable with Java 7+ and all code can be compiled to JavaScript by GWT 2.6+ ([Google Web Toolkit](https://code.google.com/p/guava-libraries/wiki/GuavaExplained)). Foundation's only runtime dependency is Guava.

* **GitHub Repo**: https://github.com/stevewedig/foundation
* **License**: This project is in the public domain via [Unlicense](http://unlicense.org).

## Foundation's features

#### Features explain via blog posts

Pending posts have code written that is in use. However cleaning code and writing posts is a slow process, so this will take a while...

* [Value Objects in Java & Python](http://stevewedig.com)
* Pending: Fallible: An Alternative to Throwing Exceptions in Java
* Pending: Typesafe Heterogenous Structs in Java
* Pending: Directed-Acyclic Graphs (DAGs) in Java
* Pending: Simple Dependency Injection Using DAGs in Java
* Pending: Framework Independent URL Routing in Java
* Pending: Typesafe Heterogenous Event Bus in Java
* Pending: Asynchronous Cached Downloads in Java
* Pending: Asynchronous Tasks with Dependency DAGs in Java
* Pending: View Flow: A UI Architecture Pattern

[foundation.util.libs](https://github.com/stevewedig/foundation/tree/master/src/main/java/com/stevewedig/foundation/util/libs) contains an assortment of self explanatory utility functions. These functions are organized into [StrLib](https://github.com/stevewedig/foundation/tree/master/src/main/java/com/stevewedig/foundation/util/StrLib.java), [LambdaLib](https://github.com/stevewedig/foundation/tree/master/src/main/java/com/stevewedig/foundation/util/LambdaLib.java), [CollectLib](https://github.com/stevewedig/foundation/tree/master/src/main/java/com/stevewedig/foundation/util/CollectLib.java), [MapLib](https://github.com/stevewedig/foundation/tree/master/src/main/java/com/stevewedig/foundation/util/MapLib.java), [SetLib](https://github.com/stevewedig/foundation/tree/master/src/main/java/com/stevewedig/foundation/util/SetLib.java), etc.

#### Other related blog posts

* [Dev Machine Setup: Java, Maven, Git](http://stevewedig.com)
* [Why & How I Write Java](http://stevewedig.com/2014/02/17/why-and-how-i-write-java/)
* [A Software Developer's Reading List](http://stevewedig.com/2014/02/03/software-developers-reading-list/)

## Foundation's design goals

* **Quality**: Consistent with the conventions, standards, & practices outlined in [Why & How I Write Java](http://stevewedig.com/2014/02/17/why-and-how-i-write-java/#how)
* **Generic**: ... useful in many contexts and application, only depend on Guava
* **Low Tech**: ... annotation, code gen, easy to read (rather than fancy/complex impl)
* **Extend Guava**: Build on Guava's libraries and generally try to make components feel consistent with Guava. Most objects defined in Foundation are immutable and use Guava's immutable collections and Optional. When necessary, Guava style fluent builders are provided for initializing immutable objects in type safe ways. Examples include the Guava-fied ToplogicalSort.java and builders for Dag, Params, Router, etc.
* **Compatabile with GWT (Google Web Toolkit)**: Last but not least, ... only use subset of guava supported by gwt
* are some components not infinitely generic like the http cache? covers a starting point. fork code if need more control or something else? talk about DRY being good but not perfect when client needs are divergent, and not best during the discovery/experimentation phase, where you want to efficiently learn use cases, rather than dragged down by backwards compatability during experimentation (this is forking with the intent to rejoin). 

## Get Code, Create Javadocs, Run Tests

Use [Git](http://en.wikipedia.org/wiki/Git_(software)) to get the project code:

    cd <PROJECT_ROOT_PARENT>
    git clone https://github.com/stevewedig/foundation.git

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

## Using Foundation

#### Maven Dependency Snippet

The easiest way to use this library is add it your dependency list in your Maven [pom.xml](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html) file:

    <dependencies>
        <dependency>
            <groupId>com.stevewedig</groupId>
            <artifactId>foundation</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>

#### Other Build Tool Dependency Snippets

The [Maven Central artifact page](http://search.maven.org/#artifactdetails%7Ccom.stevewedig%7Cfoundation%7C1.0.0%7Cjar) has snippets for other tools like Buildr, Ivy, and SBT.

#### Inheriting Foundation in your GWT module file

If you're using GWT, in addition to getting the code via Maven or other mechanism, add this line to your .gwt.xml file:

    <inherits name="com.stevewedig.foundation.Foundation" />


