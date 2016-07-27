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

# We want to list the contents of the root folder
DIR="/"

# execute curl and save the output
OUTPUT=$(curl -v -s -i -k -u ${username}:${password} -X GET \
    "${gateway}/webhdfs/v1/${DIR}?op=LISTSTATUS")

# was the curl command successful?
SUCCESS="$(echo $OUTPUT | grep -c 'HTTP/1.1 200 OK')"

# show the output from the curl command
echo $OUTPUT

if [[ $SUCCESS == 1 ]]
then
   printf "\n>> Ls test was successful.\n\n"
   exit 0
else
   printf "\n>> Ls test was not successful.\n\n"
   exit 1
fi
