package com.sree.health.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {
	public static String login(String user, String password) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			// con = Database.getConnection();
			// ps = con.prepareStatement("select user, pass from userinfo where
			// user= ? and pass= ? ");
			// ps.setString(1, user);
			// ps.setString(2, password);

			// ResultSet rs = ps.executeQuery();
			// if (rs.next()) // found
			// {
			// System.out.println(rs.getString("user"));
			// return true;
			// } else {
			// return false;
			// }
			if ("sree".equals(user))
				return "customer";
			else if ("dhar".equals(user))
				return "doctor";
			else
				return "login";
		} catch (Exception ex) {
			System.out.println("Error in login() -->" + ex.getMessage());
			return "login";
		} finally {
			// Database.close(con);
		}
	}
}