package com.sree.hydera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "fileUploadBean")
@SessionScoped
public class FileUploadBean
{

  private UploadedFile file;

  public UploadedFile getFile()
  {
    return file;
  }

  public void handleFileUpload(FileUploadEvent event)
  {
    file = event.getFile();
    if (file != null)
    {
      try
      {
        File targetFolder = new File(System.getProperty("catalina.base"), "webapps/images");

        InputStream inputStream = file.getInputstream();
        OutputStream out = new FileOutputStream(new File(targetFolder, file.getFileName()));
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1)
        {
          out.write(bytes, 0, read);
        }
        inputStream.close();
        out.flush();
        out.close();
        FacesMessage message = new FacesMessage("Succesful " + file.getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }

  public void setFile(UploadedFile file)
  {
    this.file = file;
  }
}
