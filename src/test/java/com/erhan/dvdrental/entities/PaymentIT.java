/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhan.dvdrental.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author erhan
 */
public class PaymentIT {
    
    public static final Logger logger = Logger.getLogger(PaymentIT.class.getName());
    
    private EntityManager em;
    private EntityTransaction transaction;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("it");
        this.em = emf.createEntityManager();
        this.transaction = this.em.getTransaction();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testPersist() {
        transaction.begin();
        Rental rental = new Rental(new Date(), new Date());
        Inventory foundInventoryId10 = em.find(Inventory.class, 10);
        rental.setInventory(foundInventoryId10);
        Customer foundCustomerId10 = em.find(Customer.class, Short.valueOf("10"));
        rental.setCustomer(foundCustomerId10);
        Staff foundStaffId1 = em.find(Staff.class, Short.valueOf("1"));
        rental.setStaff(foundStaffId1);
        em.persist(rental);
        Payment payment = new Payment(new BigDecimal("4.56"), new Date());
        payment.setRental(rental);
        payment.setCustomer(foundCustomerId10);
        payment.setStaff(foundStaffId1);
        em.persist(payment);
        transaction.commit();
        
        transaction.begin();
        Payment foundPaymentNewlyCreated = em.find(Payment.class, payment.getPaymentId());
        assertNotNull(foundPaymentNewlyCreated);
        assertEquals(foundPaymentNewlyCreated.getPaymentId(), payment.getPaymentId());
        assertEquals(foundPaymentNewlyCreated.getRental(), rental);
        assertEquals(foundPaymentNewlyCreated.getCustomer(), foundCustomerId10);
        assertEquals(foundPaymentNewlyCreated.getStaff(), foundStaffId1);
        transaction.commit();
        logger.log(Level.SEVERE, "PaymentIT-testPersist passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        Payment foundPaymentId10 = em.find(Payment.class, Short.valueOf("10"));
        assertNotNull(foundPaymentId10);
        assertEquals(foundPaymentId10.getPaymentId(), Short.valueOf("10"));
        transaction.commit();
        logger.log(Level.SEVERE, "PaymentIT-testFind passed.");
    }
    
    @Test
    public void testMerge() {
        transaction.begin();
        Payment foundPaymentId10 = em.find(Payment.class, Short.valueOf("10"));
        foundPaymentId10.setAmount(new BigDecimal("20.34"));
        em.merge(foundPaymentId10);
        transaction.commit();
        
        transaction.begin();
        Payment foundPaymentId10Updated = em.find(Payment.class, Short.valueOf("10"));
        assertEquals(foundPaymentId10Updated.getAmount(), new BigDecimal("20.34"));
        transaction.commit();
        logger.log(Level.SEVERE, "PaymentIT-testMerge passed.");
    }
    
    @Test
    public void testRemove() {
        transaction.begin();
        Payment foundPaymentId10 = em.find(Payment.class, Short.valueOf("10"));
        em.remove(foundPaymentId10);
        transaction.commit();
        
        transaction.begin();
        Payment foundPaymentId10AfterRemove = em.find(Payment.class, Short.valueOf("10"));
        assertNull(foundPaymentId10AfterRemove);
        transaction.commit();
        logger.log(Level.SEVERE, "PaymentIT-testRemove passed.");
    }
}
