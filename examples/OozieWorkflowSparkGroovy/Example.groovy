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
jobDir = "/user/" + username + "/test"
jarFile = "./build/libs/OozieWorkflowSparkGroovy.jar"

session = Hadoop.login( gateway, username, password )

// we need to inpsect the /iop/apps/ dir to find out where spark-assembly.jar is located
dirText = Hdfs.ls( session ).dir( "/iop/apps/" ).now().string
json = (new JsonSlurper()).parseText( dirText )
biVersion = json.FileStatuses.FileStatus.pathSuffix[0]
sparkAssyJar = "/iop/apps/${biVersion}/spark/jars/spark-assembly.jar"

definition = """\
<workflow-app xmlns="uri:oozie:workflow:0.2" name="wordcount-workflow">
    <start to="root-node"/>
    <action name="root-node">
        <java>
            <job-tracker>\${jobTracker}</job-tracker>
            <name-node>\${nameNode}</name-node>
            <main-class>org.apache.spark.deploy.SparkSubmit</main-class>
            <arg>--class</arg>
            <arg>org.apache.spark.examples.WordCount</arg>
            <arg>--deploy-mode</arg>
            <arg>cluster</arg>
            <arg>--master</arg>
            <arg>yarn-cluster</arg>
            <arg>--queue</arg>
            <arg>default</arg>
            <arg>--num-executors</arg>
            <arg>2</arg>
            <arg>--executor-cores</arg>
            <arg>2</arg>
            <arg>spark-wordcount-example.jar</arg>
            <arg>\${inputDir}/FILE</arg>
            <arg>\${outputDir}</arg>
            <file>\${jobDir}/lib/spark-wordcount-example.jar</file>
            <file>${sparkAssyJar}</file>
            <capture-output/>
        </java>
        <ok to="end"/>
        <error to="fail"/>
    </action>
    <kill name="fail">
        <message>Java failed, error message[\${wf:errorMessage(wf:lastErrorNode())}]</message>
    </kill>
    <end name="end"/>
</workflow-app>
"""

configuration = """\
<configuration>
    <property>
        <name>user.name</name>
        <value>default</value>
    </property>
    <property>
        <name>nameNode</name>
        <value>default</value>
    </property>
    <property>
        <name>jobTracker</name>
        <value>default</value>
    </property>
    <property>
        <name>jobDir</name>
        <value>$jobDir</value>
    </property>
    <property>
        <name>inputDir</name>
        <value>$jobDir/input</value>
    </property>
    <property>
        <name>outputDir</name>
        <value>$jobDir/output</value>
    </property>
    <property>
        <name>oozie.wf.application.path</name>
        <value>$jobDir</value>
    </property>
</configuration>
"""


println "Delete " + jobDir + ": " + Hdfs.rm( session ).file( jobDir ).recursive().now().statusCode
println "Mkdir " + jobDir + ": " + Hdfs.mkdir( session ).dir( jobDir ).now().statusCode

putData = Hdfs.put(session).file( inputFile ).to( jobDir + "/input/FILE" ).later() {
  println "Put " + jobDir + "/input/FILE: " + it.statusCode }

putJar = Hdfs.put(session).file( jarFile ).to( jobDir + "/lib/spark-wordcount-example.jar" ).later() {
  println "Put " + jobDir + "/lib/spark-wordcount-example.jar:" + it.statusCode }

putWorkflow = Hdfs.put(session).text( definition ).to( jobDir + "/workflow.xml" ).later() {
  println "Put " + jobDir + "/workflow.xml: " + it.statusCode }

println "Uploading jar file may take some time..."
session.waitFor( putWorkflow, putData, putJar )

jobId = Workflow.submit(session).text( configuration ).now().jobId
println "Submitted job: " + jobId

println "Polling up to 300s for job completion..."
status = "RUNNING";
count = 0;
while( status == "RUNNING" && count++ < 300 ) {
  sleep( 1000 )
  json = Workflow.status(session).jobId( jobId ).now().string
  status = JsonPath.read( json, "\$.status" )
  print "."; System.out.flush();
}
println ""
println "Job status: " + status

if( status == "SUCCEEDED" ) {
  text = Hdfs.ls( session ).dir( jobDir + "/output" ).now().string
  json = (new JsonSlurper()).parseText( text )
  println json.FileStatuses.FileStatus.pathSuffix

  println "Spark output:"
  println Hdfs.get( session ).from( jobDir + "/output/part-00000" ).now().string
  println Hdfs.get( session ).from( jobDir + "/output/part-00001" ).now().string
}

session.shutdown()
