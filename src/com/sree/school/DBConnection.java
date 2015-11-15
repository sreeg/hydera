package com.sree.school;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

	private static Connection conn;
	private static String databaseServer;
	private static String databaseName;
	private static String databaseUserName;
	private static String databaseServerPort;
	private static String databaseJDBCDriver;
	private static String databaseUserPassword;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (conn == null) {
			readProperties();
			
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "sreedhar");
			StringBuilder connectionString = new StringBuilder();
			connectionString.append("jdbc:mysql://");
			connectionString.append(databaseServer);
			connectionString.append(":");
			connectionString.append(databaseServerPort);
			connectionString.append("/");
			connectionString.append(databaseName);

			Class.forName(databaseJDBCDriver);
			conn = DriverManager.getConnection(connectionString.toString(), databaseUserName, databaseUserPassword);

		}
		return conn;
	}

	private static void readProperties() {
		Properties props = new Properties();
		try {
			FileInputStream in = new FileInputStream("C://db.properties");
			props.load(in);
			in.close();

			databaseServer = props.getProperty("db.server");
			databaseServerPort = props.getProperty("db.port");
			databaseName = props.getProperty("db.name");
			databaseUserName = props.getProperty("db.user");
			databaseUserPassword = props.getProperty("db.password");
			databaseJDBCDriver = props.getProperty("db.jdbc.driver");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
