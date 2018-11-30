package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Actor;
import java.util.List;
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

    public List<Actor> findByFirstName(String firstName) {
        return em.createNamedQuery(Actor.FIND_BY_FIRST_NAME)
                .setParameter("firstName", firstName).getResultList();
    }

    public List<Actor> findByLastName(String lastName) {
        return em.createNamedQuery(Actor.FIND_BY_LAST_NAME)
                .setParameter("lastName", lastName).getResultList();
    }

}
