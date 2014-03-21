Foundation Libraries for Java 7+
=============


(This is a work in progress: the blog post isn't published yet and the artifact isn't in the Maven central yet.)

GitHub has a rendered version of this readme: https://github.com/stevewedig/foundation/blob/master/README.md

This project extends Google Guava's core libraries for Java 7+. (what is guava.) (along those lines, what does foundation add?)

**Foundation's Features Explained via Blog Posts:**
* [Immutable Value Objects in Java & Python](http://stevewedig.com)
* router
* Not explained in blog posts: collect, etc, ...

**Foundation's Design Goals:**
* **Quality**: Consistent with the conventions, standards, & practices outlined in [Why & How I Write Java](http://stevewedig.com/2014/02/17/why-and-how-i-write-java/#how)
* **Generic**: ... useful in many contexts and application, only depend on Guava
* **Low Tech**: ... annotation, code gen, easy to read (rather than fancy/complex impl)
* **Compatabile with Google Web Toolkit (GWT)**: ... only use subset of guava supported by gwt
* **Extend Guava**: Build on Guava's libraries & practices. When possible, try to design interfaces to feel consistent with Guava.

**Project Information:**
* **GitHub Repo**: https://github.com/stevewedig/foundation
* **License**: This project is in the public domain via [Unlicense](http://unlicense.org).

**Other Related Blog Posts:**
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

## Using the Library

### Maven Dependency Snippet

The easiest way to use this library is add it your dependency list in your Maven [pom.xml](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html) file:

    <dependencies>
        <dependency>
            <groupId>com.stevewedig</groupId>
            <artifactId>foundation</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>

### Other Build Tool Dependency Snippets

The [Maven Central artifact page](http://search.maven.org/#artifactdetails%7Ccom.stevewedig%7Cfoundation%7C1.0.0%7Cjar) has snippets for other tools like Buildr, Ivy, and SBT.


### Copying Library Files

Alternatively you can just copy the library files into your own codebase. The code depends on [Google Guava](https://code.google.com/p/guava-libraries/).

### Adding to your GWT module file (if using GWT)

In addition to providing the dependency code, add this line to your .gwt.xml file:

    <inherits name="com.stevewedig.foundation.Foundation" />


