package com.sree.hydera.Filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.faces.application.FacesMessage;
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

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.primefaces.model.DefaultStreamedContent;

import com.sree.hydera.DBConnection;
import com.sree.hydera.SystemSettingsBean;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthFilter implements Filter
{

  private static void readProperties()
  {
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setPassword("sree"); // could be got from web, env variable...
    Properties props = new EncryptableProperties(encryptor);

    try
    {
      File configDir = new File(System.getProperty("catalina.base"), "conf");
      File configFile = new File(configDir, "dbhydera.properties");

      if (!configFile.exists() || configFile.isDirectory())
      {
        System.out.println("JDBC_SERVER : " + System.getProperty("JDBC_SERVER"));

        DBConnection.databaseServer = System.getProperty("JDBC_SERVER");
        DBConnection.databaseServerPort = System.getProperty("JDBC_SERVER_PORT");
        DBConnection.databaseName = System.getProperty("JDBC_SERVER_DBNAME");
        DBConnection.databaseUserName = System.getProperty("JDBC_SERVER_USER");
        DBConnection.databaseUserPassword = System.getProperty("JDBC_SERVER_PASSWORD");
        DBConnection.databaseJDBCDriver = System.getProperty("JDBC_DRIVER");

        SystemSettingsBean.schoolname = System.getProperty("APP_DISPLAY_NAME");
        SystemSettingsBean.shortdescription = System.getProperty("APP_DISPLAY_NAME_DESC");
        SystemSettingsBean.disableemail = !Boolean.getBoolean(System.getProperty("APP_PARAM_DISABLE_EMAIL"));
        SystemSettingsBean.peoplemanagement = "true".equalsIgnoreCase(System.getProperty("APP_PARAM_PM"));
        SystemSettingsBean.salarymanagement = "true".equalsIgnoreCase(System.getProperty("APP_PARAM_SM"));
        SystemSettingsBean.feemanagement = "true".equalsIgnoreCase(System.getProperty("APP_PARAM_FM"));
        SystemSettingsBean.charts = "true".equalsIgnoreCase(System.getProperty("APP_PARAM_CHARTS"));
        SystemSettingsBean.reports = "true".equalsIgnoreCase(System.getProperty("APP_PARAM_REPORTS"));
        return;
      }

      FileInputStream in = new FileInputStream(configFile);
      props.load(in);
      in.close();

      DBConnection.databaseServer = props.getProperty("db.server");
      DBConnection.databaseServerPort = props.getProperty("db.port");
      DBConnection.databaseName = props.getProperty("db.name");
      DBConnection.databaseUserName = props.getProperty("db.user");
      DBConnection.databaseUserPassword = props.getProperty("db.password");
      DBConnection.databaseJDBCDriver = props.getProperty("db.jdbc.driver");

      String property;
      SystemSettingsBean.schoolname = props.getProperty("app.schoolname");
      SystemSettingsBean.shortdescription = props.getProperty("app.shortdescription");
      //SystemSettingsBean.logo = new DefaultStreamedContent(new FileInputStream(new File(System.getProperty("catalina.base"), "conf/resources/images/logo.gif")), "image/gif");
      // ArrayList<StreamedContent> a = new ArrayList<>();
      // for (int i = 0; i < 5; i++)
      // {
      // try
      // {
      // a.add(new DefaultStreamedContent(new FileInputStream(new
      // File(System.getProperty("catalina.base"),
      // "conf/resources/images/STUDENT_" + (i + 1) + ".png")), "image/png"));
      // }
      // catch (Exception e)
      // {
      // continue;
      // }
      // }
      // SystemSettingsBean ssb = new SystemSettingsBean();
      // ssb.setPicsList(a);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Something went wront when reading properties", "");
      FacesContext.getCurrentInstance().addMessage(null, msg);
    }

  }

  public AuthFilter()
  {
  }

  @Override
  public void destroy()
  {
  }

  @SuppressWarnings("deprecation")
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    // check whether session variable is set
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    HttpSession ses = req.getSession(false);

    try
    {
      // allow user to proceed if url is login.xhtml or user logged in or
      // user is accessing any page in //public folder
      String reqURI = req.getRequestURI();
      if (reqURI.indexOf("/login.xhtml") >= 0 || (ses != null && ses.getAttribute("username") != null) || reqURI.indexOf("/public/") >= 0
          || reqURI.contains("javax.faces.resource"))
        chain.doFilter(request, response);
      else if (reqURI.indexOf("/admin.xhtml") >= 0)
      {
        chain.doFilter(request, response);
      }
      else // user didn't log in but asking for a page that is not allowed
           // so take user to login page
        res.sendRedirect(req.getContextPath() + "/login.xhtml");
    }
    catch (Throwable t)
    {
      System.out.println(t.getMessage());
      ses.putValue("errorDetails", t.toString().trim());
      res.sendRedirect(req.getContextPath() + "/error.xhtml");
      // FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL,
      // t.toString().trim(), "");
      // FacesContext.getCurrentInstance().addMessage(null, msg);
    }
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException
  {
    readProperties();
  }
}