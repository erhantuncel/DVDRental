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
public class StaffIT {
    
    public static final Logger logger = Logger.getLogger(StaffIT.class.getName());
    
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
        Staff staff = new Staff("Erhan", "TUNÇEL", true, "erhan", new Date());
        Address foundAddressId20 = em.find(Address.class, Short.valueOf("20"));
        staff.setAddress(foundAddressId20);
        Store foundStoreId2 = em.find(Store.class, Short.valueOf("2"));
        staff.setStoreId(foundStoreId2);
        em.persist(staff);
        transaction.commit();
        
        transaction.begin();
        Staff foundStaffNewlyCreated = em.find(Staff.class, staff.getStaffId());
        assertNotNull(foundStaffNewlyCreated);
        assertEquals(foundStaffNewlyCreated.getStaffId(), staff.getStaffId());
        assertEquals(foundStaffNewlyCreated.getAddress(), foundAddressId20);
        assertEquals(foundStaffNewlyCreated.getStoreId(), foundStoreId2);
        transaction.commit();
        logger.log(Level.SEVERE, "StaffIT-testPersist passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        Staff foundStaffId2 = em.find(Staff.class, Short.valueOf("2"));
        assertNotNull(foundStaffId2);
        assertEquals(foundStaffId2.getStaffId(), Short.valueOf("2"));
        transaction.commit();
        logger.log(Level.SEVERE, "StaffIT-testFind passed.");
    }
    
    @Test
    public void testMerge() {
        transaction.begin();
        Staff foundStaffId2 = em.find(Staff.class, Short.valueOf("2"));
        foundStaffId2.setActive(false);
        em.merge(foundStaffId2);
        transaction.commit();
        
        transaction.begin();
        Staff foundStaffId2Updated = em.find(Staff.class, Short.valueOf("2"));
        assertEquals(foundStaffId2Updated.getActive(), false);
        transaction.commit();
        logger.log(Level.SEVERE, "StaffIT-testMerge passed.");
    }
    
}
