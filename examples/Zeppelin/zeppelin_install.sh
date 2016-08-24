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

# debugging - echo commands as they are executed
set -x 

ZEPPELIN=zeppelin-0.6.0-bin-all

if [[ -d ./${ZEPPELIN} ]]
then
   echo "*******"
   echo "ERROR: Found existing ${ZEPPELIN} installation.  Uninstall before retrying the installation."
   echo "*******"
   exit 0
fi

cat << EOF > ${ZEPPELIN}.tgz.md5
62a018ccbcc9bbff254bed3001b31b29  zeppelin-0.6.0-bin-all.tgz
EOF

if [[ ! -e ${ZEPPELIN}.tgz || "$(md5sum -c --status ${ZEPPELIN}.tgz.md5)" != 0 ]]
then
    echo "Couldn't find ${ZEPPELIN}.tgz so downloading now ..."
    wget -q http://archive.apache.org/dist/zeppelin/zeppelin-0.6.0/${ZEPPELIN}.tgz
fi

tar xzf ${ZEPPELIN}.tgz

# copy the zeppelin environment variable script to the conf folder
cp ./zeppelin_env.sh ./${ZEPPELIN}/conf/zeppelin-env.sh

# load the zeppelin envivonment variables
source ./${ZEPPELIN}/conf/zeppelin-env.sh

./${ZEPPELIN}/bin/zeppelin-daemon.sh start

