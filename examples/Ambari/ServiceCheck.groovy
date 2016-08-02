import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*
import groovy.json.JsonSlurper

env = System.getenv()

ambariUrl = env.ambariUrl
ambariUser = env.ambariUsername
ambariPassword = env.ambariPassword
service_name = env.service_name

def client = new RESTClient( ambariUrl )
client.ignoreSSLIssues()

client.headers['Authorization'] = 'Basic ' + "$ambariUser:$ambariPassword".getBytes('iso-8859-1').encodeBase64()
client.headers['X-Requested-By'] = 'ambari'

println ""

// Make REST call to get cluster
print "Get cluster info: "
def resp = client.get( path : 'api/v1/clusters' )

println resp.status

assert resp.status == 200  // HTTP response code; 404 means not found, etc.

// Parse output to JSON
def jsonSlurper = new JsonSlurper()
def object = jsonSlurper.parseText(resp.data.text)

// Get Cluster Name
clusterName = object.items.Clusters[0].cluster_name
println "\nCluster name is: " + clusterName


// Define map of service to check and service check directive
// note: webhcat is commented out. Some issue need to investigate
def services = [
   'HDFS':'HDFS_SERVICE_CHECK',
   'YARN':'YARN_SERVICE_CHECK',
   'MAPREDUCE2':'MAPREDUCE2_SERVICE_CHECK',
   'HBASE':'HBASE_SERVICE_CHECK',
   'HIVE':'HIVE_SERVICE_CHECK',
   'BIGSQL':'BIGSQL_SERVICE_CHECK',
   'BIGR':'BIGR_SERVICE_CHECK',
//   'WEBHCAT':'WEBHCAT_SERVICE_CHECK',
   'PIG':'PIG_SERVICE_CHECK',
   'OOZIE':'OOZIE_SERVICE_CHECK',
   'ZOOKEEPER':'ZOOKEEPER_QUORUM_SERVICE_CHECK'
]

service_check = services[service_name]

assert service_check != null, "Make sure to provide correct SERVICE name. Valid values are HDFS, YARN, MAPREDUCE2, HIVE, PIG, OOZIE, ZOOKEEPER"

postBody = '{ "RequestInfo" : { "context":"'+ service_name + ' Service Check", "command":"' + service_check +'" }, "Requests/resource_filters": [{ "service_name":"' + service_name + '" }] }'

// Make Ambari REST call to run service check
println "\nCall Ambari Service Check for ${service_name}"

def respServiceCheck = client.post( path: 'api/v1/clusters/' + clusterName + '/requests', body: postBody, requestContentType: URLENC )

println respServiceCheck.status

assert respServiceCheck.status == 202  // HTTP response code; 404 means not found, etc.

// Get request id
def object_request = jsonSlurper.parseText(respServiceCheck.data.text)

def requestId = object_request.Requests.id
println "\nRequest id is: " + requestId

// Now let's check the status of the request
// status should be COMPLETED with 0 failed tasks
println "\nPolling up to 60s for job completion..."
status = "IN_PROGRESS";
count = 0;
while( status == "IN_PROGRESS" && count++ < 120 ) {
  sleep( 1000 )
  def check_request = client.get( path : 'api/v1/clusters/' + clusterName + '/requests/' + requestId )
  request_status = jsonSlurper.parseText(check_request.data.text)
  status = request_status.Requests.request_status
  print "."; System.out.flush();
}
println ""

println "Job status  is: ${request_status.Requests.request_status}"
println "Job failed tasks is: ${request_status.Requests.failed_task_count}"

assert request_status.Requests.failed_task_count == 0

println ">>> Service Check for ${service_name} passed!"
println ""

