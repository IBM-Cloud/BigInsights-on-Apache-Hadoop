import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.*
import groovy.json.JsonSlurper

class AmbariClient {

	def url, user, password
	RESTClient client
	String name
	
    AmbariClient(url,user,password) {
		
		this.url = url
		this.user = user
		this.password = password
		
		this.client = new RESTClient( this.url )
		this.client.ignoreSSLIssues()

		this.client.headers['Authorization'] = 'Basic ' + "${this.user}:${this.password}".getBytes('iso-8859-1').encodeBase64()
		this.client.headers['X-Requested-By'] = 'ambari'
		
		def resp = this.client.get( path : 'api/v1/clusters' )

		// Parse output to JSON
		def jsonSlurper = new JsonSlurper()
		def object = jsonSlurper.parseText(resp.data.text)

		// Set Cluster Name
		this.name = object.items.Clusters[0].cluster_name
	}
	
	List getServices() {
	
		def resp = this.client.get( path : 'api/v1/clusters/' + this.name + '/services' )

		def jsonSlurper = new JsonSlurper()
		def object = jsonSlurper.parseText(resp.data.text)

	    def service_list = []
	    object.items.each { service ->
	        service_list << service.ServiceInfo.service_name
	    }
		
		return service_list	 
	}
	
	Integer startService(service) {
		
		def body = '{"RequestInfo": {"context" :"Start ' + service + ' via REST"}, "Body": {"ServiceInfo": {"state": "STARTED"}}}'

		def resp = this.client.put( path: 'api/v1/clusters/' + this.name + '/services/' + service, body: body, requestContentType: URLENC )

		// Get request id
		def jsonSlurper = new JsonSlurper()
		def object = jsonSlurper.parseText(resp.data.text)
		def requestId = object.Requests.id

		return requestId
	}

	Integer stopService(service) {
		
		def body = '{"RequestInfo": {"context" :"Stop ' + service + ' via REST"}, "Body": {"ServiceInfo": {"state": "INSTALLED"}}}'

		def resp = this.client.put( path: 'api/v1/clusters/' + this.name + '/services/' + service, body: body, requestContentType: URLENC )

		// Get request id
		def jsonSlurper = new JsonSlurper()
		def object = jsonSlurper.parseText(resp.data.text)
		def requestId = object.Requests.id

		return requestId
	}
	
	Integer checkRequest(id) {
		
		def jsonSlurper = new JsonSlurper()		  
		
		def status = "IN_PROGRESS"
		def count = 0
		def failed_tasks = 0
		
		while( status == "IN_PROGRESS" && count++ < 60 ) {
		  sleep( 1000 )
		  def resp = this.client.get( path : 'api/v1/clusters/' + this.name + '/requests/' + id )
		  def object = jsonSlurper.parseText(resp.data.text)
		  status = object.Requests.request_status
          failed_tasks = object.Requests.failed_task_count
		}

		return failed_tasks
	}
	
	String getState(service) {

	   def resp = this.client.get( path : 'api/v1/clusters/' + this.name + "/services/" + service )

	   def jsonSlurper = new JsonSlurper()		  
	   def object = jsonSlurper.parseText(resp.data.text)	   
	   def state = object.ServiceInfo.state
	   
	   return state		
	}
	
}

