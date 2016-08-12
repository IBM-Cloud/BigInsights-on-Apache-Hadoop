## Overview

This example shows how to connect to HIVE against BigInsights cluster using Hive Beeline shell tool. It sets up a [JDBC](https://en.wikipedia.org/wiki/Java_Database_Connectivity) connection to Hive Server.

Apache HIVE is data warehouse software to facilitate reading, writing, and managing large datasets residing in HDFS using SQL. Structure can be projected onto data already in HDFS (e.g. external tables). 

See also:

- [Apache Hive](https://hive.apache.org)
- [Hive Server2 Client](https://cwiki.apache.org/confluence/display/Hive/HiveServer2+Clients)

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with Database concepts and SQL language
- Familiar with the [Gradle](https://gradle.org/) build tool

## Example Requirements

- You meet the [pre-requisites](../../README.md#pre-requisites) in the top level [README](../../README.md)
- You have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)
- You are running on a OSX or Linux machine

Specificaly, ensure you have run from top level repo directory the following tasks:
- `./gradlew DownloadCertificate` - to get SSL certificate

## Run the example

This example runs a hive beeline shell preconfigured to connect to your BigInsights environment.

To start Beeline, in a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew -q clean Example` (OS X / *nix)
      - `gradlew.bat -q clean Example` (Windows)
   - some output from running the command on my machine is shown below

```bash
$ cd examples/HiveBeeline
$ ./gradlew -q Example
>> Creating key store

[ant:exec] Result: 1
>> Starting Beeline and connect to Hive

Connecting to jdbc:hive2://bicloud-186-master-3.bicloud.com:10000/default;ssl=true;sslTrustStore=/Users/pierreregazzoni/BigInsights-on-Apache-Hadoop/examples/HiveBeeline/truststore.jks;
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/Users/pierreregazzoni/.gradle/caches/modules-2/files-2.1/org.apache.logging.log4j/log4j-slf4j-impl/2.4.1/1153bec315f388b2635c25cf97105ae9dce61b58/log4j-slf4j-impl-2.4.1.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/Users/pierreregazzoni/.gradle/caches/modules-2/files-2.1/ch.qos.logback/logback-classic/1.0.9/258c3d8f956e7c8723f13fdea6b81e3d74201f68/logback-classic-1.0.9.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/Users/pierreregazzoni/.gradle/caches/modules-2/files-2.1/org.slf4j/slf4j-log4j12/1.7.5/6edffc576ce104ec769d954618764f39f0f0f10d/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.apache.logging.slf4j.Log4jLoggerFactory]
ERROR StatusLogger No log4j2 configuration file found. Using default configuration: logging only errors to the console.
Connected to: Apache Hive (version 1.2.1-IBM-22)
Driver: Hive JDBC (version 2.0.0)
Transaction isolation: TRANSACTION_REPEATABLE_READ
Beeline version 2.0.0 by Apache Hive
0: jdbc:hive2://bicloud-186-master-3.bicloud.> 
```

You can then type any hive command, for example:

```
0: jdbc:hive2://bicloud-186-master-3.bicloud.> show tables;
+-----------+--+
| tab_name  |
+-----------+--+
| emp       |
| emp1      |
+-----------+--+
2 rows selected (1.197 seconds)
0: jdbc:hive2://bicloud-186-master-3.bicloud.> !q
Closing: 0: jdbc:hive2://bicloud-186-master-3.bicloud.com:10000/default;ssl=true;sslTrustStore=/Users/pierreregazzoni/BigInsights-on-Apache-Hadoop/examples/HiveBeeline/truststore.jks;
```

The output above shows:

- Create keystore as defined in [build.gradle](./build.gradle)
- Start beeline and create connection to Hive Server
- List tables after you enter `show tables;`
- You can quit shell by typing `!q`
 
## Decomposition Instructions

The example uses a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.  The build.gradle for this example does the following:

- Identify HIVE server2 to connect to
- Create key store with the SSL certificate. This is used as part of the JDBC connection
- Start Beeline and create connection to Hive server2

All code is well commented and it is suggested that you browse the build.gradle and *.groovy scripts to understand in more detail how they work.

## Improvement Ideas

To support Windows, it would be better if the build script downloads Hive 2 and configures it for the user similar to the approach taken for setting up SquirrelSQL in the [SquirrelSQL Example](../SquirrelSQL/build.gradle)
