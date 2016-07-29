#!/bin/bash

# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# disallow undefined variables
set -u

# abort script if any commands return an error
set -e

# enable debug output
# set -x

# environment variables that are set by build.gradle for this script.
# set the variables below to run this script standalone (without gradle)
#
#   gateway
#   username
#   password

# our working folders
DIR="/user/${username}/test-$(date +%s)"
INPUT_DIR="${DIR}/input"
OUTPUT_DIR="${DIR}/output"

# we will use MapReduce to perform a word count on the Apache License
FILE="LICENSE"

################################################################################
# create the workflow definition

cat << EOF > workflow-definition.xml
<workflow-app xmlns="uri:oozie:workflow:0.2" name="wordcount-workflow">
    <start to="root"/>
    <action name="root">
        <java>
            <job-tracker>\${jobTracker}</job-tracker>
            <name-node>\${nameNode}</name-node>
            <main-class>org.apache.hadoop.examples.WordCount</main-class>
            <arg>\${inputDir}</arg>
            <arg>\${outputDir}</arg>
        </java>
        <ok to="end"/>
        <error to="fail"/>
    </action>
    <kill name="fail">
        <message>Java failed, error message[\${wf:errorMessage(wf:lastErrorNode())}]</message>
    </kill>
    <end name="end"/>
</workflow-app>
EOF

################################################################################
# create the workflow configuration

cat << EOF > workflow-configuration.xml
<configuration>
    <property>
        <name>jobTracker</name>
        <value>default</value>
    </property>
    <property>
        <name>nameNode</name>
        <value>default</value>
    </property>
    <property>
        <name>oozie.wf.application.path</name>
        <value>${DIR}</value>
    </property>
    <property>
        <name>user.name</name>
        <value>${username}</value>
    </property>
    <property>
        <name>inputDir</name>
        <value>${INPUT_DIR}</value>
    </property>
    <property>
        <name>outputDir</name>
        <value>${OUTPUT_DIR}</value>
    </property>
</configuration>
EOF

################################################################################
# create the temporary directory

RESPONSE="$(curl -s -i -k -u ${username}:${password} -X PUT "${gateway}/webhdfs/v1/${DIR}?op=MKDIRS")"

# Test that the response contains at least one HTTP 200

if [[ "$(echo ${RESPONSE} | grep -c 'HTTP/1.1 200 OK')" == "0" ]]
then
    echo "Problem creating directory ${DIR}:\n"
    echo $RESPONSE
    exit 1
else
    echo "Successfully created directory ${DIR}"
fi


RESPONSE="$(curl -s -i -k -u ${username}:${password} -X PUT "${gateway}/webhdfs/v1/${INPUT_DIR}?op=MKDIRS")"

# Test that the response contains at least one HTTP 200

if [[ "$(echo ${RESPONSE} | grep -c 'HTTP/1.1 200 OK')" == "0" ]]
then
    echo "Problem creating directory ${INPUT_DIR}:\n"
    echo $RESPONSE
    exit 1
else
    echo "Successfully created directory ${INPUT_DIR}"
fi

################################################################################
# upload workflow.xml

# register the name for the file, and get the location (use tr to strip header CRLF
LOCATION=$(curl -s -i -k -u ${username}:${password} -X PUT "${gateway}/webhdfs/v1/${DIR}/workflow.xml?op=CREATE" | tr -d '\r' | sed -En "s/^Location: (.*)$/\1/p")

# cmd to send the file to the location
RESPONSE="$(curl -s -i -k -u ${username}:${password} -T workflow-definition.xml -X PUT ${LOCATION})"

# Test that the response contains at least one HTTP 201

if [[ "$(echo ${RESPONSE} | grep -c 'HTTP/1.1 201 Created')" == "0" ]]
then
    echo "Problem uploading workflow-definition.xml:\n"
    echo $RESPONSE
    exit 1
else
    echo "Successfully uploaded workflow-definition.xml"
