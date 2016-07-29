## Overview

This example shows how to compile a Java Map/Reduce program and programmatically submit it to a BigInsights cluster and wait for the job response.  An example use cases for programmatically running Map/Reduce jobs on the BigInsights cluster is a client application that needs to process data in BigInsights and send the results of processing to a third party application.  Here the client application could be an [ETL](https://en.wikipedia.org/wiki/Extract,_transform,_load) tool job, a [Microservice](https://en.wikipedia.org/wiki/Microservices), or some other custom application. 

This example uses [Apache Oozie](https://oozie.apache.org/) to run and monitor the Map/Reduce job.  Oozie is a workflow scheduler system to manage Apache Hadoop jobs.

BigInsights for Apache Hadoop clusters are secured using [Apache Knox](https://knox.apache.org/).  Apache Knox is a REST API Gateway for interacting with Apache Hadoop clusters.  The Apache Knox project provides a [Java client library](https://cwiki.apache.org/confluence/display/KNOX/Client+Usage) and this can be used for interacting with the Oozie REST API.  Using a library is usally more productive because the library provides a higher level of abstraction than when working directly with the REST API.

See also:

- [Oozie Map/Reduce cURL Example](../OozieWorkflowMapReduceCurl/README.md)
- [Oozie Map/Reduce Cookbook](https://cwiki.apache.org/confluence/display/OOZIE/Map+Reduce+Cookbook)
- [BigInsights Apache Knox documentation](https://www.ibm.com/support/knowledgecenter/en/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.admin.doc/doc/knox_overview.html)
- [BigInsights Oozie documentation](https://www.ibm.com/support/knowledgecenter/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.product.doc/doc/bi_oozie.html)

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with compiling and running java applications
- Able to read code written in a high level language such as [Groovy](http://www.groovy-lang.org/)
- Familiar with the [Gradle](https://gradle.org/) build tool
- Familiar with Map/Reduce concepts
- Familiar with Oozie concepts
- Familiar with the [WebHdfsGroovy Example](../WebHdfsGroovy)

## Example Requirements

You have met the [pre-requisites](../../README.md#pre-requisites) and have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)

## Run the example

This example consists of three parts:

- [WordCount.java](./src/main/java/org/apache/hadoop/examples/WordCount.java) - Map/Reduce Java code
- [Example.groovy](./Example.groovy) - Groovy script to submit the Map/Reduce job to Oozie
- [build.gradle](./build.gradle) - Gradle script to compile and package the Map/Reduce code and execute the Example.groovy script 

To run the example, open a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Example` (OS X / *nix)
      - `gradlew.bat Example` (Windows)
   - some output from running the command on my machine is shown below 

```bash
biginsight-bluemix-docs $ cd examples/OozieWorkflowMapReduceGroovy
biginsight-bluemix-docs/examples/OozieWorkflowMapReduceGroovy $ ./gradlew Example
compileJava
:compileGroovy
...
:processResources
:classes
:jar
:OozieMapReduce
[Example.groovy] Delete /user/snowch/test: 200
[Example.groovy] Mkdir /user/snowch/test: 200
[Example.groovy] Put /user/snowch/test/input/FILE: 201
[Example.groovy] Put /user/snowch/test/workflow.xml: 201
[Example.groovy] Put /user/snowch/test/lib/hadoop-examples.jar: 201
[Example.groovy] Submitted job: 0000005-160721161255658-oozie-oozi-W
[Example.groovy] Polling up to 60s for job completion...
[Example.groovy] ...........................
[Example.groovy] Job status: SUCCEEDED
[Example.groovy] [_SUCCESS, part-r-00000]
[Example.groovy] Mapreduce output:
********************************************************************************
"AS 2
"Contribution"  1
"Contributor"   1
"Derivative 1
"Legal  1
"License"   1
"License"); 1
"Licensor"  1
"NOTICE"    1
"Not    1
"Object"    1
...
your    4
{name   1
{yyyy}  1

********************************************************************************
:Example

BUILD SUCCESSFUL

Total time: 39.642 secs
```

The output above shows:

- the steps performed by gradle (command names are prefixed by ':' such as :compileJava) 
- the steps performed by the Example.groovy script (output is prefixed by '[Example.groovy]')
- the Map/Reduce output which is the count of the number of times each word is seen in an Apache 2.0 License file
 
## Decomposition Instructions

The examples uses a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.  The build.gradle does the following:

- compile the Map/Reduce code.  The `apply plugin: 'java'` statement controls this [more info](https://docs.gradle.org/current/userguide/java_plugin.html).
- create a Jar file with the compiled Map/Reduce code.  The `jar { ... }` statement controls this [more info](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.bundling.Jar.html).
- compile and execute the [Example.groovy](./Example.groovy) script.  The `task('OozieMapReduce' ...)` statement controls this [more info](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html)

The [Example.groovy](./Example.groovy) script performs the following:

- Create an Oozie workflow XML document
- Create an Oozie configuration XML document
- Create a HTTP session on the BigInsights Knox REST API 
- Upload the workflow XML file over WebHDFS
- Upload an Apache 2.0 LICENSE file over WebHDFS
- Upload the jar file (containing the Map/Reduce code) over WebHDFS
- Submit the Oozie workflow job using Knox REST API for Oozie
- Every second, check the status of the Oozie workflow job using Knox REST API for Oozie
- When the Oozie workflow job successfully finishes, download the wordcount output from the Map/Reduce job

All code is well commented and it is suggested that you browse the build.gradle and *.groovy scripts to understand in more detail how they work.


