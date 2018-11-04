package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.City;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CityFacade extends AbstractFacade<City> {

    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public CityFacade() {
        super(City.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
