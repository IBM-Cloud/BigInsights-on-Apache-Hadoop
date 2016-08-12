## Overview

This example extends [OozieWorkflowSparkGroovy](../OozieWorkflowSparkGroovy) and shows you how you could deploy the Oozie code to Bluemix.


## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with compiling and running java applications
- Able to read code written in a high level language such as [Groovy](http://www.groovy-lang.org/)
- Familiar with the [Gradle](https://gradle.org/) build tool
- Familiar with [Spring Boot](http://projects.spring.io/spring-boot/)
- Familiar with Spark concepts
- Familiar with Oozie concepts
- Familiar with the [OozieWorkflowSparkGroovy Example](../OozieWorkflowSparkGroovy)

## Example Requirements

- You meet the [pre-requisites](../../README.md#pre-requisites) in the top level [README](../../README.md)
- You have performed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)
- You have a Bluemix account where the spring boot application can be deployed
- You have installed the bluemix [command line tools](https://console.ng.bluemix.net/docs/starters/install_cli.html)

## Run the example

This example consists of two parts separated into two directories:

- [hadoop_deploy](./hadoop_deploy) : compile, package and upload the spark code and oozie workflow file to webHDFS
- [bluemix_deploy](./bluemix_deploy) : compile, package and upload the spring boot oozie client application to Bluemix

### Hadoop Deploy

To run the example, open a command prompt window:

   - change into the hadoop_deploy directory and run:
      - `./gradlew Setup` (OS X / *nix)
      - `gradlew.bat Setup` (Windows)

### Bluemix Deploy

To run the example locally, open a command prompt window:

   - change into the bluemix_deploy directory and run:
      - `./gradlew RunLocally` (OS X / *nix)
      - `gradlew.bat RunLocally` (Windows)
   - follow the instructions in the output

To run the example on Bluemix, open a command prompt window:

   - change into the bluemix_deploy directory and run:
      - `./gradlew Deploy` (OS X / *nix)
      - `gradlew.bat Deploy` (Windows)
   - follow the instructions in the output
 
## Decomposition Instructions

The examples uses a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.

More info to come ...

All code is well commented and it is suggested that you browse the build.gradle and *.groovy scripts to understand in more detail how they work.


