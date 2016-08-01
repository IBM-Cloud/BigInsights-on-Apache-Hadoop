import java.sql.*;
import java.util.Map;
import java.util.Date;

public class Example {

	public static void main(String[] args) throws Exception {
		
		// truststore.jks is created by the build.gradle script
		
		final String connOptions = "ssl=true;sslTrustStore=./truststore.jks;trustStorePassword=mypassword;";
		
		// Hostname, Username and Password are passed in through the environment

		Map<String, String> env = System.getenv();

		final String db = String.format(
				"jdbc:hive2://%s:10000/default;%s", env.get("hostname"), connOptions
				);

		final String user = env.get("username");
		final String pwd  = env.get("password");


		Connection conn = null;
		Statement stmt = null;

		Class.forName("org.apache.hive.jdbc.HiveDriver");

		conn = DriverManager.getConnection(db, user, pwd);

		stmt = conn.createStatement();

        	String tmpTableName = String.format("%s_temp_%s",
                    env.get("username"),
                    new Date().getTime()
                    );

        	stmt.execute(
             	   String.format("create table %s ( id int, name string )", tmpTableName)
                );
		System.out.println("\n>> Create Table");
		
        	stmt.execute(
           	     String.format("drop table %s", tmpTableName)
                );
		System.out.println("\n>> Drop Table");
		
		stmt.close();
		conn.close();

		System.out.println("\n>> Connectivity test was successful.");
	}
}
