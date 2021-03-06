package com.sree.hydera.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.sree.hydera.DBConnection;
import com.sree.hydera.Util;

public class UserDAO {
	private static Connection conn;

	public static String login(String user, String password) throws SQLException {
		PreparedStatement ps = null;
		try {
			conn = DBConnection.getConnection();
			ps = conn.prepareStatement(
					"select username, password, staffid, lastlogindatetime, issuperuser, firstname, lastname, gender from userinfo where username= ? and password= ? ");
			ps.setString(1, user);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) // found
			{
				HttpSession session = Util.getSession();
				session.setAttribute("username", rs.getString("username"));
				session.setAttribute("staffid", rs.getString("staffid"));
				session.setAttribute("lastlogindatettime", rs.getTimestamp("lastlogindatetime"));
				session.setAttribute("issuperuser", rs.getBoolean("issuperuser"));
				session.setAttribute("firstname", rs.getString("firstname"));
				session.setAttribute("lastname", rs.getString("lastname"));
				session.setAttribute("gender", rs.getString("gender"));
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
			//conn.close();
		}
	}
}