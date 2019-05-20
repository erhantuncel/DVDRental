package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Rental;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
    
    public List<Rental> findLastFiveRentals() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Rental> query = criteriaBuilder.createQuery(Rental.class);
        Root<Rental> root = query.from(Rental.class);
        query.select(root);
        query.orderBy(criteriaBuilder.desc(root.get("rentalDate")));
        TypedQuery<Rental> q = em.createQuery(query);
        q.setMaxResults(5);
        return q.getResultList();
    }
}
