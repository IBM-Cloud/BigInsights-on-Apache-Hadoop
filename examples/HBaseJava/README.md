## Overview

This example shows how to programmaticaly interact with Apache HBASE on the BigInsights cluster. It uses [HBASE REST api](http://hbase.apache.org/book.html#_rest).

Apache HBase provides efficient random, realtime read/write access to your data. It can store very large tables (billions of rows X millions of columns) on top of HDFS distributed file system.

BigInsights for Apache Hadoop clusters are secured using [Apache Knox](https://knox.apache.org/).  Apache Knox is a REST API Gateway for interacting with Apache Hadoop clusters.  The Apache Knox project provides a [Java client library](https://cwiki.apache.org/confluence/display/KNOX/Client+Usage) and this can be used for interacting with the [HBase REST API](http://hbase.apache.org/book.html#_rest).  Using a library is usally more productive because the library provides a higher level of abstraction than when working directly with the REST API.

See also:

- [BigInsights Apache Knox documentation](https://www.ibm.com/support/knowledgecenter/en/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.admin.doc/doc/knox_overview.html)
- [Apache HBase](https://hbase.apache.org)
- [Apache HBase REST API](http://hbase.apache.org/book.html#_rest)

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with Columnar database or store
- Able to read code written in a high level language such as [Java](https://en.wikipedia.org/wiki/Java_\(programming_language\))
- Familiar with the [Gradle](https://gradle.org/) build tool

## Example Requirements

Your have met the [pre-requisites](../../README.md#pre-requisites) and have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)

## Run the example

There are on example in this folder written [Java](https://en.wikipedia.org/wiki/Java_\(programming_language\)):

- [Example.java](./src/main/java/Example.java) - this example connects to HBASE REST api and get system version, cluster version and cluster status

To run the examples, in a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Example` (OS X / *nix)
      - `gradlew.bat Example` (Windows)
   - some output from running the command on my machine is shown below

```bash
$ cd examples/HBaseJava
$ ./gradlew Example
:compileJava UP-TO-DATE
:processResources
:classes
:Connect
System version : {"Server":"jetty/6.1.26-ibm","Jersey":"1.9","REST":"0.0.3","JVM":"Oracle Corporation 1.8.0_77-25.77-b03","OS":"Linux 2.6.32-642.1.1.el6.x86_64 amd64"}
Cluster version : 1.2.0-IBM-7
Status : {"regions":4,"requests":0,"averageLoad":1.3333333333333333,"LiveNodes":[{"name":"https://bicloud-186-mastermanager.bicloud.com:8443/gateway/default/hbase-region?_=AAAACAAAABAAAAAwVc1vIRCqbPD895dJF7QGz90dHjToCimwvvQ4eybZtuxNk5dx_ltkB01Awod2_yNPpgWrtdl_xYZE9nC9JC8f1EDh4XlOxXKA","startCode":1469409015544,"requests":0,"heapSizeMB":74,"maxHeapSizeMB":1004,"Region":[{"name":"aGJhc2U6bmFtZXNwYWNlLCwxNDY5Mzk4NTcwMzg5LjM5NWY0YzEyMjBhZGY0NDA3ODZlZWI2NGQyOGU5YzQ3Lg==","stores":1,"storefiles":1,"storefileSizeMB":0,"memstoreSizeMB":0,"storefileIndexSizeMB":0,"readRequestsCount":4,"writeRequestsCount":0,"rootIndexSizeKB":0,"totalStaticIndexSizeKB":0,"totalStaticBloomSizeKB":0,"totalCompactingKVs":0,"currentCompactedKVs":0}]},{"name":"https://bicloud-186-mastermanager.bicloud.com:8443/gateway/default/hbase-region?_=AAAACAAAABAAAAAwVc1vIRCqbPD895dJF7QGz90dHjToCimwvvQ4eybZtuxNk5dx_ltkB5SW0cNqt9Rv3v7Jf5WTTwaDOS4uMBlDJayq8OpDRyM5","startCode":1469409008640,"requests":0,"heapSizeMB":54,"maxHeapSizeMB":1004,"Region":[{"name":"YW1iYXJpc21va2V0ZXN0LCwxNDY5NDE4Nzg5ODQ0LmFkOTVhZDUwZWM0ZDVkYzJkZjgxZDE4MGY3YWQzYTViLg==","stores":1,"storefiles":1,"storefileSizeMB":0,"memstoreSizeMB":0,"storefileIndexSizeMB":0,"readRequestsCount":2,"writeRequestsCount":1,"rootIndexSizeKB":0,"totalStaticIndexSizeKB":0,"totalStaticBloomSizeKB":0,"totalCompactingKVs":0,"currentCompactedKVs":0},{"name":"aGJhc2U6bWV0YSwsMQ==","stores":1,"storefiles":1,"storefileSizeMB":0,"memstoreSizeMB":0,"storefileIndexSizeMB":0,"readRequestsCount":7599,"writeRequestsCount":29,"rootIndexSizeKB":0,"totalStaticIndexSizeKB":0,"totalStaticBloomSizeKB":0,"totalCompactingKVs":24,"currentCompactedKVs":24}]},{"name":"https://bicloud-186-mastermanager.bicloud.com:8443/gateway/default/hbase-region?_=AAAACAAAABAAAAAwVc1vIRCqbPD895dJF7QGz90dHjToCimwvvQ4eybZtuxNk5dx_ltkBzTDXDsccpnz1RrlZlGlEugAPBYDY6u2jtRlY-P9VI-a","startCode":1469409009629,"requests":0,"heapSizeMB":61,"maxHeapSizeMB":1004,"Region":[{"name":"aGJhc2U6YWNsLCwxNDY5Mzk4NTcxNjg1LjE3N2I2MTQ5MTI0MzMxOGM2ZGM5YjdmNTc3NzU0NDR                               kLg==","stores":1,"storefiles":2,"storefileSizeMB":0,"memstoreSizeMB":0,"storefileIndexSizeMB":0,"readRequestsCount":18,"writeRequestsCount":17,"rootIndexSizeKB":0,"totalStaticIndexSizeKB":0,"totalStaticBloomSizeKB":0,"totalCompactingKVs":4,"currentCompactedKVs":4}]}],"DeadNodes":[]}
:Example

BUILD SUCCESSFUL

Total time: 3.589 secs
```

The output above shows:

- the steps performed by gradle (command names are prefixed by ':' such as :compileJava) 
- after `:Connect` you see test output for the Connect test
 
## Decomposition Instructions

The examples use a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.  The build.gradle for this example does the following:

- each example simply compiles and executed the groovy code
- as part of the groovy code, each test:
   - connects to HBASE REST API via knox shell
   - executes HBASE operation via knox shell

All code is well commented and it is suggested that you browse the build.gradle and *.groovy scripts to understand in more detail how they work.

