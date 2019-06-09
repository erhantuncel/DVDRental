package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Address;
import com.erhan.dvdrental.entities.City;
import com.erhan.dvdrental.entities.Country;
import com.erhan.dvdrental.entities.Staff;
import com.erhan.dvdrental.entities.Store;
import com.erhan.dvdrental.entities.UserGroup;
import com.erhan.dvdrental.jpa.facade.AddressFacade;
import com.erhan.dvdrental.jpa.facade.StaffFacade;
import com.erhan.dvdrental.jpa.facade.StoreFacade;
import com.erhan.dvdrental.jpa.facade.UserGroupFacade;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@Named("createStoreBacking")
@ViewScoped
public class CreateStoreBacking implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private StoreFacade storeFacade;
    
    @EJB
    private StaffFacade staffFacade;
    
    @EJB
    private AddressFacade addressFacade;
    
    @EJB
    private UserGroupFacade userGroupFacade;
    
    List<City> cityListBasedOnCountry = new VirtualFlow.ArrayLinkedList<>();
    List<Staff> staffListForStoreManager = null;
    
    private String address1;
    private String address2;
    private Country country;
    private City city;
    private String district;
    private String postalCode;
    private String phone;
    private Staff managerStaff;
    private String managerStaffAsString;

    public CreateStoreBacking() {
    }

    @PostConstruct
    public void init() {
        List<Staff> allStaff = staffFacade.findAll();
        staffListForStoreManager = new ArrayList<>();
        for(Staff staff : allStaff) {
            UserGroup staffUserGroup = userGroupFacade.find(staff.getUsername());
            if(staffUserGroup.getGroupname().equals(UserGroup.EMPLOYEE_GROUP)) {
                staffListForStoreManager.add(staff);
            }
        }
    }
    
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

    public UserGroupFacade getUserGroupFacade() {
        return userGroupFacade;
    }

    public void setUserGroupFacade(UserGroupFacade userGroupFacade) {
        this.userGroupFacade = userGroupFacade;
    }

    public AddressFacade getAddressFacade() {
        return addressFacade;
    }

    public void setAddressFacade(AddressFacade addressFacade) {
        this.addressFacade = addressFacade;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Staff getManagerStaff() {
        return managerStaff;
    }

    public void setManagerStaff(Staff managerStaff) {
        this.managerStaff = managerStaff;
    }

    public String getManagerStaffAsString() {
        return managerStaffAsString;
    }

    public void setManagerStaffAsString(String managerStaffAsString) {
        this.managerStaffAsString = managerStaffAsString;
    }

    public List<City> getCityListBasedOnCountry() {
        return cityListBasedOnCountry;
    }

    public void setCityListBasedOnCountry(List<City> cityListBasedOnCountry) {
        this.cityListBasedOnCountry = cityListBasedOnCountry;
    }

    public List<Staff> getStaffListForStoreManager() {
        return staffListForStoreManager;
    }

    public void setStaffListForStoreManager(List<Staff> staffListForStoreManager) {
        this.staffListForStoreManager = staffListForStoreManager;
    }
    
    public void countrySelectOneMenuValueChanged(ValueChangeEvent event) {
        Country country = (Country) event.getNewValue();
        setCityListBasedOnCountry(country.getCityList());
    }
    
    public void showSelectStaffDialog() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("width", 650);
        options.put("height", 300);
        options.put("contentHeight", "100%");
        options.put("contentWidth", "100%");
        options.put("closeOnEscape", true);
        
        PrimeFaces.current().dialog().openDynamic("/views/admin/selectStaffDialogContent", options, null);
    }
    
    public void selectStaffFromDialog(Staff staff) {
        PrimeFaces.current().dialog().closeDynamic(staff);
    }
    
    public void handleReturnStaff(SelectEvent event) {
        Staff selectedStaff = (Staff) event.getObject();
        setManagerStaff(selectedStaff);
        setManagerStaffAsString(managerStaff.getFirstName() + " " + managerStaff.getLastName());
    }
    
    public String createStore() {
        Address storeAddress = new Address(address1, district, phone, new Date());
        storeAddress.setAddress2(address2);
        storeAddress.setPostalCode(postalCode);
        storeAddress.setCity(city);
        getAddressFacade().create(storeAddress);
        
        Store newStore = new Store(new Date());
        newStore.setAddress(storeAddress);
        newStore.setManagerStaff(managerStaff);
        storeFacade.create(newStore);
        
        managerStaff.setUserGroup(UserGroup.ADMINISTRATOR_GROUP);
        staffFacade.edit(managerStaff);
        
        return "stores?faces-redirect=true";
    }
}
