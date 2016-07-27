## Overview

This example shows how to connect and execute HIVE SQL against BigInsights cluster. It connects via [JDBC](https://en.wikipedia.org/wiki/Java_Database_Connectivity) via Hive Server to send request and process result sets.

Apache HIVE is data warehouse software to facilitate reading, writing, and managing large datasets residing in HDFS using SQL. Structure can be projected onto data already in HDFS (e.g. external tables). 



See also:

- [BigInsights Apache Knox documentation](https://www.ibm.com/support/knowledgecenter/en/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.admin.doc/doc/knox_overview.html)
- [Apache Hive](https://hive.apache.org)

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with Database concepts and SQL language
- Able to read code written in a high level language such as [Groovy](http://www.groovy-lang.org/)
- Familiar with the [Gradle](https://gradle.org/) build tool

## Example Requirements

Your have met the [pre-requisites](../../README.md#pre-requisites) and have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)

Specificaly, ensure you have run from top level repo directory the following tasks:
- `./gradlew DownloadCertificate` - to get SSL certificate

## Run the example

There is one simple example in this folder written in the [Groovy Language](http://www.groovy-lang.org/):

- [Example.groovy](./Example.groovy) - this example connects to Hive via JDBC, Creates and Drops a table

To run the example [Example.groovy](./Example.groovy), in a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Example` (OS X / *nix)
      - `gradlew.bat Example` (Windows)
   - some output from running the command on my machine is shown below

```bash
$ cd examples/HiveGroovy
$ ./gradlew Example
>> Remove key store
>> Create key store
:compileJava UP-TO-DATE
:compileGroovy
:processResources UP-TO-DATE
:classes
:Example
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/Users/pierreregazzoni/.gradle/caches/modules-2/files-2.1/org.apache.logging.log4j/log4j-slf4j-impl/2.4.1/1153bec315f388b2635c25cf97105ae9dce61b58/log4j-slf4j-impl-2.4.1.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/Users/pierreregazzoni/.gradle/caches/modules-2/files-2.1/ch.qos.logback/logback-classic/1.0.9/258c3d8f956e7c8723f13fdea6b81e3d74201f68/logback-classic-1.0.9.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/Users/pierreregazzoni/.gradle/caches/modules-2/files-2.1/org.slf4j/slf4j-log4j12/1.7.5/6edffc576ce104ec769d954618764f39f0f0f10d/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.apache.logging.slf4j.Log4jLoggerFactory]
ERROR StatusLogger No log4j2 configuration file found. Using default configuration: logging only errors to the console.
>> Create table
>> Drop table

>> Hive test was successful.

BUILD SUCCESSFUL

Total time: 8.429 secs
```

The output above shows:

- remove and Create keystore as defined in [build.gradle](./build.gradle)
- the steps performed by gradle (command names are prefixed by ':' such as :compileGroovy) 
- execute groovy script to Create and Drop table in HIVE
 
## Decomposition Instructions

The examples uses a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.  The build.gradle for this example does the following:

- Identify HIVE server2 to connect to
- Create key store with the SSL certificate. This is used as part of the JDBC connection
- compile and execute the groovy code

All code is well commented and it is suggested that you browse the build.gradle and *.groovy scripts to understand in more detail how they work.

