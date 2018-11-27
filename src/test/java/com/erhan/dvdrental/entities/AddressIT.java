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
public class AddressIT {
    
    Logger logger = Logger.getLogger(AddressIT.class.getName());
    
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
        this.transaction = em.getTransaction();
    }
    
    @After
    public void tearDown() {
       
    }

    @Test
    public void testPersist() {
        City cityId10 = em.find(City.class, Short.valueOf("10"));
        Address address1 = new Address("Borazanlar M.", "Merkez", "03458764477", new Date());
        address1.setCity(cityId10);
        transaction.begin();
        em.persist(address1);
        transaction.commit();
        logger.log(Level.SEVERE, "Newly created address id = {0}", address1.getAddressId());
        
        transaction.begin();
        Address foundAddressNewlyCreated = em.find(Address.class, address1.getAddressId());
        assertNotNull(foundAddressNewlyCreated);
        assertEquals(foundAddressNewlyCreated.getAddressId(), address1.getAddressId());
        transaction.commit();
        logger.log(Level.SEVERE, "AddressIT-testPersist passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        Address foundAddressId10 = em.find(Address.class, Short.valueOf("10"));
        assertNotNull(foundAddressId10);
        assertEquals(foundAddressId10.getAddressId(), Short.valueOf("10"));
        transaction.commit();
        logger.log(Level.SEVERE, "AddressIT-testFind passed.");
    }
    
    @Test
    public void testMerge() {
        transaction.begin();
        Address foundAddressId10 = em.find(Address.class, Short.valueOf("10"));
        foundAddressId10.setAddress2("Nazif Onbaşı Sok.");
        Staff staffId1 = em.find(Staff.class, Short.valueOf("1"));
        foundAddressId10.addStaff(staffId1);
        Store storeId1 = em.find(Store.class, Short.valueOf("1"));
        foundAddressId10.addStore(storeId1);
        int customerCount = foundAddressId10.getCustomerList().size();
        Customer customerId1 = em.find(Customer.class, Short.valueOf("1"));
        foundAddressId10.addCustomer(customerId1);
        em.merge(foundAddressId10);
        transaction.commit();
        
        transaction.begin();
        Address foundAddressId10Updated = em.find(Address.class, Short.valueOf("10"));
        Staff staffId1Updated = em.find(Staff.class, Short.valueOf("1"));
        Store storeId1Updated = em.find(Store.class, Short.valueOf("1"));
        Customer customerId1Updated = em.find(Customer.class, Short.valueOf("1"));
        assertEquals(foundAddressId10Updated.getAddress2(), "Nazif Onbaşı Sok.");
        assertEquals(foundAddressId10Updated.getStaffList().get(0), staffId1);
        assertEquals(foundAddressId10Updated.getStoreList().get(0), storeId1);
        assertEquals(foundAddressId10Updated.getCustomerList().size(), customerCount+1);
        assertEquals(staffId1Updated.getAddress(), foundAddressId10Updated);
        assertEquals(storeId1Updated.getAddress(), foundAddressId10Updated);
        assertEquals(customerId1Updated.getAddress(), foundAddressId10Updated);
        transaction.commit();
        logger.log(Level.SEVERE, "AddressIT-testMerge passed.");
    }
    
    @Test
    public void testRemove() {
        transaction.begin();
        Address foundAddressId10 = em.find(Address.class, Short.valueOf("10"));
        em.remove(foundAddressId10);
        transaction.commit();
        
        transaction.begin();
        Address foundAddressId10AfterRemove = em.find(Address.class, Short.valueOf("10"));
        assertNull(foundAddressId10AfterRemove);
        transaction.commit();
        logger.log(Level.SEVERE, "AddressIT-testRemove passed.");
    }
}
