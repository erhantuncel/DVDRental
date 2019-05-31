package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Customer;
import com.erhan.dvdrental.jpa.facade.CustomerFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("customerController")
@SessionScoped
public class CustomerController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CustomerFacade customerFacade;
    private List<Customer> customerList = null;
    
    public CustomerController() {
    }

    public CustomerFacade getCustomerFacade() {
        return customerFacade;
    }

    public void setCustomerFacade(CustomerFacade customerFacade) {
        this.customerFacade = customerFacade;
    }

    public List<Customer> getCustomerList() {
        return getCustomerFacade().findAll();
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public Customer getCustomer(Short id) {
        return getCustomerFacade().find(id);
    }
}
