package com.sree.hydera;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "tableBean")
@SessionScoped

public class TableBean
{
  private String imageID;
  private String imageName;

  private String imageLength;

  Connection MySQLcon = null;

  Statement stmt = null;

  PreparedStatement ps;
  ResultSet rs;

  public List<TableBean> getAllImage()
  {
    List<TableBean> imageInfo = new ArrayList<TableBean>();
    try
    {
      Connection con = DBConnection.getConnection();

      stmt = con.createStatement();
      String strSql = "select image_id,Image_name from upload_image order by image_id ";
      // System.err.println("*select all***" + strSql);
      rs = stmt.executeQuery(strSql);
      while (rs.next())
      {
        TableBean tbl = new TableBean();
        tbl.setImageID(rs.getString("image_id"));
        tbl.setImageName(rs.getString("Image_name"));
        imageInfo.add(tbl);
      }
    }
    catch (SQLException e)
    {
      System.out.println("Exception in getAllImage::" + e.getMessage());
    }
    catch (ClassNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return imageInfo;
  }

  public String getImageID()
  {
    return imageID;
  }

  public String getImageLength()
  {
    return imageLength;
  }

  public String getImageName()
  {
    return imageName;
  }

  public void setImageID(String imageID)
  {
    this.imageID = imageID;
  }

  public void setImageLength(String imageLength)
  {
    this.imageLength = imageLength;
  }

  public void setImageName(String imageName)
  {
    this.imageName = imageName;
  }
}