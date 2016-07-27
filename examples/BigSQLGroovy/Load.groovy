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

dataFile = """1|Pierre
2|Chris
3|Jane
"""

// Put data file in HDFS
println "Copy data to tmp"
rmData = Hdfs.rm( session ).file( "/tmp/data.csv" ).now()
putData = Hdfs.put( session ).text( dataFile ).to( "/tmp/data.csv" ).now()

url = "jdbc:db2://${env.hostname}:51000/bigsql:sslConnection=true;sslTrustStoreLocation=./truststore.jks;Password=mypassword;"

db = [ url:url, user:env.username, password:env.password, driver:'com.ibm.db2.jcc.DB2Driver']

sql = Sql.newInstance(db.url, db.user, db.password, db.driver)

def tableName = "test_${new Date().getTime()}"

// Drop table
println 'Drop table'
sql.execute """
  drop table if exists $tableName
""".toString()

// Create table
println 'Create table'
sql.execute """
  create hadoop table if not exists $tableName(id int, name varchar(30))
""".toString()

// Load data
println 'Load into table'
sql.execute """
   load hadoop using file url '/tmp/data.csv' with source properties ('field.delimiter'='|', 'ignore.extra.fields'='true') into table $tableName append
""".toString()

// Select from table
def select_statment = "select id, name from $tableName"
println "Select from table: $select_statment"
sql.eachRow(select_statment.toString()) { row ->
  println "ID: $row.id NAME: $row.name"
}

sql.execute """
  drop table if exists $tableName
""".toString()

println "\n>> Connectivity test was successful."
