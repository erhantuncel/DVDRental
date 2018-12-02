/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Payment;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author erhan
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentFacadeTest {

    private static final Logger LOG = Logger.getLogger(PaymentFacadeTest.class.getName());
    
    private static final Date PAYMENT_DATE = new Date();
    
    @InjectMocks
    PaymentFacade paymentFacade;
    
    @Mock
    EntityManager em;
    
    @Test
    public void testFindByPaymentDate() {
        List<Payment> paymentList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(paymentList);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Payment.FIND_BY_PAYMENT_DATE)).thenReturn(mockedQuery);
        paymentList = this.paymentFacade.findByPaymentDate(PAYMENT_DATE);
        assertThat(paymentList, is(not(nullValue())));
        LOG.log(Level.SEVERE, "PaymentFacadeTest-testFindByPaymentDate passed");
    }
    
    
}
