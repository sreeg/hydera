package com.sree.school.Filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sree.school.DBConnection;
import com.sree.school.LoginBean;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthFilter implements Filter {

	public AuthFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("In Init of AuthFilter");
		readProperties();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// check whether session variable is set
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession ses = req.getSession(false);

		try {
			// allow user to proccede if url is login.xhtml or user logged in or
			// user is accessing any page in //public folder
			String reqURI = req.getRequestURI();
			if (reqURI.indexOf("/login.xhtml") >= 0 || (ses != null && ses.getAttribute("username") != null)
					|| reqURI.indexOf("/public/") >= 0 || reqURI.contains("javax.faces.resource"))
				chain.doFilter(request, response);
			else // user didn't log in but asking for a page that is not allowed
					// so take user to login page
				res.sendRedirect(req.getContextPath() + "/login.xhtml"); // Anonymous
																			// user.
																			// Redirect
																			// to
																			// login
																			// page
		} catch (Throwable t) {
			System.out.println(t.getMessage());
			ses.putValue("errorDetails", t.toString().trim());
			res.sendRedirect(req.getContextPath() + "/error.xhtml");
		}
	} // doFilter

	@Override
	public void destroy() {

	}

	private static void readProperties() {
		Properties props = new Properties();
		try {
			File configDir = new File(System.getProperty("catalina.base"), "conf");
			File configFile = new File(configDir, "db.properties");

			FileInputStream in = new FileInputStream(configFile);
			props.load(in);
			in.close();

			DBConnection.databaseServer = props.getProperty("db.server");
			DBConnection.databaseServerPort = props.getProperty("db.port");
			DBConnection.databaseName = props.getProperty("db.name");
			DBConnection.databaseUserName = props.getProperty("db.user");
			DBConnection.databaseUserPassword = props.getProperty("db.password");
			DBConnection.databaseJDBCDriver = props.getProperty("db.jdbc.driver");

			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);

			LoginBean.schoolname = props.getProperty("app.schoolname");
			LoginBean.shortdescription = props.getProperty("app.shortdescription");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}