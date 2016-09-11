package com.sree.hydera;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@ManagedBean(name = "bookHistoryBean")
@ViewScoped
public class BookHistoryBean implements Serializable
{

  private static final long serialVersionUID = 1L;
  private Book book;

  public BookHistoryBean()
  {
    book = new Book();
  }

  public Book getBook()
  {
    return book;
  }

  public void issueBook()
  {
    try
    {
      Connection conn = DBConnection.getConnection();

      PreparedStatement ps = conn.prepareStatement("insert into bookhistory (book_id, status, issuedate, receiveddate, comments, student_id) values (?,?,?,?,?,?)");
      ps.setString(1, book.getBookid());
      ps.setString(2, book.getIssuestatus());
      ps.setDate(3, new java.sql.Date(book.getIssueddate().getTime()));
      ps.setDate(4, new java.sql.Date(book.getReceiveddate().getTime()));
      ps.setString(5, book.getIssuecomments());
      ps.setString(6, book.getBookid());
      ps.executeUpdate();

      ps.close();

      FacesMessage message = new FacesMessage("Book Issued", "");
      FacesContext.getCurrentInstance().addMessage(null, message);

    }
    catch (Exception e)
    {
      System.out.println("Exception." + e.getMessage());
    }
    book = new Book();

  }

  public void setBook(Book book)
  {
    this.book = book;
  }
}