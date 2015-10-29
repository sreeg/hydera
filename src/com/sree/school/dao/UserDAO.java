package com.sree.school.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.sree.school.Util;

public class UserDAO {
	private static Connection conn;

	public static String login(String user, String password) throws SQLException {
		PreparedStatement ps = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "root", "sreedhar");
			ps = conn.prepareStatement(
					"select username, password, staffid, lastlogindatetime from userinfo where username= ? and password= ? ");
			ps.setString(1, user);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) // found
			{

				HttpSession session = Util.getSession();
				session.setAttribute("username", rs.getString("username"));
				session.setAttribute("staffid", rs.getString("staffid"));
				session.setAttribute("lastlogindatettime", rs.getTimestamp("lastlogindatetime"));
				return "staff";
			} else
				return "login";
		} catch (Exception ex) {
			System.out.println("Error in login() -->" + ex.getMessage());
			return "login";
		} finally {
			ps = conn.prepareStatement(
					"update userinfo set lastlogindatetime = now() where username= "+ "'" + user + "'");
			ps.executeUpdate();
			conn.close();
		}
	}
}