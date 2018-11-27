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
public class StoreIT {
    
    Logger logger = Logger.getLogger(StoreIT.class.getName());
    
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
        
        
        transaction.begin();
        City city = em.find(City.class, Short.valueOf("10"));
        Address storeAddress = new Address("Store Address", "Merkez", "03445876635", new Date());
        storeAddress.setCity(city);
        Address staffAddress = new Address("Staff Address", "Merkez", "03987768847", new Date());
        staffAddress.setCity(city);
        em.persist(storeAddress);
        em.persist(staffAddress);
        transaction.commit();
        logger.log(Level.SEVERE, "Store Address Id = {0}", storeAddress.getAddressId() );
        logger.log(Level.SEVERE, "Manager Staff Address Id = {0}", staffAddress.getAddressId() );
        
        Store storeId2 = em.find(Store.class, Short.valueOf("2"));
        Staff staff = new Staff("Erhan", "Tunçel", true, "erhan", new Date());
        staff.setStoreId(storeId2);
        staffAddress.addStaff(staff);
        Store store = new Store(new Date());
        store.setManagerStaff(staff);
        store.setAddress(storeAddress);
        transaction.begin();
        em.persist(store);
        transaction.commit();
        transaction.begin();
        staff.setStoreId(store);
        em.merge(staff);
        transaction.commit();
        logger.log(Level.SEVERE, "Staff Id = {0}", staff.getStaffId());
        logger.log(Level.SEVERE, "Store id = {0}", store.getStoreId());
        
        
        transaction.begin();
        Store foundStoreNewlyCreated = em.find(Store.class, store.getStoreId());
        assertNotNull(foundStoreNewlyCreated);
        assertEquals(foundStoreNewlyCreated.getStoreId(), store.getStoreId());
        assertEquals(foundStoreNewlyCreated.getAddress(), storeAddress);
        assertEquals(foundStoreNewlyCreated.getManagerStaff().getFirstName(), "Erhan");
        transaction.commit();
        
        logger.log(Level.SEVERE, "StoreIT-testPersist passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        Store foundStoreId1 = em.find(Store.class, Short.valueOf("1"));
        assertNotNull(foundStoreId1);
        assertEquals(foundStoreId1.getStoreId(), Short.valueOf("1"));
        transaction.commit();
        logger.log(Level.SEVERE, "StoreIT-testFind passed.");
    }
    
    @Test
    public void testMerge() {
        transaction.begin();
        Store foundStoreId1 = em.find(Store.class, Short.valueOf("1"));
        Calendar calendar = Calendar.getInstance();
        Date updateDate = calendar.getTime();
        foundStoreId1.setLastUpdate(updateDate);
        Film foundFilmId10 = em.find(Film.class, Short.valueOf("10"));
        int inventoryCount = foundStoreId1.getInventoryList().size();
        Inventory inventory = new Inventory(new Date());
        inventory.setFilm(foundFilmId10);
        foundStoreId1.addInventory(inventory);
        em.merge(foundStoreId1);
        transaction.commit();
        
        transaction.begin();
        Store foundStoreId1Updated = em.find(Store.class, Short.valueOf("1"));
        assertEquals(foundStoreId1Updated.getLastUpdate(), updateDate);
        assertEquals(foundStoreId1Updated.getInventoryList().size(), inventoryCount+1);
        transaction.commit();
        logger.log(Level.SEVERE, "StoreIT-testMerge passed.");
    }
}
