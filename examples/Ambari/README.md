## Overview

This example shows how to programmatically interact with Ambari on the BigInsights cluster.

BigInsights for Apache Hadoop clusters are using [Apache Ambari](https://ambari.apache.org). The Apache Ambari project is aimed at making Hadoop management simpler by developing software for provisioning, managing, and monitoring Apache Hadoop clusters. Ambari provides an intuitive, easy-to-use Hadoop management web UI backed by its RESTful APIs.

See also:

- [Ambari REST API](https://github.com/apache/ambari/blob/trunk/ambari-server/docs/api/v1/index.md) - Ambari provides RESTful api to interact, monitor and operate with your cluster

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with compiling and running java applications
- Able to read code written in a high level language such as [Groovy](http://www.groovy-lang.org/)
- Familiar with the [Gradle](https://gradle.org/) build tool

## Example Requirements

You have met the [pre-requisites](../../README.md#pre-requisites) and have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)

Also you might make sure the user defined in connection.properties has OPERATE privilege in order to run service check operations.

## Run the example

There are three examples in this folder written in the [Groovy Language](http://www.groovy-lang.org/) and using the HTTP Rest Groovy client library:

- [HdfsServiceCheck.groovy](./HdfsServiceCheck.groovy) - this example runs HDFS service check and checks request completes successfuly.
- [ListServices.groovy](./ListServices.groovy) - this example lists all the services installed as part of this cluster.
- [ShowMaster.groovy](./ShowMaster.groovy) - this example lists the host/s running for a few key component services like Hive, Big SQL, etc.

To run the examples, in a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Example` (OS X / *nix)
      - `gradlew.bat Example` (Windows)
   - some output from running the command on my machine is shown below 

```bash
$ cd examples/Ambari
$ ./gradlew Example
:compileJava UP-TO-DATE
:compileGroovy
:processResources UP-TO-DATE
:classes
:HdfsServiceCheck

Get cluster info: 200

Cluster name is: EHaasCluster

Call Ambari Service Check: 202

Request id is: 78
Request href is: https://bicloud-186-mastermanager.bicloud.com:9443/api/v1/clusters/EHaasCluster/requests/78

Polling up to 60s for job completion...
.......
Job status  is: COMPLETED
Job failed tasks is: 0
:ListServices

Ambari URL: https://bicloud-186-mastermanager.bicloud.com:9443/

Cluster name is: EHaasCluster

Services are: 
service 0: AMBARI_METRICS
service 1: BIGR
service 2: BIGSHEETS
service 3: BIGSQL
service 4: DATASERVERMANAGER
service 5: FLUME
service 6: HBASE
service 7: HDFS
service 8: HIVE
service 9: KERBEROS
service 10: KNOX
service 11: MAPREDUCE2
service 12: OOZIE
service 13: PIG
service 14: RSERV
service 15: SLIDER
service 16: SPARK
service 17: SQOOP
service 18: TEXTANALYTICS
service 19: WEBUIFRAMEWORK
service 20: YARN
service 21: ZOOKEEPER
:ShowMaster

BIGSQL_HEAD (BIGSQL) hostname is: [bicloud-186-master-3.bicloud.com]
KNOX_GATEWAY (KNOX) hostname is: [bicloud-186-mastermanager.bicloud.com]
HIVE_SERVER (HIVE) hostname is: [bicloud-186-master-3.bicloud.com]
BIGRCONNECTOR (BIGR) hostname is: [bicloud-186-master-3.bicloud.com]
:Example

BUILD SUCCESSFUL

Total time: 26.439 secs
```

The output above shows service check was submitted successfully (`202`) and checks for the request `id : 78` to complete with no failures. Second example will list all services installed on the cluster. The last example, shows the hostname for BIGSQL_HEAD, KNOX_GATEWAY, HIVE_SERVER and BIGRCONNECTOR.
 
## Decomposition Instructions

The examples uses a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.  The build.gradle for this example does the following:

- compile the groovy script so that it is executable by Java
- read the connection details in your connection.properties file
- run the compiled groovy script as a Java application, making the connection details available as environment variables

All code is well commented and it is suggested that you browse the build.gradle and *.groovy scripts to understand in more detail how they work.

