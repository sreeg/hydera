package com.sree.school;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static Connection conn;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (conn == null) {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "sreedhar");
		}
		return conn;
	}

}
