/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.jayway.jsonpath.JsonPath
import groovy.json.JsonSlurper
import org.apache.hadoop.gateway.shell.Hadoop
import org.apache.hadoop.gateway.shell.hdfs.Hdfs
import org.apache.hadoop.gateway.shell.job.Job

import static java.util.concurrent.TimeUnit.SECONDS

def env = System.getenv()
gateway = env.gateway
username = env.username
password = env.password

inputFile = "LICENSE"
jobDir = "/user/" + username + "/test"
jarFile = "samples/hadoop-examples.jar"

session = Hadoop.login( gateway, username, password )

println "[MapReduce.groovy] Delete " + jobDir + ": " + Hdfs.rm( session ).file( jobDir ).recursive().now().statusCode
println "[MapReduce.groovy] Mkdir " + jobDir + ": " + Hdfs.mkdir( session ).dir( jobDir ).now().statusCode

println "[MapReduce.groovy] Copy input file to HDFS"
Hdfs.put(session).file( inputFile ).to( jobDir + "/input/FILE" ).now()
println "[MapReduce.groovy] Copy jar file to HDFS"
Hdfs.put(session).file( jarFile ).to( jobDir + "/lib/hadoop-examples.jar" ).now()

jobId = Job.submitJava(session) \
  .jar( jobDir + "/lib/hadoop-examples.jar" ) \
  .app( "org.apache.hadoop.examples.WordCount" ) \
  .input( jobDir + "/input" ) \
  .output( jobDir + "/output" ) \
  .now().jobId

println "[MapReduce.groovy] Submitted job: " + jobId

println "[MapReduce.groovy] Polling up to 60s for job completion..."
done = false
count = 0
while( !done && count++ < 60 ) {
  sleep( 1000 )
  json = Job.queryStatus(session).jobId(jobId).now().string
  done = JsonPath.read( json, "\$.status.jobComplete" )
  print "."; System.out.flush();
}
println ""
println "[MapReduce.groovy] Job status: " + done


text = Hdfs.ls( session ).dir( jobDir + "/output" ).now().string
json = (new JsonSlurper()).parseText( text )
println json.FileStatuses.FileStatus.pathSuffix

session.shutdown()
