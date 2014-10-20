# stu - Simple Test Utils

[![Build Status](https://buildhive.cloudbees.com/job/Seitenbau/job/stu/badge/icon)](https://buildhive.cloudbees.com/job/Seitenbau/job/stu/)

The project contains <b>s</b>imple <b>t</b>est <b>u</b>tils for JUnit and DBUnit. See [documentation](stu-documentation/src/stu.asciidoc) for more Details.

# Use STU in your project

## Use STU in a maven project

Repository needed in your project's pom.xml :

    <repository>
      <id>sb-utils-bintray</id>
      <name>Seitenbau utils bintrayrepository</name>
      <url>http://dl.bintray.com/seitenbau/utils</url>
    </repository>

Dependencies needed in your project's pom.xml :

    <dependency>
      <groupId>com.seitenbau.stu</groupId>
      <artifactId>stu-common</artifactId>
      <version>0.2.0-RC4</version>
    </dependency>
    <dependency>
      <groupId>com.seitenbau.stu</groupId>
      <artifactId>stu-database</artifactId>
      <version>0.2.0-RC4</version>
    </dependency>

# Use STU in a gradle project

    apply plugin: 'java'
    
    repositories {
      mavenCentral()
      maven { url "http://dl.bintray.com/seitenbau/utils" }
    }
    
    dependencies {
      compile("com.seitenbau.stu:stu-common:0.2.0-RC4")
      compile("com.seitenbau.stu:stu-database:0.2.0-RC4")
    }

# Build STU

Either use provided gradlew or you need gradle (<2.0) 1.8 installed. STU currently only compiles with a JDK 1.7.

	gradlew build


## Build Documentation
To generate the documentation please invoke:

	gradlew asciidoctor

The documentation is generated to stu/documentation/html/stu.html.

## Eclipse 

To import the project into eclipse invoke:

	gradlew eclipse
	
Then the project can be imported as eclipse projects.
Please be sure to use the code-conventions-sb-testing.xml file from src/eclipse folder.

## IntelliJ

To import the project into IntelliJ IDEA  invoke:

    gradlew idea

Then the project can be open in IntelliJ.When you use IntelliJ version > 12, IntelliJ has build in
gradle support  then the project can direct be opened. When the workspace is ready the gradle task generateDataSetDsl should be invoked.

# Documentation

The documentation can be found [here](stu-documentation/src/stu.asciidoc). The asciidoc files are stored in the subproject [stu-documentation](stu-documentation).
