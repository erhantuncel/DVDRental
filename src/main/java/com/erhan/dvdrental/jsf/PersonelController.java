package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Staff;
import com.erhan.dvdrental.jpa.facade.StaffFacade;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("personelController")
@SessionScoped
public class PersonelController implements Serializable {

    @EJB
    private StaffFacade staffFacade;
    private Staff selected;
    private StreamedContent personelPicture;
    
    public PersonelController() {
    }

    public StaffFacade getStaffFacade() {
        return staffFacade;
    }
    
    public Staff getSelected() {
        return selected;
    }

    public void setSelected(Staff selected) {
        this.selected = selected;
    }

    public StreamedContent getPersonelPicture() {
        DefaultStreamedContent image = null;
        if(selected.getPicture() != null) {
            image = new DefaultStreamedContent(new ByteArrayInputStream(selected.getPicture()), "image/png");
            return image;
        } else {
            return image;
        }
    }
    
}
