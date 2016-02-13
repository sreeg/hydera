package com.sree.school;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{

  public static Connection conn;
  public static String databaseServer;
  public static String databaseName;
  public static String databaseUserName;
  public static String databaseServerPort;
  public static String databaseJDBCDriver;
  public static String databaseUserPassword;

  public static Connection getConnection() throws ClassNotFoundException, SQLException
  {
    if (conn == null)
    {
      // readProperties();

      // Class.forName("com.mysql.jdbc.Driver");
      // conn =
      // DriverManager.getConnection("jdbc:mysql://localhost:3306/school",
      // "root", "sreedhar");
      StringBuilder connectionString = new StringBuilder();
      connectionString.append("jdbc:mysql://");
      connectionString.append(databaseServer);
      connectionString.append(":");
      connectionString.append(databaseServerPort);
      connectionString.append("/");
      connectionString.append(databaseName);
      System.out.println("DATABASE URL : " + connectionString.toString());
      Class.forName(databaseJDBCDriver);
      conn = DriverManager.getConnection(connectionString.toString(), databaseUserName, databaseUserPassword);

    }
    return conn;
  }

}
