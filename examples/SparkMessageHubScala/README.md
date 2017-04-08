## Overview

This example shows how to execute a spark streaming example on the BigInsights cluster that consumes messages from IBM MessageHub (Kafka).  Note that the spark streaming job runs on a single node and not as a yarn application.

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Able to read code written in a high level language such as [Groovy](http://www.groovy-lang.org/)
- Familiar with the [Gradle](https://gradle.org/) build tool
- Familiar with Spark concepts such as spark streaming
- Familiar with MessageHub (Kafka)

## Example Requirements

- You meet the [pre-requisites](../../README.md#pre-requisites) in the top level [README](../../README.md)
- You have performed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)
- You are using BigInsights 4.2 with Spark 1.6 (this solution has not been tested with BigInsights 4.3 and Spark 2.0)
- You have setup an instance of MessageHub
- If you want to use the python script for sending messages to kafka, you must have:
  - Python 3.x installed
  - kafka-python library installed (`pip install kafka-python`)

## Run the example

To run the examples, in a command prompt window:

   - copy vcap.json_template to vcap.json
   - edit vcap.json with your messagehub connection details
   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Example` (OS X / *nix)
      - `gradlew.bat Example` (Windows)
   - While the streaming example is running, open a new terminal window and execute the python script, e.g.
      - `python send_message.py 12345`
   - Ssh into your cluster
   - Run `hdfs dfs -ls /user/<<your_username>>/` to see the output folders
   - Run `hdfs dfs -cat /user/<<your_username>>/test-<<output_folder_uuid>>/*`

You should see something like this:

```
-bash-4.1$ hdfs dfs -cat /user/snowch/test-0458b164-007e-465a-89fe-13aa8f40f35d/*
(null,12345)
```

When you have finished running, CTRL-C the window running `./gradlew Example`, then run `./gradlew KillAll` to kill the streaming example on the cluster.

## Decomposition Instructions

The [./build.gradle](./build.gradle) script runs the example.  Build.gradle:

- compiles [MessageHubConsumer.scala](./src/main/scala/biginsights/examples/MessageHubConsumer.scala)
- compiles helper classes in the package [com.ibm.cds.spark.samples.*](src/main/scala/com/ibm/cds/spark/samples/)
- package MessageHubConsumer classes and dependencies into a jar file `build/libs/SparkMessageHubScala-all.jar`

It then uses a ssh plugin to:

- copy `build/libs/SparkMessageHubScala-all.jar` to the BigInsights cluster
- from the ssh session, execute the MessageHubConsumer spark streaming job

It is recommended that you spend time investigating the classes in the package `com.ibm.cds.spark.samples.*`.  You will probably need to make changes in these classes, especially in:

- [MessageHubConfig#initConfigKeys() method](src/main/scala/com/ibm/cds/spark/samples/config/MessageHubConfig.scala)
