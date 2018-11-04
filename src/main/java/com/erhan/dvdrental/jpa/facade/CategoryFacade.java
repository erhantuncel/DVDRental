package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Category;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CategoryFacade  extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public CategoryFacade() {
        super(Category.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
