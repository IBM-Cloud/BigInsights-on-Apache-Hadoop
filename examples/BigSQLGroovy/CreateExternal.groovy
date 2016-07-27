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

import groovy.sql.Sql
import org.apache.hadoop.gateway.shell.Hadoop
import org.apache.hadoop.gateway.shell.hdfs.Hdfs

env = System.getenv()

session = Hadoop.login( env.gateway, env.username, env.password )

url = "jdbc:db2://${env.hostname}:51000/bigsql:sslConnection=true;sslTrustStoreLocation=./truststore.jks;Password=mypassword;"

db = [ url:url, user:env.username, password:env.password, driver:'com.ibm.db2.jcc.DB2Driver']

sql = Sql.newInstance(db.url, db.user, db.password, db.driver)

def tableName = "test_${new Date().getTime()}"

def hdfsFolder = "/tmp/${tableName}/"

// Drop table - note this will delete the 'external' folder in hdfs 
println '\nDrop table'
sql.execute """
  drop table if exists $tableName
""".toString()

// Put data file in HDFS
dataFile = """1|Pierre
2|Chris
3|Jane
"""

println "\nCopy data to tmp"
rmData = Hdfs.rm( session ).file( hdfsFolder ).now()
putData = Hdfs.put( session ).text( dataFile ).to( "${hdfsFolder}/data.csv").now()

// Create table
println '\nCreate external table on ' + hdfsFolder
sql.execute """
  create external hadoop table if not exists $tableName (id int, name varchar(30))
  row format delimited
  fields terminated by '|'
  lines terminated by '\\n'
  location '${hdfsFolder}'
""".toString()

// Select from table
def select_statement = "select id, name from $tableName".toString()
def rows = sql.rows(select_statement)
def rowCount = rows.size()

println "\nSelect from table: $select_statement"
rows.each { row ->
  println "ID: $row.id NAME: $row.name"
}

println "\nDrop external table"
sql.execute """
  drop table if exists $tableName
""".toString()

println "\nCheck file still present in hdfs location"
println Hdfs.ls( session ).dir( hdfsFolder ).now().string

println "\nDelete file ${hdfsFolder}"
rmData = Hdfs.rm( session ).file( hdfsFolder ).recursive().now()

// verify we had three rows
assert rowCount == 3

session.shutdown()

println "\n>> Query from Hdfs was successful."
