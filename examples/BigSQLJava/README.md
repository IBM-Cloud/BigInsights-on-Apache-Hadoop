## Overview

This example shows how to connect and execute SQL against a BigInsights cluster using JAVA. It connects via [JDBC](https://en.wikipedia.org/wiki/Java_Database_Connectivity) to send request and process result sets.

Big SQL is a massively parallel processing ([MPP](https://en.wikipedia.org/wiki/Massively_parallel_\(computing\))) SQL engine that deploys directly on the physical Hadoop Distributed File System (HDFS) cluster. This SQL engine pushes processing down to the same nodes that hold the data. Big SQL uses a low-latency parallel execution infrastructure that accesses Hadoop data natively for reading and writing.

See also:

- [BigInsights Big SQL documentation](https://www.ibm.com/support/knowledgecenter/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.product.doc/doc/bi_sql_access.html)

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with Database concepts and SQL language
- Able to read code written in a high level language such as [Java](https://en.wikipedia.org/wiki/Java_\(programming_language\))
- Familiar with the [Gradle](https://gradle.org/) build tool

## Example Requirements

- Your cluster has Big SQL service installed
- You have met the [pre-requisites](../../README.md#pre-requisites) in the top level [README](../../README.md)
- You have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)

Specificaly, ensure you have run from top level repo directory the following tasks:

- `./gradlew DownloadCertificate` - to get SSL certificate
- `./gradlew DownloadLibs` - to get JDBC jar libraries

## Run the example

This example consists of the following examples:

- [Example.java](./src/main/java/Example.java) - example with simple jdbc connection to BigSQL

To run the one of the examples, open a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Example` (OS X / *nix)
      - `gradlew.bat Example` (Windows)
   - some output from running the command on my machine is shown below 

```bash
$ cd examples/BigSQLJava
$ ./gradlew Example
Defining custom 'clean' task when using the standard Gradle lifecycle plugins has been deprecated and is scheduled to be removed in Gradle 3.0
:CreateTrustStore
Certificate was added to keystore
:SetupLibs
Attepmting to copy /Users/pierreregazzoni/BigInsights-on-Apache-Hadoop/downloads/db2jcc.jar to /Users/pierreregazzoni/BigInsights-on-Apache-Hadoop/examples/BigSQLJava/lib
Attepmting to copy /Users/pierreregazzoni/BigInsights-on-Apache-Hadoop/downloads/db2jcc4.jar to /Users/pierreregazzoni/BigInsights-on-Apache-Hadoop/examples/BigSQLJava/lib
Attepmting to copy /Users/pierreregazzoni/BigInsights-on-Apache-Hadoop/downloads/db2jcc_license_cu.jar to /Users/pierreregazzoni/BigInsights-on-Apache-Hadoop/examples/BigSQLJava/lib
:compileJava
:processResources
:classes
:Connect

>> Connectivity test was successful.
:Example

BUILD SUCCESSFUL

Total time: 9.612 secs
```

The output above shows:

- the steps performed by gradle (command names are prefixed by ':' such as :compileJava) 
- CreateTrustore step creates the keystore using your cluster SSL certificate
- SetupLibs step copies the JDBC jars from the downloads directory into the lib directory
- Steps performed by each java code (output is prefixed by '[Connect](./src/main/java/Example.java)')

## Decomposition Instructions

The examples uses a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.  The build.gradle for this example does the following:

- perform the dependency steps. Example depends on following two tasks:
    - CreateTrustStore - This will create the keystore with the SSL certificate. This is used as part of the JDBC connection.
    - SetupLibs - This task will copy the JDBC driver files under `lib/` directory. Build.gradle also set runtime dependency via following line: `runtime fileTree(dir: 'lib', include: '*.jar')`
- compile and execute the Java code

All code is well commented and it is suggested that you browse the build.gradle and *.java source under `src/main/java/` to understand in more detail how they work.

