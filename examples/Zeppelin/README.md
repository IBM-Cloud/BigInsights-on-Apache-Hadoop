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


### Test Zeppelin with Pyspark

First run Zeppelin.  In the brower window, you should see an option for configuring interpreters by clicking on the drop down menu button at the top right of the window:

![Configure Interpreters](./ConfigureInterpreters.png)

- Select Interpreter
  - Search for 'spark'
      - Navigate to the 'livy' section
          - Click edit
          - Change 'livy.spark.master' to 'yarn-client'
          - Click Save
          - Click OK to save and restart the interpreter
      - Navigate to the 'spark' section
          - Click edit
          - Change 'master' to 'yarn-client'
          - Click Save
          - Click OK to save and restart the interpreter

Click on the Notebook dropdown menu from the main toolbar and click on the Pyspark Test notebook:

![Pyspark Test Notebook](./Pyspark_Test.png)

- Click on the 'Pyspark Test' notebook to open it
- Click on the run button in the notebook windows to execute the notebook code 
- Verify the row count output from spark equals the expected row count

You should also be able to see the above spark job running on the spark cluster.  While the notebook is running, you could ssh into the cluster and run the command `yarn application -list`.  




## Limitations

- current only the spark and bash interpreters are configured
- currently only basic zeppelin spark configuration is performed: see [zeppelin_install.sh](./zeppelin_install.sh) for details
