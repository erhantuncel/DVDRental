package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.City;
import com.erhan.dvdrental.entities.Country;
import com.erhan.dvdrental.entities.Staff;
import com.erhan.dvdrental.entities.Store;
import com.erhan.dvdrental.entities.UserGroup;
import com.erhan.dvdrental.jpa.facade.StaffFacade;
import com.erhan.dvdrental.jpa.facade.StoreFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("updateStoreBacking")
@ViewScoped
public class UpdateStoreBacking implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private StoreFacade storeFacade;  
    
    @EJB
    private StaffFacade staffFacade;
    
    private List<City> cityListBasedOnCountry = new ArrayList<>();
    private List<Staff> staffListForManageStore = null;
    
    private Store selectedStore;
    private Staff selectedManager;

    public StoreFacade getStoreFacade() {
        return storeFacade;
    }
    
    public void setStoreFacade(StoreFacade storeFacade) {
        this.storeFacade = storeFacade;
    }

    public StaffFacade getStaffFacade() {
        return staffFacade;
    }

    public void setStaffFacade(StaffFacade staffFacade) {
        this.staffFacade = staffFacade;
    }

    public List<City> getCityListBasedOnCountry() {
        return cityListBasedOnCountry;
    }

    public List<Staff> getStaffListForManageStore() {
        return staffListForManageStore;
    }

    public void setStaffListForManageStore(List<Staff> staffListForManageStore) {
        this.staffListForManageStore = staffListForManageStore;
    }
    
    public void setCityListBasedOnCountry(List<City> cityListBasedOnCountry) {
        this.cityListBasedOnCountry = cityListBasedOnCountry;
    }

    public Store getSelectedStore() {
        return selectedStore;
    }

    public void setSelectedStore(Store selectedStore) {
        this.selectedStore = selectedStore;
        cityListBasedOnCountry = selectedStore.getAddress().getCity().getCountry().getCityList();
    }

    public Staff getSelectedManager() {
        return selectedManager;
    }

    public void setSelectedManager(Staff selectedManager) {
        this.selectedManager = selectedManager;
    }

    public void countrySelectOneMenuValueChanged(ValueChangeEvent event) {
        Country country = (Country) event.getNewValue();
        setCityListBasedOnCountry(country.getCityList());
    }
    
    public void hideUpdateStoreDialog() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("stores.xhtml");
    }

    public void loadStaffListForManage(ActionEvent event) {
        List<Staff> allStaffList = getStaffFacade().findAll();
        String currentUserName = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        staffListForManageStore = new ArrayList<>();
        selectedStore = (Store) event.getComponent().getAttributes().get("store");
        for(Staff staff : allStaffList) {
            if((!selectedStore.getManagerStaff().equals(staff)) && (!currentUserName.equalsIgnoreCase(staff.getUsername()))) {
               staffListForManageStore.add(staff);
            }
        }
    }
    
    public void updateStore() {
        selectedStore.setLastUpdate(new Date());
        getStoreFacade().edit(selectedStore);
    }
    
    public void updateManagerSelectedStore() {
        Staff oldManager = selectedStore.getManagerStaff();
        
        selectedManager.setUserGroup(UserGroup.ADMINISTRATOR_GROUP);
        staffFacade.edit(selectedManager);
        selectedStore.setManagerStaff(selectedManager);
        storeFacade.edit(selectedStore);
        
        oldManager.setStoreToManage(null);
        oldManager.setUserGroup(UserGroup.EMPLOYEE_GROUP);
        staffFacade.edit(oldManager);
    }
}
