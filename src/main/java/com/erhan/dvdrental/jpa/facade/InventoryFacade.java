package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Inventory;
import com.erhan.dvdrental.entities.Store;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    
    public List<Inventory> findByIdAndStore(Integer inventoryId, Store store) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Inventory> cq = cb.createQuery(Inventory.class);
        Root<Inventory> inventory = cq.from(Inventory.class);
        Predicate predicateForInventoryId = cb.equal(inventory.get("inventoryId"), inventoryId);
        Predicate predicateForStore = cb.equal(inventory.get("store"), store);
        cq.select(inventory).where(predicateForInventoryId, predicateForStore);
        TypedQuery<Inventory> typedQuery = em.createQuery(cq);
        return typedQuery.getResultList();
    }
}
