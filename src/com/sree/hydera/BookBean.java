package com.sree.hydera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.primefaces.model.UploadedFile;

@ManagedBean(name = "bookBean")
@ViewScoped
public class BookBean implements Serializable
{

  private static final long serialVersionUID = 1L;
  private Book book;

  public BookBean()
  {
    book = new Book();
  }

  public Book getBook()
  {
    return book;
  }

  public void setBook(Book book)
  {
    this.book = book;
  }

  public void upload()
  {

    UploadedFile file = getBook().getFile();

    try
    {
      Connection conn = DBConnection.getConnection();

      ResultSet rs1 = conn.createStatement().executeQuery("select * from books");

      int i = 0;
      while (rs1.next())
      {
        i++;
      }
      String bookId = "Book_" + (i + 1);
      String bookname = "";
      InputStream fin = null;
      fin = file.getInputstream();
      long size = file.getSize();

      PreparedStatement ps = conn.prepareStatement("insert into books (bookindex, title, author, publishers, category, price, image, id, imagename) values (?,?,?,?,?,?,?,?,?)");
      ps.setInt(1, (i + 1));
      ps.setString(2, book.getTitle());
      ps.setString(3, book.getAuthor());
      ps.setString(4, book.getPublishers());
      ps.setString(5, book.getCategory());
      ps.setDouble(6, book.getPrice());
      if (size != 0)
      {
        ps.setBinaryStream(7, fin, file.getSize());
        bookname = bookId + ".png";
      }
      else
      {
        ps.setBinaryStream(7, null);
        bookname = "no_book.png";
      }
      ps.setString(8, bookId);
      ps.setString(9, bookname);
      ps.executeUpdate();

      ps.close();

      File targetFolder = new File(System.getProperty("catalina.base"), "webapps/images/books");
      InputStream inputStream = file.getInputstream();
      OutputStream out = new FileOutputStream(new File(targetFolder, bookname));
      int read = 0;
      byte[] bytes = new byte[1024];

      while ((read = inputStream.read(bytes)) != -1)
      {
        out.write(bytes, 0, read);
      }
      inputStream.close();
      out.flush();
      out.close();
      FacesMessage message = new FacesMessage("Book added, Image " + file.getFileName() + ".png" + " is uploaded.");
      FacesContext.getCurrentInstance().addMessage(null, message);

    }
    catch (Exception e)
    {
      System.out.println("Exception-File Upload." + e.getMessage());
    }
    book = new Book();

  }
}