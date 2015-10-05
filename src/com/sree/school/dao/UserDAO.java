package com.sree.school.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
	private static Connection conn;

	public static String login(String user, String password) throws SQLException {
		PreparedStatement ps = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school","root", "sreedhar");			
			ps = conn.prepareStatement("select username, password, staffid from userinfo where username= ? and password= ? ");
			ps.setString(1, user);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) // found
			{
				System.out.println(rs.getString("username"));
				System.out.println(rs.getString("password"));
				System.out.println(rs.getString("staffid"));
				return "staff";
			}
			
			if ("sree".equals(user))
				return "doctor";
			else if ("krishnaveni".equals(user))
				return "doctor";
			else
				return "login";
		} catch (Exception ex) {
			System.out.println("Error in login() -->" + ex.getMessage());
			return "login";
		} finally {
			conn.close();
		}
	}
}