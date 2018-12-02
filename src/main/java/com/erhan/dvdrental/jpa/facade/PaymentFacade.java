package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Payment;
import java.util.Date;
import java.util.List;
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
    
    public List<Payment> findByPaymentDate(Date paymentDate) {
        return em.createNamedQuery(Payment.FIND_BY_PAYMENT_DATE)
                .setParameter("paymentDate", paymentDate).getResultList();
    }
}
