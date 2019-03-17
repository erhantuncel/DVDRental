package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Staff;
import com.erhan.dvdrental.jpa.facade.StaffFacade;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class StaffController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private StaffFacade staffFacade;
    private List<Staff> staffList = null;
    
    public StaffFacade getStaffFacade() {
        return staffFacade;
    }

    public void setStaffFacade(StaffFacade staffFacade) {
        this.staffFacade = staffFacade;
    }

    public List<Staff> getStaffList() {
        return this.staffList;
    }
    
    public StreamedContent getStaffPictureAsStreamedContent(byte[] picture) {
        DefaultStreamedContent imageStream = null;
        if(picture != null) {
            imageStream = new DefaultStreamedContent(new ByteArrayInputStream(picture), "image/png");
        }
        return imageStream;
    }
}
