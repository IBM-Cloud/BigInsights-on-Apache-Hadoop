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

# abort script if any commands return an error
set -e

# Uncomment to debug
#set -x

DIR="/user/${username}/test-$(date +%s)"

FILE="LICENSE"

# create the temporary directory
CMD="curl -s -i -k --negotiate -u ${username}: -X PUT '${gateway}/webhdfs/v1/${DIR}?op=MKDIRS' | grep 'HTTP/1.1 200 OK'"

echo $CMD

# register the name for the file, and get the location (use tr to strip header CRLF
LOCATION=$(curl -s -i -k -u ${username}:${password} -X PUT "${gateway}/webhdfs/v1/${DIR}/${FILE}?op=CREATE" | tr -d '\r' | sed -En "s/^Location: (.*)$/\1/p")

# cmd to send the file to the location
curl -s -i -k -u ${username}:${password} -T ${FILE} -X PUT ${LOCATION} | grep 'HTTP/1.1 201 Created'

# clean up - remove the temporary directory
curl -s -i -k -u ${username}:${password} -X DELETE "${gateway}/webhdfs/v1/${DIR}?op=DELETE&recursive=true" | grep 'HTTP/1.1 200 OK' 

printf "\n>> Put test was successful.\n\n"
