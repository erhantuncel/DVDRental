package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Staff;
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
}
