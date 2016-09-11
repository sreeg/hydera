package com.sree.school;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.extensions.component.masterdetail.SelectLevelEvent;

@ManagedBean (name = "selectLevelListener")
@RequestScoped  
public class SelectLevelListener {  
  
    private boolean errorOccured = false;  
  
    public int handleNavigation(SelectLevelEvent selectLevelEvent) {  
        if (errorOccured) {  
            return 2;  
        } else {  
            return selectLevelEvent.getNewLevel();  
        }  
    }  
  
    public void setErrorOccured(boolean errorOccured) {  
        this.errorOccured = errorOccured;  
    }  
}  