import java.sql.*;
import java.util.Map;

public class Example {

	public static void main(String[] args) throws Exception {
		
		// NOTE: truststore.jks is created by build.gradle
		
		final String connOptions = "sslConnection=true;sslTrustStoreLocation=./truststore.jks;Password=mypassword;";
		
		// Hostname, Username and Password are passed in through the environment

		Map<String, String> env = System.getenv();

		final String db = String.format(
				"jdbc:db2://%s:51000/bigsql:%s", env.get("hostname"), connOptions
				);

		final String user = env.get("username");
		final String pwd  = env.get("password");

		Connection conn = null;
		Statement stmt = null;

		Class.forName("com.ibm.db2.jcc.DB2Driver");

		conn = DriverManager.getConnection(db, user, pwd);

		stmt = conn.createStatement();
		String sql = "SELECT 99 as ID FROM SYSIBM.SYSDUMMY1";
		ResultSet rs = stmt.executeQuery(sql);
		
		rs.next(); // Go to first record
		
		// Check our query returned the expected result
		
		if (rs.getInt("ID") != 99) {
			throw new RuntimeException("Test failed attemping to connect to BigSql");
		}
		
		rs.close();
		stmt.close();
		conn.close();

		System.out.println("\n>> Connectivity test was successful.");
	}
}
