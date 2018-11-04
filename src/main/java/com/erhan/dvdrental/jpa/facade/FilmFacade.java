package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Film;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class FilmFacade extends AbstractFacade<Film> {
    
    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public FilmFacade() {
        super(Film.class);
    }

    @Override
    protected EntityManager getEntityManager() {
       return em;
    }
}
