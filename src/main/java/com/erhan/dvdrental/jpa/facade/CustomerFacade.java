package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Customer;
import java.util.List;
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
 
    public List<Customer> findByFirstName(String firstName) {
        return em.createNamedQuery(Customer.FIND_BY_FIRST_NAME)
                .setParameter("firstName", firstName).getResultList();
    }
    
    public List<Customer> findByLastName(String lastName) {
        return em.createNamedQuery(Customer.FIND_BY_LAST_NAME)
                .setParameter("lastName", lastName).getResultList();
    }
    
    public List<Customer> findByEmail(String email) {
        return em.createNamedQuery(Customer.FIND_BY_EMAIL)
                .setParameter("email", email).getResultList();
    }
}
