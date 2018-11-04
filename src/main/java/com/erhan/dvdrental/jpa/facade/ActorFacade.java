package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Actor;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ActorFacade extends AbstractFacade<Actor> {
    
    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public ActorFacade() {
        super(Actor.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
