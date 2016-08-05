## Overview

This example installs, configures and runs [Zeppelin](https://zeppelin.apache.org) on a BigInsights cluster without requiring root privileges.  Two reasons for running Zeppelin may be:

- to provide data scientists with notebook functionality
- to provide example code in the form of notebooks for services such as hbase


## User experience

Users will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts

## Example Requirements

- You meet the [pre-requisites](../../README.md#pre-requisites) in the top level [README](../../README.md)
- You have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)
- You need to ensure you have the appropriate services running on your cluster for the interpreters you wish to use (e.g. Spark)

## Run the example

### Install Zeppelin

To install Zeppelin, in a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Install` (OS X / *nix)
      - `gradlew.bat Install` (Windows)
   - some output from running the command on my machine is shown below 

```bash
biginsight-bluemix-docs $ cd examples/Zeppelin
biginsight-bluemix-docs/examples/Zeppelin $ ./gradlew Install
```

### Uninstall Zeppelin

To uninstall Zeppelin, in a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Uninstall` (OS X / *nix)
      - `gradlew.bat Uninstall` (Windows)
   - some output from running the command on my machine is shown below 

```bash
biginsight-bluemix-docs $ cd examples/Zeppelin
biginsight-bluemix-docs/examples/Zeppelin $ ./gradlew Uninstall
```

### Run Zeppelin

First install Zeppelin.  Then to run Zeppelin, in a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Run` (OS X / *nix)
      - `gradlew.bat Run` (Windows)
   - some output from running the command on my machine is shown below 

```bash
biginsight-bluemix-docs $ cd examples/Zeppelin
biginsight-bluemix-docs/examples/Zeppelin $ ./gradlew Run
:Run
Strict host key checking is off. It may be vulnerable to man-in-the-middle attacks.
bicluster#0|Zeppelin is running                            [  OK  ]
bicluster#0|
> Building 0% > :Run

SSH forwarding localhost:51174 -> BigInsights zeppelin
Access zeppelin at: http://localhost:51174.
Press ENTER to quit.
```

Follow the instructions output by the Run command.  

### Zeppelin daemon: stop, start, status

You can check the status of the Zeppelin daemon by running `./gradlew -q status`

```bash
$ ./gradlew -q status
bicluster#1|Zeppelin is running                            [  OK  ]
bicluster#1|
```

You can stop the Zeppelin daemon with `./gradlew -q stop`

```bash
$ ./gradlew -q stop
bicluster#1|Zeppelin stop                                  [  OK  ]
```

and start the Zeppelin daemon with `./gradlew -q start`

```bash
$ ./gradlew -q start
bicluster#1|Zeppelin start                                 [  OK  ]
bicluster#1|
```


### Test Zeppelin with Pyspark

First run Zeppelin, Then click on the Notebook dropdown menu from the main toolbar and click on the Pyspark Test notebook:

![Pyspark Test Notebook](./docs/Pyspark_Test.png)

- Click on the 'Pyspark Test' notebook to open it
- Click on the run button in the notebook windows to execute the notebook code 
- Verify the row count output from spark equals the expected row count

You should also be able to see the above spark job running on the spark cluster.  While the notebook is running, you could ssh into the cluster and run the command `yarn application -list`.  

**NOTE**: The spark yarn application should remain in RUNNING state until it is explicilty closed or Zeppelin is restarted. 

### Test Zeppelin with SparkR

Follow the instructions for 'Test Zeppelin with Pyspark', then create a new cell with the contents:

```
%spark.r 

peopleDF <- read.json(sqlContext, file.path("file://", Sys.getenv("SPARK_HOME"), "examples/src/main/resources/people.json"))
printSchema(peopleDF)
SparkR::head(peopleDF)
```

and the output:

```
root
 |– age: long (nullable = true)
 |– name: string (nullable = true)
  age    name
1  NA Michael
2  30    Andy
3  19  Justin
```

## Decomposition Instructions

The examples uses a gradle build file [build.gradle](./build.gradle) when you run ./gradlew or gradle.bat. The build.gradle for this 'Run' target does the following:

- verifies that zeppelin has been installed on the cluster
- verifies if zeppelin is running on the cluster, if not zeppelin is not running it is started 
- sets up [port forwarding](https://en.wikipedia.org/wiki/Port_forwarding) on the local machine, see below

![SSH Port Forwarding](./docs/ssh_port_forwarding.png)

Port forwarding works as follows:

- the gradle ssh client binds to a random port on the local machine
- any traffic to this port on the local machine gets forwarded over ssh to the remote zeppelin port
- gradle prints the URL with the random local port on the local machine

Therefore, when you connect to this local URL you are really connecting to the zeppelin service on the cluster.  

## Limitations

- current only the spark and bash interpreters are configured
- currently only basic zeppelin spark configuration is performed: see [zeppelin_install.sh](./zeppelin_install.sh) for details.  If you would like Hbase support consider voting for this upstream [issue](https://issues.apache.org/jira/browse/ZEPPELIN-1252)
