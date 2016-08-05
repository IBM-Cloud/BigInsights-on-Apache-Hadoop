#!/bin/bash
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# IMPORTANT: Before you make any changes to this file, ensure that you 
#            stop Zeppelin with `./gradlew stop`
#
#            After making the change run `./gradlew UpdateEnv`, followed
#            by `./gradlew start` or `./gradlew restart`

export ZEPPELIN_PORT=8080
export SPARK_HOME=/usr/iop/current/spark-client
export HADOOP_CONF_DIR=/usr/iop/current/hadoop-client/conf
