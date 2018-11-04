package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Language;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LanguageFacade extends AbstractFacade<Language> {

    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public LanguageFacade() {
        super(Language.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
