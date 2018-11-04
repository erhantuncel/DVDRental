package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Inventory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class InventoryFacade extends AbstractFacade<Inventory> {

    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public InventoryFacade() {
        super(Inventory.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
