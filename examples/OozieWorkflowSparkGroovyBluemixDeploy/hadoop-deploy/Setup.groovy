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
import org.apache.hadoop.gateway.shell.workflow.Workflow

import static java.util.concurrent.TimeUnit.SECONDS

def env = System.getenv()
gateway = env.gateway
username = env.username
password = env.password

inputFile = "LICENSE"
jobDir = "/user/" + username + "/oozie_spark_bluemix_test"

localWordCountJar = "./build/libs/OozieWorkflowSparkGroovyBluemixDeploy.jar"

hdfsWordCountJar = "${jobDir}/lib/OozieWorkflowSparkGroovyBluemixDeploy.jar"

session = Hadoop.login( gateway, username, password )

// we need to inpsect the /iop/apps/ dir to find out where spark-assembly.jar is located
dirText = Hdfs.ls( session ).dir( "/iop/apps/" ).now().string
json = (new JsonSlurper()).parseText( dirText )
biVersion = json.FileStatuses.FileStatus.pathSuffix[0]

hdfsSparkAssyJar = "/iop/apps/${biVersion}/spark/jars/spark-assembly.jar"

definition = """\
<workflow-app xmlns='uri:oozie:workflow:0.5' name='SparkWordCount'>
 <start to='spark-node' />
  <action name='spark-node'>
   <spark xmlns="uri:oozie:spark-action:0.1">
    <job-tracker>\${jobTracker}</job-tracker>
    <name-node>\${nameNode}</name-node>
    <master>\${master}</master>
    <mode>\${mode}</mode>
    <name>Spark-Wordcount</name>
    <class>org.apache.spark.examples.WordCount</class>
    <jar>\${nameNode}/\${hdfsWordCountJar}</jar>
    <spark-opts>--conf spark.driver.extraJavaOptions=-Diop.version=4.2.0.0</spark-opts>
    <arg>\${inputDir}/FILE</arg>
    <arg>\${outputDir}</arg>
   </spark>
   <ok to="end" />
   <error to="fail" />
  </action>
  <kill name="fail">
   <message>Workflow failed, error
    message[\${wf:errorMessage(wf:lastErrorNode())}]
   </message>
  </kill>
 <end name='end' />
</workflow-app>
"""

// uncomment to debug the xml
// println( definition )

println "[Example.groovy] Delete " + jobDir + ": " + Hdfs.rm( session ).file( jobDir ).recursive().now().statusCode
println "[Example.groovy] Mkdir " + jobDir + ": " + Hdfs.mkdir( session ).dir( jobDir ).now().statusCode

putData = Hdfs.put(session).file( inputFile ).to( jobDir + "/input/FILE" ).later() {
  println "[Example.groovy] Put " + jobDir + "/input/FILE: " + it.statusCode }

putJar = Hdfs.put(session).file( localWordCountJar ).to( hdfsWordCountJar ).later() {
  println "[Example.groovy] Put " + hdfsWordCountJar + " " + it.statusCode }

putWorkflow = Hdfs.put(session).text( definition ).to( jobDir + "/workflow.xml" ).later() {
  println "[Example.groovy] Put " + jobDir + "/workflow.xml: " + it.statusCode }

println "[Example.groovy] Uploading jar file may take some time..."
session.waitFor( putWorkflow, putData, putJar )

// Need spark-assembly.jar in oozie lib path.
// We could update the shared oozie lib but requires to copy lib and update sharedlib.
// One way is to add the jar to lib/ directory under the oozie job directory
println "[Example.groovy] Downloading spark assembly jar file, may take some time..."
Hdfs.get( session ).file( "spark-assembly.jar" ).from( hdfsSparkAssyJar ).now()
println "[Example.groovy] Uploading spark assembly jar file to job lib dir, may take some time..."
Hdfs.put(session).file( "spark-assembly.jar" ).to( jobDir + "/lib/spark-assembly.jar" ).now() 

session.shutdown()
