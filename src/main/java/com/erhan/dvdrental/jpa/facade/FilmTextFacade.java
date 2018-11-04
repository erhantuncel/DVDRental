package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.FilmText;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class FilmTextFacade extends AbstractFacade<FilmText> {

    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public FilmTextFacade() {
        super(FilmText.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
