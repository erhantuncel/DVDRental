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
public class InventoryIT {
    
    public static final Logger logger = Logger.getLogger(InventoryIT.class.getName());
    
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
        Inventory inventory = new Inventory(new Date());
        Film foundFilmId10 = em.find(Film.class, Short.valueOf("10"));
        inventory.setFilm(foundFilmId10);
        Store foundStoreId1 = em.find(Store.class, Short.valueOf("1"));
        inventory.setStore(foundStoreId1);
        Rental rental = new Rental(new Date(), new Date());
        Customer foundCustomerId10 = em.find(Customer.class, Short.valueOf("10"));
        rental.setCustomer(foundCustomerId10);
        Staff foundStaffId1 = em.find(Staff.class, Short.valueOf("1"));
        rental.setStaff(foundStaffId1);
        inventory.addRental(rental);
        em.persist(inventory);
        transaction.commit();
        
        transaction.begin();
        Inventory foundInventoryNewlyCreated = em.find(Inventory.class, inventory.getInventoryId());
        assertNotNull(foundInventoryNewlyCreated);
        assertEquals(foundInventoryNewlyCreated.getInventoryId(), inventory.getInventoryId());
        assertEquals(foundInventoryNewlyCreated.getRentalList().size(), 1);
        assertEquals(foundInventoryNewlyCreated.getRentalList().get(0), rental);
        transaction.commit();
        logger.log(Level.SEVERE, "InventoryIT-testPersist passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        Inventory foundInventoryId10 = em.find(Inventory.class, 10);
        assertNotNull(foundInventoryId10);
        assertEquals(foundInventoryId10.getInventoryId(), new Integer(10));
        transaction.commit();
        logger.log(Level.SEVERE, "InventoryIT-testFind passed.");
    } 
    
    @Test
    public void testMerge() {
        transaction.begin();
        Inventory foundInventoryId10 = em.find(Inventory.class, 10);
        Film foundFilmId20 = em.find(Film.class, Short.valueOf("20"));
        foundInventoryId10.setFilm(foundFilmId20);
        em.merge(foundInventoryId10);
        transaction.commit();
        
        transaction.begin();
        Inventory foundInventoryId10Updated = em.find(Inventory.class, 10);
        assertEquals(foundInventoryId10Updated.getFilm(), foundFilmId20);
        transaction.commit();
        logger.log(Level.SEVERE, "InventoryIT-testMerge passed.");
    }
    
    @Test
    public void testRemove() {
        transaction.begin();
        Inventory foundInventoryId10 = em.find(Inventory.class, 10);
        em.remove(foundInventoryId10);
        transaction.commit();
        
        transaction.begin();
        Inventory foundInventoryIdAfterRemove = em.find(Inventory.class, 10);
        assertNull(foundInventoryIdAfterRemove);
        transaction.commit();
        logger.log(Level.SEVERE, "InventoryIT-testRemove passed.");
    }
}