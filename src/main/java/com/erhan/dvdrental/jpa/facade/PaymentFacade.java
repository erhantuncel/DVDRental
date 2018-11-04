package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Payment;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PaymentFacade extends AbstractFacade<Payment> {

    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public PaymentFacade() {
        super(Payment.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
