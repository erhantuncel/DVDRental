package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Address;
import com.erhan.dvdrental.entities.City;
import com.erhan.dvdrental.entities.Country;
import com.erhan.dvdrental.entities.Customer;
import com.erhan.dvdrental.entities.Staff;
import com.erhan.dvdrental.entities.Store;
import com.erhan.dvdrental.jpa.facade.AddressFacade;
import com.erhan.dvdrental.jpa.facade.CustomerFacade;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("createCustomerBacking")
@ViewScoped
public class CreateCustomerBacking implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private CustomerFacade customerFacade;
    
    @EJB
    private AddressFacade addressFacade;
    
    List<City> cityListBasedOnCountry = new VirtualFlow.ArrayLinkedList<>();
    
    private Short customerId;
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

    public CreateCustomerBacking() {
    }

    public CustomerFacade getCustomerFacade() {
        return customerFacade;
    }

    public void setCustomerFacade(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    public AddressFacade getAddressFacade() {
        return addressFacade;
    }

    public void setAddressFacade(AddressFacade addressFacade) {
        this.addressFacade = addressFacade;
    }

    public List<City> getCityListBasedOnCountry() {
        return cityListBasedOnCountry;
    }

    public void setCityListBasedOnCountry(List<City> cityListBasedOnCountry) {
        this.cityListBasedOnCountry = cityListBasedOnCountry;
    }

    public Short getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Short customerId) {
        this.customerId = customerId;
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
    
    public void countrySelectOneMenuValueChanged(ValueChangeEvent event) {
        Country country = (Country) event.getNewValue();
        setCityListBasedOnCountry(country.getCityList());
    }
    
    public String createCustomer() {
        System.out.println("Address creating is started.");
        Address customerAddress = new Address(getAddress1(), getDistrict(), getPhone(), new Date());
        customerAddress.setAddress2(getAddress2());
        customerAddress.setCity(getCity());
        customerAddress.setPostalCode(getPostalCode());
        getAddressFacade().create(customerAddress);
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Staff loggedInStaff = (Staff) sessionMap.get("Staff");
        Store customerStore = loggedInStaff.getStoreId();
        
        System.out.println("Customer creating is started.");
        Customer customer = new Customer(getFirstName(), getLastName(), true, new Date());
        customer.setEmail(getEmail());
        customer.setLastUpdate(new Date());
        customer.setAddress(customerAddress);
        customer.setStore(customerStore);
        customerFacade.create(customer);
        setCustomerId(customer.getCustomerId());
        return "customers";
    }

}
