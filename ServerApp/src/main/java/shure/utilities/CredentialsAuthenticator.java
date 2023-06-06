package shure.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

@Component
public class CredentialsAuthenticator {
	// JDBC driver name and database URL
	final String JDBC_DRIVER = "org.h2.Driver";
	final String DB_URL = "jdbc:h2:file:../H2 Database/data";

	// Database credentials
	final String USER = "shure";
	final String PASS = "";

	public boolean verifyCredentials(String hashedCredentials) {

		Connection connection = null;
		Statement statement = null;
		boolean response = false;

		try {
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// Open a connection
			connection = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			statement = connection.createStatement();
			String sql = "SELECT * FROM MANAGER FETCH FIRST ROW ONLY";
			ResultSet resultSet = statement.executeQuery(sql);

			resultSet.next();
			response = hashedCredentials.equals(resultSet.getString("HASH"));
			resultSet.close();

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return response;
	}

}
