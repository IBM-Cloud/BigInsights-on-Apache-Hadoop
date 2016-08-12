## Overview

BigInsights clusters can be accessed via [jdbc](https://en.wikipedia.org/wiki/Java_Database_Connectivity) using either [Hive](https://hive.apache.org/) or [BigSQL](http://www.ibm.com/support/knowledgecenter/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.analyze.doc/doc/bigsql_analyzingbigdata.html).  Hive is available on all BigInsight clusters as part of the [Open Data Platform](https://www.odpi.org/).  BigSQL is an optional extra for BigInsights clusters.

This example downloads a popular open source SQL client, [SquirrelSQL](http://squirrel-sql.sourceforge.net/) and configures it with connections to access your cluster over Hive and BigSQL (if BigSQL is available on your cluster).


See also:

- [Hive Reference](https://cwiki.apache.org/confluence/display/Hive/LanguageManual)
- [BigSQL Reference](http://www.ibm.com/support/knowledgecenter/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.db2biga.doc/doc/biga_intro.html)

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with SQL Concepts

## Example Requirements

- You have met the [pre-requisites](../../README.md#pre-requisites) in the top level [README](../../README.md)
- You have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)

Specificaly, ensure you have run from top level repo directory the following tasks:

- `./gradlew DownloadCertificate` - to get SSL certificate
- `./gradlew DownloadLibs` - to get BigSQL JDBC jar libraries (only required for BigSQL connectivity)

## Run the example

To run the example, open a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew RunSquirrel` (OS X / *nix)
      - `gradlew.bat RunSquirrel` (Windows)

The SquirrelSQL window should be opened for you where you can select Hive and BigSQL connections to your cluster. Note: you will only be provided with a BigSQL connection if your cluster has the BigSQL service running.

 
## Decomposition Instructions

The examples uses a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.  The build.gradle does the following:

- Configure a truststore for SSL connectivity to Hive/BigSQL
- Make the Hive/BigSQL jdbc jar files available for SquirrelSQL
- Download SquirrelSQL and unzip it
- Configure SquirrelSQL with your connection details
- Execute SquirrelSQL

All code is well commented and it is suggested that you browse the source code to understand in more detail what they do.

## Help

If you run into the following issue, see the [FAQ](../../FAQ.md)
```
> Failed to create MD5 hash for ...
```
