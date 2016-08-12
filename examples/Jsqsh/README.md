## Overview

You can use IBM® Big SQL from any client tool that uses a [JDBC](https://en.wikipedia.org/wiki/Java_Database_Connectivity) or ODBC driver. One of those clients is the Java SQL Shell (JSqsh) command interface that is an open source technology that BigInsights® provides. When you run the jsqsh command from the command line, you open a command shell. You can type specific Big SQL commands or statements into this shell and view output from Big SQL queries.

Big SQL is a massively parallel processing ([MPP](https://en.wikipedia.org/wiki/Massively_parallel_\(computing\))) SQL engine that deploys directly on the physical Hadoop Distributed File System (HDFS) cluster. This SQL engine pushes processing down to the same nodes that hold the data. Big SQL uses a low-latency parallel execution infrastructure that accesses Hadoop data natively for reading and writing.

See also:

- [BigInsights Big SQL documentation](https://www.ibm.com/support/knowledgecenter/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.product.doc/doc/bi_sql_access.html)
- [Jsqsh documentation](http://www.ibm.com/support/knowledgecenter/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.analyze.doc/doc/bsql_jsqsh.html)
- [Jsqsh github repo](https://github.com/scgray/jsqsh)

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with Database concepts and SQL language
- Able to read code written in a high level language such as [Groovy](http://www.groovy-lang.org/)
- Familiar with the [Gradle](https://gradle.org/) build tool

## Example Requirements

- Your cluster has Big SQL service installed
- You have met the [pre-requisites](../../README.md#pre-requisites) in the top level [README](../../README.md)
- You have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)

Specificaly, ensure you have run from top level repo directory the following tasks:

- `./gradlew DownloadCertificate` - to get SSL certificate
- `./gradlew DownloadLibs` - to get JDBC jar libraries

## Run the setup

To setup the jsqsh tool and configuration, open a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew SetupJsqsh` (OS X / *nix)
      - `gradlew.bat SetupJsqsh` (Windows)
   - some output from running the command on my machine is shown below 

```bash
$ cd examples/Jsqsh
$ ./gradlew SetupJsqsh
:CreateConfig UP-TO-DATE
:CreateKeyStore UP-TO-DATE
:DownloadJsqsh UP-TO-DATE
:SetupJsqsh
*******************************************************************
Run jsqsh session with:

  ./jsqsh-2.2/bin/jsqsh biginsights -C config [other jsqsh options]

Run script:

  ./jsqsh-2.2/bin/jsqsh biginsights -C config -e -i simple_select.sql
*******************************************************************

BUILD SUCCESSFUL

Total time: 8.302 secs
```

You can then open shell connection or run script as stated above, for example:

```
$ ./jsqsh-2.2/bin/jsqsh biginsights -C config -e -i simple_select.sql

select * from (values (1));
+---+
| 1 |
+---+
| 1 |
+---+
1 row in results(first row: 0.259s; total: 0.515s)
```

The output above shows:

- the steps performed by gradle (command names are prefixed by ':' such as :CreateConfig) 
- Create jsqsh configuration for Big SQL
- CreateTrustore step creates the keystore using your cluster SSL certificate
- Download and install jsqsh

All code is well commented and it is suggested that you browse the build.gradle and *.groovy scripts to understand in more detail how they work.

## Help

If you run into the following issue, see the [FAQ](../../FAQ.md)
```
> Failed to create MD5 hash for ...
```
