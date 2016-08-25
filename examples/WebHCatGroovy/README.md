## Overview

This example shows how to use WebHCat to submit Hive, MapReduce and Pig jobs to a BigInsights cluster and wait for the job response.  An example use cases for programmatically running Map/Reduce jobs on the BigInsights cluster is a client application that needs to process data in BigInsights and send the results of processing to a third party application.  Here the client application could be an [ETL](https://en.wikipedia.org/wiki/Extract,_transform,_load) tool job, a [Microservice](https://en.wikipedia.org/wiki/Microservices), or some other custom application. 

This example uses [WebHCat](https://cwiki.apache.org/confluence/display/Hive/WebHCat) to run and monitor the three types of job: Hive, Map-Reduce and Pig.  

WebHCat, previously known as Templeton is the REST API for HCatalog, a table and storage management layer for Hadoop

BigInsights for Apache Hadoop clusters are secured using [Apache Knox](https://knox.apache.org/).  Apache Knox is a REST API Gateway for interacting with Apache Hadoop clusters.  The Apache Knox project provides a [Java client library](https://cwiki.apache.org/confluence/display/KNOX/Client+Usage) and this can be used for interacting with the Oozie REST API.  Using a library is usally more productive because the library provides a higher level of abstraction than when working directly with the REST API.

See also:

- [Using the HCatalog REST API](https://cwiki.apache.org/confluence/display/Hive/WebHCat+UsingWebHCat)
- [BigInsights Apache Knox documentation](https://www.ibm.com/support/knowledgecenter/en/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.admin.doc/doc/knox_overview.html)

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with compiling and running java applications
- Able to read code written in a high level language such as [Groovy](http://www.groovy-lang.org/)
- Familiar with the [Gradle](https://gradle.org/) build tool
- Familiar with Map/Reduce, Hive and Pig concepts
- Familiar with the [WebHdfsGroovy Example](../WebHdfsGroovy)

## Example Requirements

You have met the [pre-requisites](../../README.md#pre-requisites) and have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)

For Sqoop example, make sure to specify following in your connection.properties file:
```
dashdb_sqoop_url:jdbc:db2://changeme:changeme/BLUDB:sslConnection=true;
dashdb_sqoop_username:changeme
dashdb_sqoop_password:changeme
```

## Run the example

This example consists of:

- [Hive.groovy](./Hive.groovy) - Groovy script to submit the Hive job via WebHCat
- [MapReduce.groovy](./MapReduce.groovy) - Groovy script to submit the Map/Reduce job via WebHCat
- [Pig.groovy](./Pig.groovy) - Groovy script to submit the Pig job via WebHCat
- [Sqoop.groovy](./Sqoop.groovy) - Groovy script to submit the Sqoop job via WebHCat
- [build.gradle](./build.gradle) - Gradle script to compile and package the Map/Reduce code and execute each groovy script 

To run the examples, open a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Example` (OS X / *nix)
      - `gradlew.bat Example` (Windows)

You can run individual example: 

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Hive` (OS X / *nix)
      - `gradlew.bat Hive` (Windows)
   - some output from running the command on my machine is shown below 
   
```bash
$ cd examples/WebHCatGroovy
$ ./gradlew Hive
:compileJava UP-TO-DATE
:compileGroovy UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:Hive
[Hive.groovy] Delete /user/biadmin/test: 200
[Hive.groovy] Mkdir /user/biadmin/test: 200
[Hive.groovy] Copy Hive query file to HDFS
[Hive.groovy] Submitted job: job_1469408964315_0126
[Hive.groovy] Polling up to 240s for job completion...
.........................................................
[Hive.groovy] Job status: true
[exit, stderr, stdout]
[Hive.groovy] Content of stdout:
set TABLE_NAME
TABLE_NAME=biadmin_temp_1470349178741


create table if not exists ${hiveconf:TABLE_NAME} ( id int, name string )

insert into table ${hiveconf:TABLE_NAME} values (1, "Hello World!")

select * from ${hiveconf:TABLE_NAME}
1	Hello World!

drop table ${hiveconf:TABLE_NAME}


BUILD SUCCESSFUL

Total time: 1 mins 13.49 secs
```

The output above shows:

- the steps performed by gradle (command names are prefixed by ':' such as :compileJava) 
- the steps performed by the Hive.groovy script prefixed with [Hive.groovy]
- the Hive Job output from content of stdout
 
To run Map-Reduce example, use `./gradlew MapReduce`

```
$ cd examples/WebHCatGroovy
$ ./gradlew MapReduce
:compileJava UP-TO-DATE
:compileGroovy UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:jar UP-TO-DATE
:MapReduce
[MapReduce.groovy] Delete /user/biadmin/test: 200
[MapReduce.groovy] Mkdir /user/biadmin/test: 200
[MapReduce.groovy] Copy input file to HDFS
[MapReduce.groovy] Copy jar file to HDFS
[MapReduce.groovy] Submitted job: job_1469408964315_0128
[MapReduce.groovy] Polling up to 60s for job completion...
................................................
[MapReduce.groovy] Job status: true
[_SUCCESS, part-r-00000]

BUILD SUCCESSFUL

Total time: 1 mins 2.985 secs
```

The output above shows:

- the steps performed by gradle (command names are prefixed by ':' such as :compileJava) 
- the steps performed by the MapReduce.groovy script prefixed with [MapReduce.groovy]
- the MapReduce Job output from content of stdout
 
To run Pig example, use `./gradlew Pig`

```
$ cd examples/WebHCatGroovy
$ ./gradlew Pig
:compileJava UP-TO-DATE
:compileGroovy UP-TO-DATE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:Pig
[Pig.groovy] Delete /user/biadmin/test: 200
[Pig.groovy] Mkdir /user/biadmin/test: 200
[Pig.groovy] Copy Pig query file to HDFS
[Pig.groovy] Copy data file to HDFS
[Pig.groovy] Submitted job: job_1469408964315_0124
[Pig.groovy] Polling up to 60s for job completion...
...................................................
[Pig.groovy] Job status: true
[exit, stderr, stdout]
[Pig.groovy] Content of stdout:
(ctdean)
(pauls)
(carmas)
(dra)


BUILD SUCCESSFUL

Total time: 1 mins 9.279 secs
```

The output above shows:

- the steps performed by gradle (command names are prefixed by ':' such as :compileJava) 
- the steps performed by the Pig.groovy script prefixed with [Pig.groovy]
- the Pig Job output from content of stdout

To Run Sqoop example, use `./gradlew Sqoop`
```
$ cd examples/WebHCatGroovy
$ ./gradlew Sqoop
:compileJava UP-TO-DATE
:compileGroovy
Note: /Users/pierreregazzoni/BigInsights-on-Apache-Hadoop/examples/WebHCatGroovy/src/main/java/org/apache/hadoop/examples/WordCount.java uses or overrides a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
:processResources UP-TO-DATE
:classes
:Sqoop
[Sqoop.groovy] Delete /user/biadmin/sqoop: 200
[Sqoop.groovy] Mkdir /user/biadmin/sqoop: 200
[Sqoop.groovy] Submitted job: job_1471997327942_0112
[Sqoop.groovy] Polling up to 60s for job completion...
..............................................
[Sqoop.groovy] Job status: true
[exit, stderr, stdout]

[Sqoop.groovy] Content of stderr:
log4j:WARN custom level class [Relative to Yarn Log Dir Prefix] not found.
16/08/25 13:18:08 INFO sqoop.Sqoop: Running Sqoop version: 1.4.6_IBM_27

...

	File Output Format Counters 
		Bytes Written=87
16/08/25 13:18:34 INFO mapreduce.ImportJobBase: Transferred 87 bytes in 22.8155 seconds (3.8132 bytes/sec)
16/08/25 13:18:34 INFO mapreduce.ImportJobBase: Retrieved 3 records.

[_SUCCESS, part-m-00000]

[Sqoop.groovy] Content of part-m-00000
b,N/A (less than 5 years old)
1,Yes, speaks another language
2,No, speaks only English


BUILD SUCCESSFUL

Total time: 59.665 secs

```

The output above shows:

- the steps performed by gradle (command names are prefixed by ':' such as :compileJava) 
- the steps performed by the Pig.groovy script prefixed with [Sqoop.groovy]
- the Sqoop Job output from content of stderr
- the content of the table that was 'sqooped' from dashdb to HDFS

**NOTE**: The Sqoop example use a local patch for [JIRA KNOX-743](https://issues.apache.org/jira/browse/KNOX-743)
 
## Decomposition Instructions

The examples uses a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.  The build.gradle does the following:

- compile the Map/Reduce code.  The `apply plugin: 'java'` statement controls this [more info](https://docs.gradle.org/current/userguide/java_plugin.html).
- create a Jar file with the compiled Map/Reduce code for MapReduce example.  The `jar { ... }` statement controls this [more info](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.bundling.Jar.html).
- compile and execute Groovy scripts.

Each example script performs the following steps:

- Create a HTTP session on the BigInsights Knox REST API 
- Upload required query file, jar file and/or data file over WebHDFS
- Submit the job using Knox REST API for WebHCat
- Every second, check the status of the job using Knox REST API for WebHCat
- When the job successfully finishes, check and download the output from the job

All code is well commented and it is suggested that you browse the build.gradle and *.groovy scripts to understand in more detail how they work.


