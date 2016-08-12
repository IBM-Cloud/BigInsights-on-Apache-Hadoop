## Overview

The Knox shell allows connects to your cluster and allows you to run commands against the cluster using a groovy API.

BigInsights for Apache Hadoop clusters are secured using [Apache Knox](https://knox.apache.org/).  Apache Knox is a REST API Gateway for interacting with Apache Hadoop clusters.  As such, the HDFS service is not available to external clients of the BigInsights cluster, instead external clients interact with HDFS through the [WebHDFS REST API](http://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-hdfs/WebHDFS.html).

The Apache Knox project provides a [Java client library](https://cwiki.apache.org/confluence/display/KNOX/Client+Usage) and this can be used for interacting with the REST API.  Using a library is usally more productive because the library provides a higher level of abstraction than when working directly with the REST API.

See also:

- [BigInsights Apache Knox documentation](https://www.ibm.com/support/knowledgecenter/en/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.admin.doc/doc/knox_overview.html)
- [Knox User Guide](https://knox.apache.org/books/knox-0-6-0/user-guide.html#Service+Details) for more knox commands.

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with compiling and running java applications
- Able to read code written in a high level language such as [Groovy](http://www.groovy-lang.org/)
- Familiar with the [Gradle](https://gradle.org/) build tool

## Example Requirements

You have met the [pre-requisites](../../README.md#pre-requisites) and have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)

## Run the example

Run the knox shell from the current folder with:

`./gradlew -q --no-daemon shell`

The shell logs in to the BigInsights cluster and saves the session in the variable `session`.

```bash
$ cd examples/KnoxShell
$ ./gradlew -q --no-daemon shell
This is a gradle Application Shell.
You can import your application classes and act on them.
import org.apache.hadoop.gateway.shell.Hadoop;import org.apache.hadoop.gateway.shell.hdfs.Hdfs;import org.apache.hadoop.gateway.shell.job.Job;import org.apache.hadoop.gateway.shell.workflow.Workflow;import org.apache.hadoop.gateway.shell.yarn.Yarn;import groovy.json.JsonSlurper;import java.util.concurrent.TimeUnit;
===> [import org.apache.hadoop.gateway.shell.Hadoop;import org.apache.hadoop.gateway.shell.hdfs.Hdfs;import org.apache.hadoop.gateway.shell.job.Job;import org.apache.hadoop.gateway.shell.workflow.Workflow;import org.apache.hadoop.gateway.shell.yarn.Yarn;import groovy.json.JsonSlurper;import java.util.concurrent.TimeUnit;]
session = Hadoop.login( "https://bicloud-186-mastermanager.bicloud.com:8443/gateway/default", "biadmin", "***MASKED***" );
===> org.apache.hadoop.gateway.shell.Hadoop@1a245833
slurper = new JsonSlurper();
===> groovy.json.JsonSlurper@480d3575
Groovy Shell (1.8.3, JVM: 1.8.0_91)
Type 'help' or '\h' for help.
-------------------------------------------------------------------------------
groovy:000>
```

You can then issue following command which lists all files and folders on the cluster in the '/' folder:

- `json = Hdfs.ls(session).dir('/').now().string` - get content of / via Knox WebHDFS.
- `files = slurper.parseText(json).FileStatuses.FileStatus.pathSuffix` - get FileStatus.pathSuffix from json object to return directory and file names

```
groovy:000> json = Hdfs.ls(session).dir('/').now().string
log4j:WARN No appenders could be found for logger (org.apache.http.impl.conn.PoolingClientConnectionManager).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
===> {"FileStatuses":{"FileStatus":[{"accessTime":0,"blockSize":0,"childrenNum":4,"fileId":16406,"group":"hadoop","length":0,"modificationTime":1469477907668,"owner":"yarn","pathSuffix":"app-logs","permission":"777","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":2,"fileId":16422,"group":"bihdfs","length":0,"modificationTime":1469398595137,"owner":"hdfs","pathSuffix":"apps","permission":"755","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":4,"fileId":16415,"group":"bihdfs","length":0,"modificationTime":1469406333988,"owner":"hdfs","pathSuffix":"biginsights","permission":"775","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":1,"fileId":16398,"group":"bihdfs","length":0,"modificationTime":1469398504244,"owner":"hdfs","pathSuffix":"ibmpacks","permission":"755","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":1,"fileId":16389,"group":"bihdfs","length":0,"modificationTime":1469398494575,"owner":"hdfs","pathSuffix":"iop","permission":"755","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":1,"fileId":16410,"group":"bihdfs","length":0,"modificationTime":1469398516791,"owner":"mapred","pathSuffix":"mapred","permission":"755","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":2,"fileId":16412,"group":"hadoop","length":0,"modificationTime":1469398518783,"owner":"mapred","pathSuffix":"mr-history","permission":"777","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":0,"encBit":true,"fileId":17978,"group":"bihdfs","length":0,"modificationTime":1469405805845,"owner":"hdfs","pathSuffix":"secureDir","permission":"755","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":3,"fileId":27506,"group":"bihdfs","length":0,"modificationTime":1469450090068,"owner":"biadmin","pathSuffix":"test","permission":"755","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":1469450420898,"blockSize":134217728,"childrenNum":0,"fileId":28249,"group":"bihdfs","length":23,"modificationTime":1469450420924,"owner":"biadmin","pathSuffix":"test_exp.csv","permission":"644","replication":3,"storagePolicy":0,"type":"FILE"},{"accessTime":0,"blockSize":0,"childrenNum":14,"fileId":16388,"group":"bihdfs","length":0,"modificationTime":1470156600643,"owner":"hdfs","pathSuffix":"tmp","permission":"777","replication":0,"storagePolicy":0,"type":"DIRECTORY"},{"accessTime":0,"blockSize":0,"childrenNum":12,"fileId":16386,"group":"bihdfs","length":0,"modificationTime":1469419175158,"owner":"hdfs","pathSuffix":"user","permission":"755","replication":0,"storagePolicy":0,"type":"DIRECTORY"}]}}
groovy:000> files = slurper.parseText(json).FileStatuses.FileStatus.pathSuffix
===> [app-logs, apps, biginsights, ibmpacks, iop, mapred, mr-history, secureDir, test, test_exp.csv, tmp, user]
groovy:000> 

```
The output above shows the list of files and directories on *my cluster*.
 
## Decomposition Instructions

The examples uses a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.  The build.gradle for this example does the following:

- Start knox shell and instanciate the session against your cluster
- Provide shell prompt for further command to be entered by user. Type `exit` to terminate the session.

All code is well commented and it is suggested that you browse the build.gradle and *.groovy scripts to understand in more detail how they work.

