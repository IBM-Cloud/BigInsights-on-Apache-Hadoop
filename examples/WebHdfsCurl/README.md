## Overview

This example shows how to programmatically interact with HDFS on the BigInsights cluster.  Some examples of programmatic interactions may be:

- An [ETL](https://en.wikipedia.org/wiki/Extract,_transform,_load) tool job that needs to exchange data with BigInsights
- A [Microservice](https://en.wikipedia.org/wiki/Microservices) that needs to access data in BigInsights

BigInsights for Apache Hadoop clusters are secured using [Apache Knox](https://knox.apache.org/).  Apache Knox is a REST API Gateway for interacting with Apache Hadoop clusters.  As such, the HDFS service is not available to external clients of the BigInsights cluster, instead external clients interact with HDFS through the [WebHDFS REST API](http://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-hdfs/WebHDFS.html).

This example uses [cURL](https://curl.haxx.se/) to interact with the REST API. This is useful for developers to understand the REST API so they can emulate the REST calls using their programming language features.

See also:

- [WebHdfsGroovy Example](../WebHdfsGroovy/README.md) - similar to this example, but uses a Java based library for working with the REST API
- [BigInsights Apache Knox documentation](https://www.ibm.com/support/knowledgecenter/en/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.admin.doc/doc/knox_overview.html)


## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with the [Gradle](https://gradle.org/) build tool
- Familiar with  [cURL](https://curl.haxx.se/)

## Example Requirements

- You have met the [pre-requisites](../../README.md#pre-requisites) and have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)
- You must have [bash](https://www.gnu.org/software/bash/) and [cURL](https://curl.haxx.se/) applications installed and in the PATH.

## Run the example

There are three examples in this folder are:

- [Ls.sh](./Ls.sh) - this example lists files and folders in the root HDFS folder
- [Mkdir.sh](./Mkdir.sh) - this example creates a folder in HDFS
- [Put.sh](./Put.sh) - this example uploads a file to HDFS

To run the example [Ls.sh](./Ls.sh), in a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Ls` (OS X / *nix)
      - `gradlew.bat Ls` (Windows)
   - some output from running the command on my machine is shown below 

```bash
biginsight-bluemix-docs $ cd examples/WebHdfsCurl
biginsight-bluemix-docs/examples/WebHdfsCurl $ ./gradlew Ls
:Ls
*   Trying 169.55.88.5...
* Connected to bi-hadoop-prod-4005.bi.services.us-south.bluemix.net (169.55.88.5) port 8443 (#0)
* TLS 1.2 connection using TLS_DHE_RSA_WITH_AES_256_GCM_SHA384
* Server certificate: *.bi.services.us-south.bluemix.net
* Server certificate: DigiCert SHA2 Secure Server CA
* Server certificate: DigiCert Global Root CA
* Server auth using Basic with user 'snowch'
> GET /gateway/default/webhdfs/v1//?op=LISTSTATUS HTTP/1.1
> Host: bi-hadoop-prod-4005.bi.services.us-south.bluemix.net:8443
> Authorization: Basic ************
> User-Agent: curl/7.43.0
> Accept: */*
>
< HTTP/1.1 200 OK
< Set-Cookie: JSESSIONID=1o6pzj8lflxk7u3wiguh1fwpa;Path=/gateway/default;Secure;HttpOnly
< Expires: Thu, 01 Jan 1970 00:00:00 GMT
< Cache-Control: no-cache
< Expires: Sat, 23 Jul 2016 09:07:36 GMT
< Date: Sat, 23 Jul 2016 09:07:36 GMT
< Pragma: no-cache
< Expires: Sat, 23 Jul 2016 09:07:36 GMT
< Date: Sat, 23 Jul 2016 09:07:36 GMT
< Pragma: no-cache
< Server: Jetty(6.1.26-ibm)
< Content-Type: application/json
< Transfer-Encoding: chunked
<
{ [5 bytes data]
* Connection #0 to host bi-hadoop-prod-4005.bi.services.us-south.bluemix.net left intact
 {"FileStatuses":{"FileStatus":[{"accessTime":0,"blockSize":0,"childrenNum":9,"fileId":16935,"group":"hdfs","length":0,"modificationTime":1469253686371,"owner":"ams","pathSuffix":"amshbase","permission":"775","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":1,"fileId":16394,"group":"hadoop","length":0,"modificationTime":1469196845692,"owner":"yarn","pathSuffix":"app-logs","permission":"777","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":2,"fileId":16389,"group":"hdfs","length":0,"modificationTime":1467263190881,"owner":"hdfs","pathSuffix":"apps","permission":"755","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":1,"fileId":16879,"group":"hdfs","length":0,"modificationTime":1467263172854,"owner":"hdfs","pathSuffix":"iop","permission":"755","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":1,"fileId":16397,"group":"hdfs","length":0,"modificationTime":1467263051976,"owner":"mapred","pathSuffix":"mapred","permission":"755","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":2,"fileId":16399,"group":"hadoop","length":0,"modificationTime":1467263052277,"owner":"mapred","pathSuffix":"mr-history","permission":"777","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":0,"encBit":true,"fileId":17153,"group":"biusers","length":0,"modificationTime":1469117673564,"owner":"snowch","pathSuffix":"securedir"       ,"permission":"700","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":2,"fileId":16386,"group":"hdfs","length":0,"modificationTime":1469117617021,"owner":"hdfs","pathSuffix":"tmp","permission":"777","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":7,"fileId":16387,"group":"hdfs","length":0,"modificationTime":1469117660348,"owner":"hdfs","pathSuffix":"user","permission":"755","replication":0,"storagePolicy":0,"type":"DIRECTORY"}]}}

>> Ls test was successful.


BUILD SUCCESSFUL

Total time: 2.297 secs
```
The output above shows the output on *my cluster*.  You may have different files and directories on *your cluster* so your output may be different.
 
**NOTE:** 
- Replace `Ls` with `Mkdir` to run the example to create a directory in HDFS, or `Put` to upload a file to HDFS.
- See the WebHDFS documentation for a `Get` example - [link](http://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-hdfs/WebHDFS.html#Open_and_Read_a_File)

## Decomposition Instructions

The examples uses a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.  The build.gradle for this example does the following:

- read the connection details in your connection.properties file
- execute the bash shell script,  making the connection details available to the script as environment variables

All code is well commented and it is suggested that you browse the build.gradle and *.sh scripts to understand in more detail how they work.

