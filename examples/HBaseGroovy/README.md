## Overview

This example shows how to programmaticaly interact with Apache HBASE on the BigInsights cluster. It uses [HBASE REST api](http://hbase.apache.org/book.html#_rest).

Apache HBase provides efficient random, realtime read/write access to your data. It can store very large tables (billions of rows X millions of columns) on top of HDFS distributed file system.

BigInsights for Apache Hadoop clusters are secured using [Apache Knox](https://knox.apache.org/).  Apache Knox is a REST API Gateway for interacting with Apache Hadoop clusters.  The Apache Knox project provides a [Java client library](https://cwiki.apache.org/confluence/display/KNOX/Client+Usage) and this can be used for interacting with the [HBase REST API](http://hbase.apache.org/book.html#_rest).  Using a library is usally more productive because the library provides a higher level of abstraction than when working directly with the REST API.

See also:

- [BigInsights Apache Knox documentation](https://www.ibm.com/support/knowledgecenter/en/SSPT3X_4.2.0/com.ibm.swg.im.infosphere.biginsights.admin.doc/doc/knox_overview.html)
- [Apache HBase](https://hbase.apache.org)
- [Apache HBase REST API](http://hbase.apache.org/book.html#_rest)

## Developer experience

Developers will gain the most from these examples if they are:

- Comfortable using Windows, OS X or *nix command prompts
- Familiar with Columnar database or store
- Able to read code written in a high level language such as [Groovy](http://www.groovy-lang.org/)
- Familiar with the [Gradle](https://gradle.org/) build tool

## Example Requirements

Your have met the [pre-requisites](../../README.md#pre-requisites) and have followed the [setup instructions](../../README.md#setup-instructions) in the top level [README](../../README.md)

## Run the example

There are two examples in this folder written in the [Groovy Language](http://www.groovy-lang.org/):

- [Connect.groovy](./Connect.groovy) - this example connects to HBASE REST api and get system version, cluster version and cluster status
- [CRUD.groovy](./CRUD.groovy) - this example connects to HBASE REST api and perform CRUD operations:
    - create table
    - update table schema
    - insert data
    - query data
    - delete data
    - drop table

To run the examples, in a command prompt window:

   - change into the directory containing this example and run gradle to execute the example
      - `./gradlew Example` (OS X / *nix)
      - `gradlew.bat Example` (Windows)
   - some output from running the command on my machine is shown below

```bash
$ cd examples/HBaseroovy
$ ./gradlew Example
:compileJava UP-TO-DATE
:compileGroovy
:processResources UP-TO-DATE
:classes
:CRUD
log4j:WARN No appenders could be found for logger (org.apache.http.impl.conn.PoolingClientConnectionManager).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
>> System version : {"Server":"jetty/6.1.26-ibm","Jersey":"1.9","REST":"0.0.3","JVM":"Oracle Corporation 1.8.0_77-25.77-b03","OS":"Linux 2.6.32-642.1.1.el6.x86_64 amd64"}
>> Cluster version : 1.2.0-IBM-7
>> Status : {"regions":4,"requests":0,"averageLoad":1.3333333333333333,"LiveNodes":[{"name":"https://bicloud-186-mastermanager.bicloud.com:8443/gateway/default/hbase-region?_=AAAACAAAABAAAAAwVc1vIRCqbPD895dJF7QGz90dHjToCimwvvQ4eybZtuxNk5dx_ltkB01Awod2_yNPpgWrtdl_xYZE9nC9JC8f1EDh4XlOxXKA","startCode":1469409015544,"requests":0,"heapSizeMB":19,"maxHeapSizeMB":1004,"Region":[{"name":"aGJhc2U6bmFtZXNwYWNlLCwxNDY5Mzk4NTcwMzg5LjM5NWY0YzEyMjBhZGY0NDA3ODZlZWI2NGQyOGU5YzQ3Lg==","stores":1,"storefiles":1,"storefileSizeMB":0,"memstoreSizeMB":0,"storefileIndexSizeMB":0,"readRequestsCount":4,"writeRequestsCount":0,"rootIndexSizeKB":0,"totalStaticIndexSizeKB":0,"totalStaticBloomSizeKB":0,"totalCompactingKVs":0,"currentCompactedKVs":0}]},{"name":"https://bicloud-186-mastermanager.bicloud.com:8443/gateway/default/hbase-region?_=AAAACAAAABAAAAAwVc1vIRCqbPD895dJF7QGz90dHjToCimwvvQ4eybZtuxNk5dx_ltkB5SW0cNqt9Rv3v7Jf5WTTwaDOS4uMBlDJayq8OpDRyM5","startCode":1469409008640,"requests":0,"heapSizeMB":122,"maxHeapSizeMB":1004,"Region":[{"name":"YW1iYXJpc21va2V0ZXN0LCwxNDY5NDE4Nzg5ODQ0LmFkOTVhZDUwZWM0ZDVkYzJkZjgxZDE4MGY3YWQzYTViLg==","stores":1,"storefiles":1,"storefileSizeMB":0,"memstoreSizeMB":0,"storefileIndexSizeMB":0,"readRequestsCount":2,"writeRequestsCount":1,"rootIndexSizeKB":0,"totalStaticIndexSizeKB":0,"totalStaticBloomSizeKB":0,"totalCompactingKVs":0,"currentCompactedKVs":0},{"name":"aGJhc2U6bWV0YSwsMQ==","stores":1,"storefiles":2,"storefileSizeMB":0,"memstoreSizeMB":0,"storefileIndexSizeMB":0,"readRequestsCount":1533,"writeRequestsCount":15,"rootIndexSizeKB":0,"totalStaticIndexSizeKB":0,"totalStaticBloomSizeKB":0,"totalCompactingKVs":24,"currentCompactedKVs":24}]},{"name":"https://bicloud-186-mastermanager.bicloud.com:8443/gateway/default/hbase-region?_=AAAACAAAABAAAAAwVc1vIRCqbPD895dJF7QGz90dHjToCimwvvQ4eybZtuxNk5dx_ltkBzTDXDsccpnz1RrlZlGlEugAPBYDY6u2jtRlY-P9VI-a","startCode":1469409009629,"requests":0,"heapSizeMB":111,"maxHeapSizeMB":1004,"Region":[{"name":"aGJhc2U6YWNsLCwxNDY5Mzk4NTcxNjg1LjE3N2I2MTQ5MTI0MzMxOGM2ZGM5YjdmNTc3Nz                               U0NDRkLg==","stores":1,"storefiles":1,"storefileSizeMB":0,"memstoreSizeMB":0,"storefileIndexSizeMB":0,"readRequestsCount":10,"writeRequestsCount":9,"rootIndexSizeKB":0,"totalStaticIndexSizeKB":0,"totalStaticBloomSizeKB":0,"totalCompactingKVs":4,"currentCompactedKVs":4}]}],"DeadNodes":[]}
>> Creating table 'test_1469556834533'...
>> Done
>> Table List : {"table":[{"name":"ambarismoketest"},{"name":"test_1469556834533"}]}
>> Schema for table 'test_1469556834533' : {"name":"test_1469556834533","ColumnSchema":[{"name":"family1","VERSIONS":"1","fm_attr1":"value3","fm_attr2":"value4","KEEP_DELETED_CELLS":"FALSE","DATA_BLOCK_ENCODING":"NONE","TTL":"2147483647","MIN_VERSIONS":"0","REPLICATION_SCOPE":"0","BLOOMFILTER":"ROW","IN_MEMORY":"false","COMPRESSION":"NONE","BLOCKCACHE":"true","BLOCKSIZE":"65536"},{"name":"family2","BLOOMFILTER":"ROW","VERSIONS":"1","IN_MEMORY":"false","KEEP_DELETED_CELLS":"FALSE","DATA_BLOCK_ENCODING":"NONE","TTL":"2147483647","COMPRESSION":"NONE","MIN_VERSIONS":"0","BLOCKCACHE":"true","BLOCKSIZE":"65536","REPLICATION_SCOPE":"0"},{"name":"family3","BLOOMFILTER":"ROW","VERSIONS":"1","IN_MEMORY":"false","KEEP_DELETED_CELLS":"FALSE","DATA_BLOCK_ENCODING":"NONE","TTL":"2147483647","COMPRESSION":"NONE","MIN_VERSIONS":"0","BLOCKCACHE":"true","BLOCKSIZE":"65536","REPLICATION_SCOPE":"0"}],"IS_META":"false","tb_attr2":"value2","tb_attr1":"value1","tb_attr3":"value5"}
>> Updating schema of table 'test_1469556834533'...
>> Done
>> Schema for table 'test_1469556834533' : {"name":"test_1469556834533","ColumnSchema":[{"name":"family1","BLOOMFILTER":"ROW","VERSIONS":"1","IN_MEMORY":"false","fm_attr1":"new_value3","KEEP_DELETED_CELLS":"FALSE","DATA_BLOCK_ENCODING":"NONE","TTL":"2147483647","COMPRESSION":"NONE","MIN_VERSIONS":"0","BLOCKCACHE":"true","BLOCKSIZE":"65536","REPLICATION_SCOPE":"0"},{"name":"family2","BLOOMFILTER":"ROW","VERSIONS":"1","IN_MEMORY":"false","KEEP_DELETED_CELLS":"FALSE","DATA_BLOCK_ENCODING":"NONE","TTL":"2147483647","COMPRESSION":"NONE","MIN_VERSIONS":"0","BLOCKCACHE":"true","BLOCKSIZE":"65536","REPLICATION_SCOPE":"0"},{"name":"family3","BLOOMFILTER":"ROW","VERSIONS":"1","IN_MEMORY":"false","KEEP_DELETED_CELLS":"FALSE","DATA_BLOCK_ENCODING":"NONE","TTL":"2147483647","COMPRESSION":"NONE","MIN_VERSIONS":"0","BLOCKCACHE":"true","BLOCKSIZE":"65536","REPLICATION_SCOPE":"0"},{"name":"family4","BLOOMFILTER":"ROW","VERSIONS":"1","IN_MEMORY":"false","fm_attr3":"value6","KEEP_DELETED_CELLS":"FALSE","DATA_BLOCK_ENCODING":"NONE","TTL":"2147483647","COMPRESSION":"NONE","MIN_VERSIONS":"0","BLOCKCACHE":"true","BLOCKSIZE":"65536","REPLICATION_SCOPE":"0"}],"IS_META":"false","tb_attr2":"value2","tb_attr1":"value1","tb_attr3":"value5"}
>> Inserting data into table...
>> Done
>> Querying row by id...
{"Row":[{"key":"cm93X2lkXzE=","Cell":[{"column":"ZmFtaWx5MTpjb2wx","timestamp":1469556844525,"$":"Y29sX3ZhbHVlMQ=="},{"column":"ZmFtaWx5MTpjb2wy","timestamp":1234567890,"$":"Y29sX3ZhbHVlMg=="},{"column":"ZmFtaWx5Mjo=","timestamp":1469556844525,"$":"ZmFtX3ZhbHVlMQ=="}]}]}
>> Querying all rows...
{"Row":[{"key":"cm93X2lkXzE=","Cell":[{"column":"ZmFtaWx5MTpjb2wx","timestamp":1469556844525,"$":"Y29sX3ZhbHVlMQ=="},{"column":"ZmFtaWx5MTpjb2wy","timestamp":1234567890,"$":"Y29sX3ZhbHVlMg=="},{"column":"ZmFtaWx5Mjo=","timestamp":1469556844525,"$":"ZmFtX3ZhbHVlMQ=="}]},{"key":"cm93X2lkXzI=","Cell":[{"column":"ZmFtaWx5MTpyb3cyX2NvbDE=","timestamp":1469556844603,"$":"cm93Ml9jb2xfdmFsdWUx"}]}]}
>> Querying row by id with extended settings...
{"Row":[{"key":"cm93X2lkXzE=","Cell":[{"column":"ZmFtaWx5Mjo=","timestamp":1469556844525,"$":"ZmFtX3ZhbHVlMQ=="}]},{"key":"cm93X2lkXzI=","Cell":[{"column":"ZmFtaWx5MTpyb3cyX2NvbDE=","timestamp":1469556844603,"$":"cm93Ml9jb2xfdmFsdWUx"}]}]}
>> Deleting cell...
>> Rows after delete:
{"Row":[{"key":"cm93X2lkXzE=","Cell":[{"column":"ZmFtaWx5MTpjb2wy","timestamp":1234567890,"$":"Y29sX3ZhbHVlMg=="},{"column":"ZmFtaWx5Mjo=","timestamp":1469556844525,"$":"ZmFtX3ZhbHVlMQ=="}]},{"key":"cm93X2lkXzI=","Cell":[{"column":"ZmFtaWx5MTpyb3cyX2NvbDE=","timestamp":1469556844603,"$":"cm93Ml9jb2xfdmFsdWUx"}]}]}
>> Extended cell delete
>> Rows after delete:
{"Row":[{"key":"cm93X2lkXzE=","Cell":[{"column":"ZmFtaWx5MTpjb2wy","timestamp":1234567890,"$":"Y29sX3ZhbHVlMg=="}]},{"key":"cm93X2lkXzI=","Cell":[{"column":"ZmFtaWx5MTpyb3cyX2NvbDE=","timestamp":1469556844603,"$":"cm93Ml9jb2xfdmFsdWUx"}]}]}
>> Table regions : {"name":"test_1469556834533","Region":[{"id":1469556836113,"startKey":"","endKey":"","location":"https://bicloud-186-mastermanager.bicloud.com:8443/gateway/default/hbase-region?_=AAAACAAAABAAAAAwVc1vIRCqbPD895dJF7QGz90dHjToCimwvvQ4eybZtuxNk5dx_ltkBzTDXDsccpnz1RrlZlGlEugAPBYDY6u2jtRlY-P9VI-a","name":"test_1469556834533,,1469556836113.4da0e7abd3d57d2cc4470e0f534bce14."}]}
>> Creating scanner...
Scanner id=14695568453357e3fff17
>> Scanner get next...
{"Row":[{"key":"cm93X2lkXzE=","Cell":[{"column":"ZmFtaWx5MTpjb2wy","timestamp":1234567890,"$":"Y29sX3ZhbHVlMg=="}]}]}
>> Dropping scanner with id=14695568453357e3fff17
>> Done
>> Dropping table 'test_1469556834533'...
>> Done
:Connect
log4j:WARN No appenders could be found for logger (org.apache.http.impl.conn.PoolingClientConnectionManager).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
>> System version : {"Server":"jetty/6.1.26-ibm","Jersey":"1.9","REST":"0.0.3","JVM":"Oracle Corporation 1.8.0_77-25.77-b03","OS":"Linux 2.6.32-642.1.1.el6.x86_64 amd64"}
>> Cluster version : 1.2.0-IBM-7
>> Status : {"regions":4,"requests":5,"averageLoad":1.3333333333333333,"LiveNodes":[{"name":"https://bicloud-186-mastermanager.bicloud.com:8443/gateway/default/hbase-region?_=AAAACAAAABAAAAAwVc1vIRCqbPD895dJF7QGz90dHjToCimwvvQ4eybZtuxNk5dx_ltkB01Awod2_yNPpgWrtdl_xYZE9nC9JC8f1EDh4XlOxXKA","startCode":1469409015544,"requests":0,"heapSizeMB":20,"maxHeapSizeMB":1004,"Region":[{"name":"aGJhc2U6bmFtZXNwYWNlLCwxNDY5Mzk4NTcwMzg5LjM5NWY0YzEyMjBhZGY0NDA3ODZlZWI2NGQyOGU5YzQ3Lg==","stores":1,"storefiles":1,"storefileSizeMB":0,"memstoreSizeMB":0,"storefileIndexSizeMB":0,"readRequestsCount":4,"writeRequestsCount":0,"rootIndexSizeKB":0,"totalStaticIndexSizeKB":0,"totalStaticBloomSizeKB":0,"totalCompactingKVs":0,"currentCompactedKVs":0}]},{"name":"https://bicloud-186-mastermanager.bicloud.com:8443/gateway/default/hbase-region?_=AAAACAAAABAAAAAwVc1vIRCqbPD895dJF7QGz90dHjToCimwvvQ4eybZtuxNk5dx_ltkB5SW0cNqt9Rv3v7Jf5WTTwaDOS4uMBlDJayq8OpDRyM5","startCode":1469409008640,"requests":1,"heapSizeMB":129,"maxHeapSizeMB":1004,"Region":[{"name":"YW1iYXJpc21va2V0ZXN0LCwxNDY5NDE4Nzg5ODQ0LmFkOTVhZDUwZWM0ZDVkYzJkZjgxZDE4MGY3YWQzYTViLg==","stores":1,"storefiles":1,"storefileSizeMB":0,"memstoreSizeMB":0,"storefileIndexSizeMB":0,"readRequestsCount":2,"writeRequestsCount":1,"rootIndexSizeKB":0,"totalStaticIndexSizeKB":0,"totalStaticBloomSizeKB":0,"totalCompactingKVs":0,"currentCompactedKVs":0},{"name":"aGJhc2U6bWV0YSwsMQ==","stores":1,"storefiles":2,"storefileSizeMB":0,"memstoreSizeMB":0,"storefileIndexSizeMB":0,"readRequestsCount":1548,"writeRequestsCount":19,"rootIndexSizeKB":0,"totalStaticIndexSizeKB":0,"totalStaticBloomSizeKB":0,"totalCompactingKVs":24,"currentCompactedKVs":24}]},{"name":"https://bicloud-186-mastermanager.bicloud.com:8443/gateway/default/hbase-region?_=AAAACAAAABAAAAAwVc1vIRCqbPD895dJF7QGz90dHjToCimwvvQ4eybZtuxNk5dx_ltkBzTDXDsccpnz1RrlZlGlEugAPBYDY6u2jtRlY-P9VI-a","startCode":1469409009629,"requests":4,"heapSizeMB":138,"maxHeapSizeMB":1004,"Region":[{"name":"aGJhc2U6YWNsLCwxNDY5Mzk4NTcxNjg1LjE3N2I2MTQ5MTI0MzMxOGM2ZGM5YjdmNTc3Nz                               U0NDRkLg==","stores":1,"storefiles":1,"storefileSizeMB":0,"memstoreSizeMB":0,"storefileIndexSizeMB":0,"readRequestsCount":12,"writeRequestsCount":11,"rootIndexSizeKB":0,"totalStaticIndexSizeKB":0,"totalStaticBloomSizeKB":0,"totalCompactingKVs":4,"currentCompactedKVs":4}]}],"DeadNodes":[]}
:Example

BUILD SUCCESSFUL

Total time: 23.477 secs
```

The output above shows:

- the steps performed by gradle (command names are prefixed by ':' such as :compileGroovy) 
- after `:CRUD` you see test output for the CRUD test
- after `:Connect` you see test output for the Connect test
 
## Decomposition Instructions

The examples use a gradle build file [build.gradle](./build.gradle) when you run `./gradlew` or `gradle.bat`.  The build.gradle for this example does the following:

- each example simply compiles and executed the groovy code
- as part of the groovy code, each test:
   - connects to HBASE REST API via knox shell
   - executes HBASE operation via knox shell

All code is well commented and it is suggested that you browse the build.gradle and *.groovy scripts to understand in more detail how they work.

