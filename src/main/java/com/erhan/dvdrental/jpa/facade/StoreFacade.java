package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Store;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class StoreFacade extends AbstractFacade<Store> {

    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public StoreFacade() {
        super(Store.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
}
