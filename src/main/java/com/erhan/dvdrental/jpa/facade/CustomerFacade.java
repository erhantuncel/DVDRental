package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Customer;
import com.erhan.dvdrental.entities.Customer_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
    
    public List<Customer> findByFirstNameContains(String prefix) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> root = cq.from(Customer.class);
        cq.select(root).where(cb.like(cb.lower(root.get(Customer_.firstName)), "%" + prefix + "%"));
        TypedQuery<Customer> typedQuery = em.createQuery(cq);
        return typedQuery.getResultList();
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
