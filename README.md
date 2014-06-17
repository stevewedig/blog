Foundation Libraries for Java 7+
=============

(GitHub has a rendered version of this readme: https://github.com/stevewedig/foundation/blob/master/README.md)

We all build up a pile of util code over time. This project is my effort to pull out the most useful and generic pieces of my util code, share them via GitHub & Maven, and explain them on my [blog](http://stevewedig.com). I build on the core libraries of [Google Guava](https://code.google.com/p/guava-libraries/), so you can think of Foundation as an extension of Guava, which itself is an extension of Java's standard library. 

(FWIW, I think pretty much everyone should use Guava because it provides fundamental tools including [Optional](https://code.google.com/p/guava-libraries/wiki/UsingAndAvoidingNullExplained), [immutable collections](https://code.google.com/p/guava-libraries/wiki/ImmutableCollectionsExplained), [important new collections (Multiset/Bag, Multimap, Bimap)](https://code.google.com/p/guava-libraries/wiki/NewCollectionTypesExplained), [collection manipulation utilities](https://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained), [functional idioms](https://code.google.com/p/guava-libraries/wiki/FunctionalExplained), and [a bunch more](https://code.google.com/p/guava-libraries/wiki/GuavaExplained). That being said it doesn't cover everything, so you still have to reach into the [Apache Commons](http://commons.apache.org/) from time to time.)

Foundation is compatable with Java 7+ and all code can be compiled to JavaScript by GWT 2.6+ ([Google Web Toolkit](https://code.google.com/p/guava-libraries/wiki/GuavaExplained)). Foundation's only runtime dependency is Guava.

* **GitHub Repo**: https://github.com/stevewedig/foundation
* **License**: This project is in the public domain via [Unlicense](http://unlicense.org).

## Foundation's features

#### Features explained via blog posts

Pending posts have code written that is in use, however cleaning code and writing posts is a slow process, so this will take a while...

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

#### Utilities not explained via blog posts

* [StrLib](https://github.com/stevewedig/foundation/tree/master/src/main/java/com/stevewedig/foundation/util/StrLib.java): ...
* [LambdaLib](https://github.com/stevewedig/foundation/tree/master/src/main/java/com/stevewedig/foundation/util/LambdaLib.java): ...
* [CollectLib](https://github.com/stevewedig/foundation/tree/master/src/main/java/com/stevewedig/foundation/util/CollectLib.java): ...
*  [MapLib](https://github.com/stevewedig/foundation/tree/master/src/main/java/com/stevewedig/foundation/util/MapLib.java): ...
* [SetLib](https://github.com/stevewedig/foundation/tree/master/src/main/java/com/stevewedig/foundation/util/SetLib.java): ...
* etc.

#### Other related blog posts

* [Dev Machine Setup: Java, Maven, Git](http://stevewedig.com)
* [Why & How I Write Java](http://stevewedig.com/2014/02/17/why-and-how-i-write-java/)
* [A Software Developer's Reading List](http://stevewedig.com/2014/02/03/software-developers-reading-list/)

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


