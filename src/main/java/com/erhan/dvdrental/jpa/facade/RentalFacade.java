package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Rental;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RentalFacade extends AbstractFacade<Rental> {

    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public RentalFacade() {
        super(Rental.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
