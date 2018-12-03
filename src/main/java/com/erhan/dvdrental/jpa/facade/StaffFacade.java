package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Staff;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class StaffFacade extends AbstractFacade<Staff> {
    
    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public StaffFacade() {
        super(Staff.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Staff> findByFirstName(String firstName) {
        return em.createNamedQuery(Staff.FIND_BY_FIRST_NAME)
                .setParameter("firstName", firstName).getResultList();
    }
    
    public List<Staff> findByLastName(String lastName) {
        return em.createNamedQuery(Staff.FIND_BY_LAST_NAME)
                .setParameter("lastName", lastName).getResultList();
    }
    
    public List<Staff> findByEmail(String email) {
        return em.createNamedQuery(Staff.FIND_BY_EMAIL)
                .setParameter("email", email).getResultList();
    }
    
    public List<Staff> findByUserName(String userName) {
        return em.createNamedQuery(Staff.FIND_BY_USER_NAME)
                .setParameter("username", userName).getResultList();
    }
}