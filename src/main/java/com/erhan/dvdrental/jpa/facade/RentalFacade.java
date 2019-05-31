package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Customer;
import com.erhan.dvdrental.entities.Inventory;
import com.erhan.dvdrental.entities.Inventory_;
import com.erhan.dvdrental.entities.Rental;
import com.erhan.dvdrental.entities.Rental_;
import com.erhan.dvdrental.entities.Store;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class RentalFacade extends AbstractFacade<Rental> {

    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public RentalFacade() {
        super(Rental.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Rental> findByRentalDate(Date rentalDate) {
        return em.createNamedQuery(Rental.FIND_BY_RENTAL_DATE)
                .setParameter("rentalDate", rentalDate).getResultList();
    }
    
    public List<Rental> findByReturnDate(Date returnDate) {
        return em.createNamedQuery(Rental.FIND_BY_RETURN_DATE)
                .setParameter("returnDate", returnDate).getResultList();
    }
    
    public List<Rental> findByReturnDateIsNull() {
        return em.createNamedQuery(Rental.FIND_BY_RETURN_DATE_IS_NULL)
                .getResultList();
    }
    
    public List<Rental> findReturnDateIsNullByStore(Store store) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rental> cq = cb.createQuery(Rental.class);
        Root<Rental> rental = cq.from(Rental.class);
        Join<Rental, Inventory> inventory = rental.join(Rental_.inventory);
        Predicate predicateForStore = cb.equal(inventory.get(Inventory_.store), store);
        Predicate predicateForReturnDateIsNull = cb.isNull(rental.get("returnDate"));
        cq.select(rental).where(predicateForStore, predicateForReturnDateIsNull);
        TypedQuery<Rental> typedQuery = em.createQuery(cq);
        return typedQuery.getResultList();        
    }
    
    public List<Rental> findReturnDateIsNullByCustomer(Customer customer) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rental> cq = cb.createQuery(Rental.class);
        Root<Rental> rental = cq.from(Rental.class);
        Predicate predicateForReturnDateIsNull = cb.isNull(rental.get("returnDate"));
        Predicate predicateForCustomer = cb.equal(rental.get("customer"), customer);
        cq.select(rental).where(predicateForCustomer, predicateForReturnDateIsNull);
        TypedQuery<Rental> typedQuery = em.createQuery(cq);
        return typedQuery.getResultList();
    }
    
    public List<Rental> findLastSixtyRentals() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Rental> query = criteriaBuilder.createQuery(Rental.class);
        Root<Rental> root = query.from(Rental.class);
        query.select(root);
        query.orderBy(criteriaBuilder.desc(root.get("rentalDate")));
        TypedQuery<Rental> q = em.createQuery(query);
        q.setMaxResults(60);
        return q.getResultList();
    }
    
    public List<Rental> findByInventory(Inventory inventory) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rental> cq= cb.createQuery(Rental.class);
        Root<Rental> root = cq.from(Rental.class);
        cq.select(root).where(cb.equal(root.get("inventory"), inventory));
        TypedQuery<Rental> typedQuery = em.createQuery(cq);
        return typedQuery.getResultList();
    }
    
}
