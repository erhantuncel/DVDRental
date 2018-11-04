package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Address;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AddressFacade extends AbstractFacade<Address> {
    
    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public AddressFacade() {
        super(Address.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
