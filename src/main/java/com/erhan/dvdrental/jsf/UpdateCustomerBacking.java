package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Customer;
import com.erhan.dvdrental.jpa.facade.CustomerFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("updateCustomerBacking")
@ViewScoped
public class UpdateCustomerBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CustomerFacade customerFacade;

    private Customer selectedCustomer;
    
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
    }
    
    
}
