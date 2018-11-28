/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhan.dvdrental.entities;

import java.util.Calendar;
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
public class RentalIT {
    
    public static final Logger logger = Logger.getLogger(RentalIT.class.getName());
    
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
        transaction.commit();
        
        transaction.begin();
        Rental foundRentalNewlyCreated = em.find(Rental.class, rental.getRentalId());
        assertNotNull(foundRentalNewlyCreated);
        assertEquals(foundRentalNewlyCreated.getRentalId(), rental.getRentalId());
        transaction.commit();
        logger.log(Level.SEVERE, "RentalIT-testPersist passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        Rental foundRentalId10 = em.find(Rental.class, 10);
        assertNotNull(foundRentalId10);
        assertEquals(foundRentalId10.getRentalId(), new Integer(10));
        transaction.commit();
        logger.log(Level.SEVERE, "RentalIT-testFind passed.");
    }
    
    @Test
    public void testMerge() {
        transaction.begin();
        Rental foundRentalId10 = em.find(Rental.class, 10);
        Calendar calendar = Calendar.getInstance();
        Date returnDate = calendar.getTime();
        foundRentalId10.setReturnDate(returnDate);
        em.merge(foundRentalId10);
        transaction.commit();
        
        transaction.begin();
        Rental foundRentalId10Updated = em.find(Rental.class, 10);
        assertEquals(foundRentalId10Updated.getReturnDate(), returnDate);
        transaction.commit();
        logger.log(Level.SEVERE, "RentalIT-testMerge passed.");
    }
    
    @Test
    public void testRemove() {
        transaction.begin();
        Rental foundRentalId10 = em.find(Rental.class, 10);
        em.remove(foundRentalId10);
        transaction.commit();
        
        transaction.begin();
        Rental foundRentalId10AfterRemove = em.find(Rental.class, 10);
        assertNull(foundRentalId10AfterRemove);
        transaction.commit();
        logger.log(Level.SEVERE, "RentalIT-testRemove passed.");
    }
}
