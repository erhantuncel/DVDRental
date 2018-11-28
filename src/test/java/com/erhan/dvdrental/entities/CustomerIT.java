/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhan.dvdrental.entities;

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
public class CustomerIT {
    
    public static final Logger logger = Logger.getLogger(CustomerIT.class.getName());
    
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
    public void testCreate() {
        Address foundAddressId10 = em.find(Address.class, Short.valueOf("10"));
        Store foundStoreId1 = em.find(Store.class, Short.valueOf("1"));
        Customer customer = new Customer("Serhan", "TUNÇEL", true, new Date());
        customer.setAddress(foundAddressId10);
        customer.setStore(foundStoreId1);
        transaction.begin();
        em.persist(customer);
        transaction.commit();
        
        transaction.begin();
        Customer foundCustomerNewlyCreated = em.find(Customer.class, customer.getCustomerId());
        assertNotNull(foundCustomerNewlyCreated);
        assertEquals(foundCustomerNewlyCreated.getCustomerId(), customer.getCustomerId());
        transaction.commit();
        logger.log(Level.SEVERE, "CustomerIT-testPersist passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        Customer foundCustomerId10 = em.find(Customer.class, Short.valueOf("10"));
        assertNotNull(foundCustomerId10);
        assertEquals(foundCustomerId10.getCustomerId(), Short.valueOf("10"));
        transaction.commit();
        logger.log(Level.SEVERE, "CustomerIT-testFind passed.");
    }
    
    @Test
    public void testMerge() {
        transaction.begin();
        Customer foundCustomerId10 = em.find(Customer.class, Short.valueOf("10"));
        foundCustomerId10.setFirstName("Erhan");
        foundCustomerId10.setActive(false);
        em.merge(foundCustomerId10);
        transaction.commit();
        
        transaction.begin();
        Customer foundCustomerId10Updated = em.find(Customer.class, Short.valueOf("10"));
        assertEquals(foundCustomerId10Updated.getActive(), false);
        assertEquals(foundCustomerId10Updated.getFirstName(), "Erhan");
        transaction.commit();
        logger.log(Level.SEVERE, "CustomerIT-testMerge passed.");
    }
}