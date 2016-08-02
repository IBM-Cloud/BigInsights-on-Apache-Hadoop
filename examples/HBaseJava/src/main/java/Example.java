import java.util.Map;

import org.apache.hadoop.gateway.shell.Hadoop;
import org.apache.hadoop.gateway.shell.hbase.HBase;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Example {
		
   public static void main(String[] args) throws Exception {
    
	   Map<String, String> env = System.getenv();
	   
	   Hadoop session = Hadoop.login( env.get("gateway"), env.get("username"), env.get("password") );
				
	   System.out.println("System version : " + HBase.session(session).systemVersion().now().getString());

	   System.out.println("Cluster version : " + HBase.session(session).clusterVersion().now().getString());
			   
	   System.out.println("Status : " + HBase.session(session).status().now().getString());
	   
       session.shutdown(10, SECONDS);
   }
}
