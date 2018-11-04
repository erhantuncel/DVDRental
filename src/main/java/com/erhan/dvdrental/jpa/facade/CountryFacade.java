package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Country;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CountryFacade extends AbstractFacade<Country> {

    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public CountryFacade() {
        super(Country.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
