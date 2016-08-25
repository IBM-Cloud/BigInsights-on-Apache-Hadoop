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

/* NOTE: 
    The package org.apache.hadoop.gateway.shell.job.Job is locally patched.
    You can see code under src/main/java/org/apache/hadoop/gateway.
    See JIRA: https://issues.apache.org/jira/browse/KNOX-743 for more detail.
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

jobDir = "/user/" + username + "/sqoop"

session = Hadoop.login( gateway, username, password )

println "[Sqoop.groovy] Delete " + jobDir + ": " + Hdfs.rm( session ).file( jobDir ).recursive().now().statusCode
println "[Sqoop.groovy] Mkdir " + jobDir + ": " + Hdfs.mkdir( session ).dir( jobDir ).now().statusCode

// Database connection information
db = [ url:env.dashdb_url, user:env.dashdb_username, password:env.dashdb_password, name:"SAMPLES", table:"LANGUAGE" ]

targetdir = jobDir + "/" + db.table

sqoop_command = "import --connect ${db.url} --username ${db.user} --password ${db.password} --table ${db.name}.${db.table} -m1 --target-dir ${targetdir}"

jobId = Job.submitSqoop(session) \
            .command(sqoop_command) \
            .statusDir("${jobDir}/output") \
            .now().jobId

println "[Sqoop.groovy] Submitted job: " + jobId

println "[Sqoop.groovy] Polling up to 60s for job completion..."
done = false
count = 0
while( !done && count++ < 60 ) {
  sleep( 1000 )
  json = Job.queryStatus(session).jobId(jobId).now().string
  done = JsonPath.read( json, "\$.status.jobComplete" )
  print "."; System.out.flush();
}
println ""
println "[Sqoop.groovy] Job status: " + done


// Check output directory
text = Hdfs.ls( session ).dir( jobDir + "/output" ).now().string
json = (new JsonSlurper()).parseText( text )
println json.FileStatuses.FileStatus.pathSuffix

println "\n[Sqoop.groovy] Content of stderr:"
println Hdfs.get( session ).from( jobDir + "/output/stderr" ).now().string

// Check table files
text = Hdfs.ls( session ).dir( jobDir + "/" + db.table ).now().string
json = (new JsonSlurper()).parseText( text )
println json.FileStatuses.FileStatus.pathSuffix

println "\n[Sqoop.groovy] Content of part-m-00000"
println Hdfs.get( session ).from( jobDir + "/" + db.table + "/part-m-00000" ).now().string



session.shutdown()
