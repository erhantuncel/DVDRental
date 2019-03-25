package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.City;
import com.erhan.dvdrental.entities.Country;
import com.erhan.dvdrental.entities.Customer;
import com.erhan.dvdrental.jpa.facade.CustomerFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("updateCustomerBacking")
@ViewScoped
public class UpdateCustomerBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CustomerFacade customerFacade;

    private Customer selectedCustomer;
    private List<City> cityListBasedOnCountry = new ArrayList<>();
    
    public CustomerFacade getCustomerFacade() {
        return customerFacade;
    }

    public void setCustomerFacade(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
        cityListBasedOnCountry = selectedCustomer.getAddress().getCity().getCountry().getCityList();
    }

    public List<City> getCityListBasedOnCountry() {
        return cityListBasedOnCountry;
    }

    public void setCityListBasedOnCountry(List<City> cityListBasedOnCountry) {
        this.cityListBasedOnCountry = cityListBasedOnCountry;
    }
 
    public void countrySelectOneMenuValueChanged(ValueChangeEvent event) {
        Country country = (Country) event.getNewValue();
        setCityListBasedOnCountry(country.getCityList());
    }
    
    public void hideUpdateCustomerDialog() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("customers.xhtml");
    }
    
    public void updateCustomer() {
        selectedCustomer.setLastUpdate(new Date());
        getCustomerFacade().edit(selectedCustomer);
    }
}
