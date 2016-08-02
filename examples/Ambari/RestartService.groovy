// Restart service uses AmbariClient class which is defined in src/main/groovy/
// This class can be extended to do more action with ambari client.

env = System.getenv()

ambariUrl = env.ambariUrl
ambariUser = env.ambariUsername
ambariPassword = env.ambariPassword
service = env.service_name

def cluster = new AmbariClient( ambariUrl, ambariUser, ambariPassword )

def state = cluster.getState(service)

if ( state == "STARTED" ) {
    println "\nStop service ${service}:"
    id = cluster.stopService(service)
    println "Check request ${id} : " + cluster.checkRequest(id)

    println "\nStart service ${service}: "
    id = cluster.startService(service)
    println "Check request ${id} : " + cluster.checkRequest(id)
} else {
    println "\nStart service: "
    id = cluster.startService(service)
    println "Check request ${id} : " + cluster.checkRequest(id)
}
