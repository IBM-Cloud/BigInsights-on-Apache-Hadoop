## Overview

This example shows how to submit a SystemML Spark program to a BigInsights cluster. It runs systemML scripts on SPARK 2.0 against your YARN cluster. 

See also:

- [Apache SystemML](https://apache.github.io/incubator-systemml/)

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Able to read code written in a high level language such as [Groovy](http://www.groovy-lang.org/)
- Familiar with the [Gradle](https://gradle.org/) build tool
- Familiar with Spark concepts
- Familiar with Machine Learning concepts

## Example Requirements

- **You have BigInsights 4.3 Tech Preview with Spark 2.0 and SystemML.**
- You meet the [pre-requisites](../../README.md#pre-requisites) in the top level [README](../../README.md)
- You have performed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)

## Run the example

This example consists:

- [LinearRegression.sh](./LinearRegression.sh) - shell program to sumbit DML scripts to perform Linear Regression
- [build.gradle](./build.gradle) - Gradle script to copy program to the cluster and execute against the cluster

To run the example, open a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew LinearRegression` (OS X / *nix)
      - `gradlew.bat LinearRegression` (Windows)
   - some output from running the command on my machine is shown below 

```bash
BigInsights-on-Apache-Hadoop $ cd examples/SparkSystemML
BigInsights-on-Apache-Hadoop/examples/SparkSystemML $ ./gradlew LinearRegression
:LinearRegression
Strict host key checking is off. It may be vulnerable to man-in-the-middle attacks.
bicluster#0|bash: kinit: command not found
Failed command bicluster#0 with status 127: kinit -k -t biguser.keytab biguser@IBM.COM
kinit not found so not renewing kerberos ticket - maybe this is a Basic cluster?
bicluster#3|
bicluster#3|Submit genLinearRegressionData
bicluster#3|16/10/04 18:01:05 WARN NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
bicluster#3|16/10/04 18:01:07 WARN DomainSocketFactory: The short-circuit local reads feature cannot be used because libhadoop cannot be loaded.
bicluster#3|16/10/04 18:01:07 INFO RMProxy: Connecting to ResourceManager at bi-hadoop-prod-4135.bi.services.us-south.bluemix.net/172.16.109.1:8050
bicluster#3|16/10/04 18:01:07 INFO Client: Requesting a new application from cluster with 4 NodeManagers
bicluster#3|16/10/04 18:01:07 INFO Client: Verifying our application has not requested more than the maximum memory capability of the cluster (12288 MB per container)
bicluster#3|16/10/04 18:01:07 INFO Client: Will allocate AM container, with 1408 MB memory including 384 MB overhead
bicluster#3|16/10/04 18:01:07 INFO Client: Setting up container launch context for our AM
bicluster#3|16/10/04 18:01:07 INFO Client: Setting up the launch environment for our AM container
bicluster#3|16/10/04 18:01:07 INFO Client: Preparing resources for our AM container
bicluster#3|16/10/04 18:01:07 INFO Client: Source and destination file systems are the same. Not copying hdfs:/iop/apps/4.3.0.0/spark/jars/spark-assembly.tgz
bicluster#3|16/10/04 18:01:07 INFO Client: Uploading resource file:/usr/iop/current/systemml-client/lib/systemml.jar -> hdfs://bi-hadoop-prod-4135.bi.services.us-south.bluemix.net:8020/user/biguser/.sparkStaging/application_1475598104224_0011/systemml.jar
bicluster#3|16/10/04 18:01:08 INFO Client: Uploading resource file:/tmp/spark-02bb32e1-9e37-4334-839f-f11c9b480fd4/__spark_conf__7474878173973514849.zip -> hdfs://bi-hadoop-prod-4135.bi.services.us-south.bluemix.net:8020/user/biguser/.sparkStaging/application_1475598104224_0011/__spark_conf__.zip
bicluster#3|16/10/04 18:01:08 WARN Client: spark.yarn.am.extraJavaOptions will not take effect in cluster mode
bicluster#3|16/10/04 18:01:08 INFO SecurityManager: Changing view acls to: biguser
bicluster#3|16/10/04 18:01:08 INFO SecurityManager: Changing modify acls to: biguser
bicluster#3|16/10/04 18:01:08 INFO SecurityManager: Changing view acls groups to: 
bicluster#3|16/10/04 18:01:08 INFO SecurityManager: Changing modify acls groups to: 
bicluster#3|16/10/04 18:01:08 INFO SecurityManager: SecurityManager: authentication disabled; ui acls disabled; users  with view permissions: Set(biguser); groups with view permissions: Set(); users  with modify permissions: Set(biguser); groups with modify permissions: Set()
bicluster#3|16/10/04 18:01:08 INFO Client: Submitting application application_1475598104224_0011 to ResourceManager
bicluster#3|16/10/04 18:01:08 INFO YarnClientImpl: Submitted application application_1475598104224_0011
bicluster#3|16/10/04 18:01:09 INFO Client: Application report for application_1475598104224_0011 (state: ACCEPTED)
bicluster#3|16/10/04 18:01:09 INFO Client: 
bicluster#3|	 client token: N/A
bicluster#3|	 diagnostics: N/A
bicluster#3|	 ApplicationMaster host: N/A
bicluster#3|	 ApplicationMaster RPC port: -1
bicluster#3|	 queue: default
bicluster#3|	 start time: 1475604068145
bicluster#3|	 final status: UNDEFINED
bicluster#3|	 tracking URL: http://bi-hadoop-prod-4135.bi.services.us-south.bluemix.net:8088/proxy/application_1475598104224_0011/
bicluster#3|	 user: biguser
bicluster#3|16/10/04 18:01:10 INFO Client: Application report for application_1475598104224_0011 (state: ACCEPTED)
bicluster#3|16/10/04 18:01:11 INFO Client: Application report for application_1475598104224_0011 (state: ACCEPTED)
bicluster#3|16/10/04 18:01:12 INFO Client: Application report for application_1475598104224_0011 (state: ACCEPTED)
bicluster#3|16/10/04 18:01:13 INFO Client: Application report for application_1475598104224_0011 (state: RUNNING)
bicluster#3|16/10/04 18:01:13 INFO Client: 
bicluster#3|	 client token: N/A
bicluster#3|	 diagnostics: N/A
bicluster#3|	 ApplicationMaster host: 172.16.109.2
bicluster#3|	 ApplicationMaster RPC port: 0
bicluster#3|	 queue: default
bicluster#3|	 start time: 1475604068145
bicluster#3|	 final status: UNDEFINED
bicluster#3|	 tracking URL: http://bi-hadoop-prod-4135.bi.services.us-south.bluemix.net:8088/proxy/application_1475598104224_0011/
bicluster#3|	 user: biguser
bicluster#3|16/10/04 18:01:14 INFO Client: Application report for application_1475598104224_0011 (state: RUNNING)
bicluster#3|16/10/04 18:01:15 INFO Client: Application report for application_1475598104224_0011 (state: RUNNING)
bicluster#3|16/10/04 18:01:16 INFO Client: Application report for application_1475598104224_0011 (state: RUNNING)
bicluster#3|16/10/04 18:01:17 INFO Client: Application report for application_1475598104224_0011 (state: RUNNING)
bicluster#3|16/10/04 18:01:18 INFO Client: Application report for application_1475598104224_0011 (state: RUNNING)
bicluster#3|16/10/04 18:01:19 INFO Client: Application report for application_1475598104224_0011 (state: RUNNING)
bicluster#3|16/10/04 18:01:20 INFO Client: Application report for application_1475598104224_0011 (state: RUNNING)
bicluster#3|16/10/04 18:01:21 INFO Client: Application report for application_1475598104224_0011 (state: FINISHED)
bicluster#3|16/10/04 18:01:21 INFO Client: 
bicluster#3|	 client token: N/A
bicluster#3|	 diagnostics: N/A
bicluster#3|	 ApplicationMaster host: 172.16.109.2
bicluster#3|	 ApplicationMaster RPC port: 0
bicluster#3|	 queue: default
bicluster#3|	 start time: 1475604068145
bicluster#3|	 final status: SUCCEEDED
bicluster#3|	 tracking URL: http://bi-hadoop-prod-4135.bi.services.us-south.bluemix.net:8088/proxy/application_1475598104224_0011/
bicluster#3|	 user: biguser
bicluster#3|16/10/04 18:01:21 INFO ShutdownHookManager: Shutdown hook called
bicluster#3|16/10/04 18:01:21 INFO ShutdownHookManager: Deleting directory /tmp/spark-02bb32e1-9e37-4334-839f-f11c9b480fd4
bicluster#3|
bicluster#3|Submit Sample
bicluster#3|16/10/04 18:01:22 WARN NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
bicluster#3|16/10/04 18:01:24 WARN DomainSocketFactory: The short-circuit local reads feature cannot be used because libhadoop cannot be loaded.
bicluster#3|16/10/04 18:01:24 INFO RMProxy: Connecting to ResourceManager at bi-hadoop-prod-4135.bi.services.us-south.bluemix.net/172.16.109.1:8050
...
```

The output above shows:

- the steps performed by gradle (command names are prefixed by ':' such as :compileJava) 
- the steps performed by the LinearRegression.sh script
 
## Decomposition Instructions

The examples uses a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.  The build.gradle does the following:

- copy [LinearRegression](./LinearRegression.sh) file to cluster.
- execute [LinearRegression](./LinearRegression.sh) against YARN cluster.

The [LinearRegression.sh](./LinearRegression.sh) executes the following steps:

- Generate random data for linear regression. A matrix is generated consisting of a data matrix with a label column appended to it.
- Randomly sample data (without replacement) into disjoint subsets.
- Split X into new X and Y
- Perform linear regression algorithm usings a direct solver for (X^T X + lambda) beta = X^T y
- Applies the estimate parameters of a glm-type regression to the new (test) dataset

All code is well commented and it is suggested that you browse the build.gradle and *.groovy scripts to understand in more detail how they work.

