package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Category;
import java.util.List;
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
    
    public List<Category> findByName(String name) {
        return em.createNamedQuery(Category.FIND_BY_NAME)
                .setParameter("name", name).getResultList();
    }
}