fi

################################################################################
# upload hadoop-examples.jar

# register the name for the file, and get the location (use tr to strip header CRLF
LOCATION=$(curl -s -i -k -u ${username}:${password} -X PUT "${gateway}/webhdfs/v1/${DIR}/hadoop-examples.jar?op=CREATE" | tr -d '\r' | sed -En "s/^Location: (.*)$/\1/p")

# cmd to send the file to the location
RESPONSE="$(curl -s -i -k -u ${username}:${password} -T samples/hadoop-examples.jar -X PUT ${LOCATION})"

if [[ "$(echo ${RESPONSE} | grep -c 'HTTP/1.1 201 Created')" == "0" ]]
then
    echo "Problem uploading samples/hadoop-examples.jar\n"
    echo $RESPONSE
    exit 1
else
    echo "Successfully uploaded samples/hadoop-examples.jar"
fi

################################################################################
# upload LICENSE

# register the name for the file, and get the location (use tr to strip header CRLF
LOCATION=$(curl -s -i -k -u ${username}:${password} -X PUT "${gateway}/webhdfs/v1/${INPUT_DIR}/LICENSE?op=CREATE" | tr -d '\r' | sed -En "s/^Location: (.*)$/\1/p")

# cmd to send the file to the location
RESPONSE="$(curl -s -i -k -u ${username}:${password} -T LICENSE -X PUT ${LOCATION})"

if [[ "$(echo ${RESPONSE} | grep -c 'HTTP/1.1 201 Created')" == "0" ]]
then
    echo "Problem uploading LICENSE\n"
    echo $RESPONSE
    exit 1
else
    echo "Successfully uploaded LICENSE"
fi

################################################################################
# submit job

JOB_ID=$(curl -s -k -u ${username}:${password} -H 'Content-Type:application/xml' -T workflow-configuration.xml -X POST "${gateway}/oozie/v1/jobs?action=start" | perl -pe 's|.*"id":"([^"]*?)".*|\1|')

echo "Submitted oozie job. jobid: ${JOB_ID}"

################################################################################
# get job status

# attempt to get status for a max of approx 200 seconds (20 attempts * 10 seconds)
for i in {1..20}
do
    # we need the non greedy operator ? to get the first match for 'status'.  sed doesn't support ? on all platforms.
    STATUS=$(curl -s -k -u ${username}:${password} -X GET "${gateway}/oozie/v1/job/${JOB_ID}" | perl -pe 's|.*?"status":"([^"]*?)".*|\1|')
    echo status: ${STATUS}
    if [[ "${STATUS}" == 'RUNNING' || "${STATUS}" == 'PREP' ]]
    then
        # Sleep for 1 second
        sleep 1
    else
        # the job has finished running so exit the loop
        break
    fi
    sleep 10
done

################################################################################

if [[ "${STATUS}" == 'SUCCEEDED' || "${STATUS}" == 'OK' ]]
then
    # get the generated output
    curl -L -s -k -u ${username}:${password} -X GET "${gateway}/webhdfs/v1/${DIR}/output/part-r-00000?op=OPEN"

    # clean up - remove the temporary directory
    curl -s -i -k -u ${username}:${password} -X DELETE "${gateway}/webhdfs/v1/${DIR}?op=DELETE&recursive=true" | grep 'HTTP/1.1 200 OK' 

    printf "\n>> MapReduce test was successful.\n\n"
    exit 0
else
    # clean up - remove the temporary directory
    curl -s -i -k -u ${username}:${password} -X DELETE "${gateway}/webhdfs/v1/${DIR}?op=DELETE&recursive=true" | grep 'HTTP/1.1 200 OK' 

    printf "\n>> MapReduce test failed:\n\n"

    curl -s -k -u ${username}:${password} -X GET "${gateway}/oozie/v1/job/${JOB_ID}"

    echo ${STATUS}
    exit 1
fi

