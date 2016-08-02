import groovyx.net.http.RESTClient
import groovy.json.JsonSlurper

env = System.getenv()

ambariUrl = env.ambariUrl
ambariUser = env.ambariUsername
ambariPassword = env.ambariPassword

println "\nAmbari URL: " + ambariUrl
def client = new RESTClient( ambariUrl )
client.ignoreSSLIssues()

client.headers['Authorization'] = 'Basic ' + "$ambariUser:$ambariPassword".getBytes('iso-8859-1').encodeBase64()
client.headers['X-Requested-By'] = 'ambari'

// Make REST call to get clusters
def resp = client.get( path : 'api/v1/clusters' )

assert resp.status == 200  // HTTP response code; 404 means not found, etc.

// Parse output to JSON
def jsonSlurper = new JsonSlurper()
def object = jsonSlurper.parseText(resp.data.text)

// Get Cluster Name
clusterName = object.items.Clusters[0].cluster_name
println "\nCluster name is: " + clusterName

// Make REST to get services
def respServices = client.get( path : 'api/v1/clusters/' + clusterName + '/services' )

assert respServices.status == 200  // HTTP response code; 404 means not found, etc.

// Get services
def object_services = jsonSlurper.parseText(respServices.data.text)

println "\nServices are: " 
object_services.items.eachWithIndex { serviceName,  index ->

   // Get service name
   service = serviceName.ServiceInfo.service_name
   print "service ${index}: ${service}"
   
   // Get service info
   def respInfo = client.get( path : 'api/v1/clusters/' + clusterName + "/services/${service}" )

  // Get service status - should be STARTED or INSTALLED (client)
  def object_health = jsonSlurper.parseText(respInfo.data.text)
  print " [${object_health.ServiceInfo.state}]"

  // Check service alerts
  critAlert = object_health.alerts_summary.CRITICAL
  warnAlert = object_health.alerts_summary.WARNING

  if ( critAlert == 0 && warnAlert == 0) {
     println " - ALL GOOD (no critical or warning alerts)"
  } else {
     println " - ATTENTION ( ${critAlert} CRITICAL and ${warnAlert} WARNING aleerts"
  }

} 

