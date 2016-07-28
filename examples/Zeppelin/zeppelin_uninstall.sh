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

# debug output
set -x

ZEPPELIN=zeppelin-0.6.0-bin-all

# zeppelin isn't installed so we can just exit
if [[ ! -d ./${ZEPPELIN} ]]
then
   echo 'Existing zeppelin install not found, so exiting'
   exit 0
fi

# suppress any errors trying to run zeppelin-daemon.sh stop
./${ZEPPELIN}/bin/zeppelin-daemon.sh stop | echo -n ''

BACKUP_DATE=$(date +%Y%m%d%H%M%S)
BACKUP_DIR=./zeppelin_backup_${BACKUP_DATE}

mkdir -p ${BACKUP_DIR}/conf
mkdir -p ${BACKUP_DIR}/notebook

cp -rvf ./${ZEPPELIN}/conf/* ./${BACKUP_DIR}/conf
cp -rvf ./${ZEPPELIN}/notebook/* ./${BACKUP_DIR}/notebook

echo '\n\n'
echo "** INFO: zeppelin conf and notebook folders backed up to ${BACKUP_DIR}"
echo '\n\n'

rm -rf ./${ZEPPELIN}

rm -f ${ZEPPELIN}.tgz
rm -f ${ZEPPELIN}.tgz.*
rm -f zeppelin_install.sh
rm -f zeppelin_uninstall.sh
rm -f zeppelin_env.sh

