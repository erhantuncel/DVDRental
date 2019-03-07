package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Address;
import com.erhan.dvdrental.entities.City;
import com.erhan.dvdrental.entities.Country;
import com.erhan.dvdrental.entities.Staff;
import com.erhan.dvdrental.entities.Store;
import com.erhan.dvdrental.entities.UserGroup;
import com.erhan.dvdrental.jpa.facade.AddressFacade;
import com.erhan.dvdrental.jpa.facade.StaffFacade;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named("createStaffBacking")
@ViewScoped
public class CreateStaffBacking implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private StaffFacade staffFacade;
    
    @EJB
    AddressFacade addressFacade;
    
    List<City> cityListBasedOnCountry = new VirtualFlow.ArrayLinkedList<>();
    
    private String firstName;
    private String lastName;
    private String email;
    private String address1;
    private String address2;
    private Country country;
    private City city;
    private String district;
    private String postalCode;
    private String phone;
    private String userName;
    private String password1;
    private String password2;
    private byte[] picture;
    private Store store;
    
    
    
    public CreateStaffBacking() {
    }
    
    public StaffFacade getStaffFacade() {
        return staffFacade;
    }

    public void setStaffFacade(StaffFacade staffFacade) {
        this.staffFacade = staffFacade;
    }

    public AddressFacade getAddressFacade() {
        return addressFacade;
    }

    public void setAddressFacade(AddressFacade addressFacade) {
        this.addressFacade = addressFacade;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public List<City> getCityListBasedOnCountry() {
        return cityListBasedOnCountry;
    }

    public void setCityListBasedOnCountry(List<City> cityListBasedOnCountry) {
        this.cityListBasedOnCountry = cityListBasedOnCountry;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    
    public void countrySelectOneMenuValueChanged(ValueChangeEvent event) {
        Country country = (Country) event.getNewValue();
        setCityListBasedOnCountry(country.getCityList());
    }
    
    public void fileUploadListener(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        setPicture(file.getContents());
    }
    
    public StreamedContent getPictureAsStreamedContent() {
        DefaultStreamedContent pictureStream = null;
        if(picture != null) {
            pictureStream = new DefaultStreamedContent(new ByteArrayInputStream(picture),"image/png");
        }
        return pictureStream;
    }
    
    public void showSelectStoreDialog() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("width", 500);
        options.put("height", 400);
        options.put("contentHeight", "100%");
        options.put("contentWidth", "100%");
        options.put("closeOnEscape", true);
        
        PrimeFaces.current().dialog().openDynamic("/views/admin/selectStoreDialogContent", options, null);
    }
    
    public void selectStoreFromDialog(Store store) {
        PrimeFaces.current().dialog().closeDynamic(store);
    }
    
    public void handleReturnStore(SelectEvent event) {
        Store selectedStore = (Store) event.getObject();
        setStore(selectedStore);
    }
    
    public String createStaff() {
//        System.out.println("firstName = " + getFirstName());
//        System.out.println("lastName = " + getLastName());
//        System.out.println("Country = " + getCountry().getCountry());
//        System.out.println("City = " + getCity().getCity());
//        System.out.println("Username = " + getUserName());
//        System.out.println("Password 1 = " + getPassword1());
//        System.out.println("Password 2 = " + getPassword2());
//        System.out.println("Picture lenght = " + getPicture().length);

        System.out.println("Address creating is started.");
        Address address = new Address(getAddress1(), getDistrict(), getPhone(), new Date());
        address.setAddress2(getAddress2());
        address.setCity(getCity());
        address.setPostalCode(getPostalCode());
        getAddressFacade().create(address);
        
        System.out.println("Staff creating is started.");
        Staff staff = new Staff(getFirstName(), getLastName(), true, getUserName(), new Date());
        staff.setEmail(getEmail());
        staff.setPicture(getPicture());
        if(getPassword1().equals(getPassword2())) {
            staff.setPassword(getPassword1());
        } else {
            System.out.println("İki şifre uyuşmuyor.");
        }
        staff.setUserGroup(UserGroup.EMPLOYEE_GROUP);
        staff.setStoreId(store);
        staff.setAddress(address);
        getStaffFacade().create(staff);        
        return "staffs";
    }
}
