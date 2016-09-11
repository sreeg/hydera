package com.sree.hydera;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "bookView")
@ViewScoped

public class BookView
{
  private List<Book> bookList;
  private String searchText;
  private String listtype;
  private Book selectedBook;

  public BookView()
  {
    FacesContext context = FacesContext.getCurrentInstance();
    Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
    listtype = paramMap.get("listtype");

    bookList = new ArrayList<>();
    getAllBooks();

  }

  public List<BookView> getAllBooks()
  {

    List<BookView> imageInfo = new ArrayList<BookView>();
    try
    {
      Connection con = DBConnection.getConnection();

      Statement stmt = con.createStatement();
      String strSql = "select id, bookindex, title, author, publishers, price, category, imagename from books order by bookindex ";
      ResultSet rs = stmt.executeQuery(strSql);
      while (rs.next())
      {
        Book b = new Book();
        b.setBookid(rs.getString("id"));
        b.setIndex("" + rs.getInt("bookindex"));
        b.setTitle(rs.getString("title"));
        b.setAuthor(rs.getString("author"));
        b.setPublishers(rs.getString("publishers"));
        b.setPrice(rs.getDouble("price"));
        b.setCategory(rs.getString("category"));
        b.setImagename(rs.getString("imagename"));
        bookList.add(b);
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

  public List<Book> getBookList()
  {
    return bookList;
  }

  public void getBooksByKey(ActionEvent actionEvent)
  {
    if (searchText == null)
    {
      searchText = "";
    }

    bookList = new ArrayList<>();
    try
    {
      Connection con = DBConnection.getConnection();

      Statement stmt = con.createStatement();
      String strSql = "select id, bookindex, title, author, publishers, price, category, imagename from books where " + "title like '%" + searchText + "%' or " + "author like '%"
          + searchText + "%' or publishers like '%" + searchText + "%' order by bookindex ";
      ResultSet rs = stmt.executeQuery(strSql);
      while (rs.next())
      {
        Book b = new Book();
        b.setBookid(rs.getString("id"));
        b.setIndex("" + rs.getInt("bookindex"));
        b.setTitle(rs.getString("title"));
        b.setAuthor(rs.getString("author"));
        b.setPublishers(rs.getString("publishers"));
        b.setPrice(rs.getDouble("price"));
        b.setCategory(rs.getString("category"));
        b.setImagename(rs.getString("imagename"));
        bookList.add(b);
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
  }

  public String getListtype()
  {
    return listtype;
  }

  public String getSearchText()
  {
    return searchText;
  }

  public Book getSelectedBook()
  {
    return selectedBook;
  }

  public void setBookList(List<Book> bookList)
  {
    this.bookList = bookList;
  }

  public void setListtype(String listtype)
  {
    this.listtype = listtype;
  }

  public void setSearchText(String searchText)
  {
    this.searchText = searchText;
  }

  public void setSelectedBook(Book selectedBook)
  {
    this.selectedBook = selectedBook;
  }

}