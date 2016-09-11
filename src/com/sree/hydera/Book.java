package com.sree.hydera;

import java.io.Serializable;
import java.util.Date;

import org.primefaces.model.UploadedFile;

public class Book implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String index;
  private String bookid;
  private String title;
  private String author;
  private String publishers;
  private String category;
  private String imagename;
  private String issuedto;
  private Date issueddate;
  private Date receiveddate;
  private String issuestatus;
  private String issuecomments;

  private double price;

  private String bookimage;

  private UploadedFile file;

  private boolean isarchived;

  public String getAuthor()
  {
    return author;
  }

  public String getBookid()
  {
    return bookid;
  }

  public String getCategory()
  {
    return category;
  }

  public UploadedFile getFile()
  {
    return file;
  }

  public String getImagename()
  {
    return imagename;
  }

  public String getIndex()
  {
    return index;
  }

  public String getIssuecomments()
  {
    return issuecomments;
  }

  public Date getIssueddate()
  {
    return issueddate;
  }

  public String getIssuedto()
  {
    return issuedto;
  }

  public String getIssuestatus()
  {
    return issuestatus;
  }

  public double getPrice()
  {
    return price;
  }

  public String getPublishers()
  {
    return publishers;
  }

  public Date getReceiveddate()
  {
    return receiveddate;
  }

  public String getTitle()
  {
    return title;
  }

  public boolean isIsarchived()
  {
    return isarchived;
  }

  public void setAuthor(String author)
  {
    this.author = author;
  }

  public void setBookid(String bookid)
  {
    this.bookid = bookid;
  }

  public void setCategory(String category)
  {
    this.category = category;
  }

  public void setFile(UploadedFile file)
  {
    this.file = file;
  }

  public void setImagename(String imagename)
  {
    this.imagename = imagename;
  }

  public void setIndex(String index)
  {
    this.index = index;
  }

  public void setIsarchived(boolean isarchived)
  {
    this.isarchived = isarchived;
  }

  public void setIssuecomments(String issuecomments)
  {
    this.issuecomments = issuecomments;
  }

  public void setIssueddate(Date issueddate)
  {
    this.issueddate = issueddate;
  }

  public void setIssuedto(String issuedto)
  {
    this.issuedto = issuedto;
  }

  public void setIssuestatus(String issuestatus)
  {
    this.issuestatus = issuestatus;
  }

  public void setPrice(double price)
  {
    this.price = price;
  }

  public void setPublishers(String publishers)
  {
    this.publishers = publishers;
  }

  public void setReceiveddate(Date receiveddate)
  {
    this.receiveddate = receiveddate;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

}
