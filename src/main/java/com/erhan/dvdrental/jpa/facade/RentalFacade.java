package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Rental;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
