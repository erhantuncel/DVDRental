package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Customer;
import com.erhan.dvdrental.jpa.facade.CustomerFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;  

@Named("customerListBacking")
@ViewScoped
public class CustomerListBacking implements Serializable{

    private static final long serialVersionUID = 1L;

    @EJB
    private CustomerFacade customerFacade;
    private List<Customer> customerList = null;

    private List<Customer> filteredCustomers;
    
    public CustomerListBacking() {
    }
    
    @PostConstruct
    public void init() {
        if(customerList == null) {
            customerList = getCustomerFacade().findAll();
        }
    }
    
    public CustomerFacade getCustomerFacade() {
        return customerFacade;
    }

    public void setCustomerFacade(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public List<Customer> getFilteredCustomers() {
        return filteredCustomers;
    }

    public void setFilteredCustomers(List<Customer> filteredCustomers) {
        this.filteredCustomers = filteredCustomers;
    }
}