package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public CustomerFacade() {
        super(Customer.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
