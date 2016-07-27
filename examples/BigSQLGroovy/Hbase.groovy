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

env = System.getenv()

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
println 'Create hbase table'
sql.execute """
  create hbase table if not exists $tableName (c1 integer, c2 integer, c3 integer) column mapping (key mapped by (c1), cf1:cq1 mapped by (c2, c3))
""".toString()

// Insert data
println 'Insert into hbase table'
sql.execute """
   insert into $tableName values (1,1,1), (2,2,2)
""".toString()

// Select from table
def select_statment = "select c1, c2, c3 from $tableName"
println "Select from table: $select_statment"
sql.eachRow(select_statment.toString()) { row ->
  println "$row.c1 : $row.c2 : $row.c3"
}

sql.execute """
  drop table if exists $tableName
""".toString()

println "\n>> BIGSQL/HBASE test was successful."
