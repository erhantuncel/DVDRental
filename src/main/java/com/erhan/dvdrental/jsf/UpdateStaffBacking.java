package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.City;
import com.erhan.dvdrental.entities.Country;
import com.erhan.dvdrental.entities.Staff;
import com.erhan.dvdrental.jpa.facade.StaffFacade;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
//import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named("updateStaffBacking")
@ViewScoped
public class UpdateStaffBacking implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private StaffFacade staffFacade;
    private List<City> cityListBasedOnCountry = new ArrayList<>();
    private byte[] imageFromFileUpload = null;
    
    private Staff selectedStaff;
    
    public StaffFacade getStaffFacade() {
        return staffFacade;
    }

    public void setStaffFacade(StaffFacade staffFacade) {
        this.staffFacade = staffFacade;
    }

    public Staff getSelectedStaff() {
        return selectedStaff;
    }

    public void setSelectedStaff(Staff selectedStaff) {
        this.selectedStaff = selectedStaff;
        cityListBasedOnCountry = selectedStaff.getAddress().getCity().getCountry().getCityList();
    }

    public List<City> getCityListBasedOnCountry() {
        return cityListBasedOnCountry;
    }

    public void setCityListBasedOnCountry(List<City> cityListBasedOnCountry) {
        this.cityListBasedOnCountry = cityListBasedOnCountry;
    }

    public byte[] getImageFromFileUpload() {
        return imageFromFileUpload;
    }

    public void setImageFromFileUpload(byte[] imageFromFileUpload) {
        this.imageFromFileUpload = imageFromFileUpload;
    }
    
    public void countrySelectOneMenuValueChanged(ValueChangeEvent event) {
        Country country = (Country) event.getNewValue();
        setCityListBasedOnCountry(country.getCityList());
    }
    
    public void updateStaffFileUploadListener(FileUploadEvent event) {
        UploadedFile uploadedImage = event.getFile();
        setImageFromFileUpload(uploadedImage.getContents());
    }
    
    public void removeSelectedStaffPicture() {
        if(selectedStaff != null && selectedStaff.getPicture() != null) {
            selectedStaff.setPicture(null);
        }
        if(imageFromFileUpload != null) {
            setImageFromFileUpload(null);
        }
    }
    
    public DefaultStreamedContent getImageFromUploadAsStreamedContent() {
        DefaultStreamedContent imageStream = null;
        if(imageFromFileUpload != null) {
            imageStream = new DefaultStreamedContent(new ByteArrayInputStream(imageFromFileUpload), "image/png");
        }
        return imageStream;
    }
    
    public DefaultStreamedContent getSelectedStaffPictureAsStreamedContent() {
        DefaultStreamedContent imageStream = null;
        if(selectedStaff.getPicture() != null) {
            imageStream = new DefaultStreamedContent(new ByteArrayInputStream(selectedStaff.getPicture()), "image/png");
        }
        return imageStream;
    }
    
    public String updateStaff() {
        if(imageFromFileUpload != null) {
            selectedStaff.setPicture(imageFromFileUpload);
            setImageFromFileUpload(null);
        }
        selectedStaff.setLastUpdate(new Date());
        getStaffFacade().edit(selectedStaff);
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage("actionResult", new FacesMessage(FacesMessage.SEVERITY_INFO, 
                selectedStaff.getFirstName() + " " + selectedStaff.getLastName() + " güncellendi.", 
                null));
        return "staffs?faces-redirect=true";
    }
    
    public void hideUpdateStaffDialog() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("staffs.xhtml");
    }
}
