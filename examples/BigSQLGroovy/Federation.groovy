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

println "[Federation.groovy] Connecting to Big SQL"
sql = Sql.newInstance(db.url, db.user, db.password, db.driver)

dash_user = env.dashUser
dash_password = env.dashPassword
dash_host = env.dashHost
dash_port = env.dashPort

// Create wrapper
println "[Federation.groovy] Create wrapper"
//sql.execute("DROP WRAPPER drda")
sql.execute("CREATE WRAPPER drda")

// Create server mapping
println "[Federation.groovy] Create server mapping"
sql.execute """
   CREATE SERVER DASHDB TYPE DB2/UDB VERSION 11.1
   WRAPPER DRDA
   AUTHORIZATION "${dash_user}" PASSWORD "${dash_password}"
   OPTIONS (HOST '${dash_host}', PORT '${dash_port}', DBNAME 'BLUDB', PASSWORD 'Y')
""".toString()

// Create user mapping
println "[Federation.groovy] Create user mapping"
sql.execute """
   CREATE USER MAPPING FOR ${db.user} SERVER DASHDB
   OPTIONS (REMOTE_AUTHID '${dash_user}', REMOTE_PASSWORD '${dash_password}')
""".toString()

// Create nickname
println "[Federation.groovy] Create nickname"
sql.execute("CREATE NICKNAME LANGUAGE FOR DASHDB.SAMPLES.LANGUAGE")

// Select from table
def select_statment = "select language_code, language_desc from language"
println "[Federation.groovy] Select from table: $select_statment"
sql.eachRow(select_statment.toString()) { row ->
  println "$row.language_code : $row.language_desc"
}

