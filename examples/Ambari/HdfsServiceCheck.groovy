import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*
import groovy.json.JsonSlurper

env = System.getenv()

ambariUrl = env.ambariUrl
ambariUser = env.ambariUsername
ambariPassword = env.ambariPassword

def client = new RESTClient( ambariUrl )
client.ignoreSSLIssues()

client.headers['Authorization'] = 'Basic ' + "$ambariUser:$ambariPassword".getBytes('iso-8859-1').encodeBase64()
client.headers['X-Requested-By'] = 'ambari'

println ""

def jsonSlurper = new JsonSlurper()
def user_privileges = []

// Identify user privileges
def respPrivileges = client.get( path : 'api/v1/users/' + ambariUser + '/privileges')
def privileges = jsonSlurper.parseText(respPrivileges.data.text)

privileges.items.each { items ->
    def respPrivilege = client.get( path: 'api/v1/users/' + ambariUser + '/privileges/' + items.PrivilegeInfo.privilege_id )
    def privilege = jsonSlurper.parseText(respPrivilege.data.text)
    user_privileges << privilege.PrivilegeInfo.permission_name
}

// Check if you have CLUSTER.OPERATE
if ( !user_privileges.contains('CLUSTER.OPERATE') ) {
    println "You don't have CLUSTER OPERATE privilege to run service check."
    println "You have the following privileges: ${user_privileges}"
    System.exit(0)
}

// Make REST call to get cluster
print "Get cluster info: "
def resp = client.get( path : 'api/v1/clusters' )
println resp.status

assert resp.status == 200  // HTTP response code; 404 means not found, etc.

// Parse output to JSON
def object = jsonSlurper.parseText(resp.data.text)

// Get Cluster Name
clusterName = object.items.Clusters[0].cluster_name
println "\nCluster name is: " + clusterName

def postBody = '{ "RequestInfo" : { "context":"HDFS Service Check", "command":"HDFS_SERVICE_CHECK" }, "Requests/resource_filters": [{ "service_name":"HDFS" }] }'

// Make Ambari REST call to run service check
print "\nCall Ambari Service Check: "

def respServiceCheck = client.post( path: 'api/v1/clusters/' + clusterName + '/requests', body: postBody, requestContentType: URLENC )

println respServiceCheck.status

assert respServiceCheck.status == 202  // HTTP response code; 404 means not found, etc.

// Get request id
def object_request = jsonSlurper.parseText(respServiceCheck.data.text)

def requestId = object_request.Requests.id
println "\nRequest id is: " + requestId
println "Request href is: ${object_request.href}"

// Now let's check the status of the request
// status should be COMPLETED with 0 failed tasks
println "\nPolling up to 60s for job completion..."
status = "IN_PROGRESS";
count = 0;
while( status == "IN_PROGRESS" && count++ < 60 ) {
  sleep( 1000 )
  def check_request = client.get( path : 'api/v1/clusters/' + clusterName + '/requests/' + requestId )
  request_status = jsonSlurper.parseText(check_request.data.text)
  status = request_status.Requests.request_status
  print "."; System.out.flush();
}
println ""

println "Job status  is: ${request_status.Requests.request_status}"
println "Job failed tasks is: ${request_status.Requests.failed_task_count}"

