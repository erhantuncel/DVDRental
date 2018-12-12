package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.UserGroup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserGroupFacade extends AbstractFacade<UserGroup> {
    
    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public UserGroupFacade() {
        super(UserGroup.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
