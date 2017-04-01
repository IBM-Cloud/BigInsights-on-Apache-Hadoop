## Overview

This example shows how to execute the spark wordcount example on the BigInsights cluster.  The wordcount is performed on an Apache 2 License file.

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Able to read code written in a high level language such as [Groovy](http://www.groovy-lang.org/)
- Familiar with the [Gradle](https://gradle.org/) build tool
- Familiar with Spark concepts

## Example Requirements

- You meet the [pre-requisites](../../README.md#pre-requisites) in the top level [README](../../README.md)
- You have performed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)

## Run the example

To run the examples, in a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Example` (OS X / *nix)
      - `gradlew.bat Example` (Windows)

Output from the command will contain the wordcounts:

```
...
bicluster#54|: 1136
bicluster#54|limited: 4
bicluster#54|all: 3
bicluster#54|code: 1
bicluster#54|managed: 1
bicluster#54|customary: 1
bicluster#54|Works,: 2
bicluster#54|APPENDIX:: 1
...

```
## Decomposition Instructions

The [./build.gradle](./build.gradle) script runs the example.  It uses a ssh plugin to:

- copy [./wordcount.py](./wordcount.py) and [./LICENSE](./LICENSE) files to the BigInsights cluster
- from the ssh session, use the `hadoop fs` command to add the LICENSE to hdfs
- from the ssh session, execute the wordcount.py script with the `pyspark` command
