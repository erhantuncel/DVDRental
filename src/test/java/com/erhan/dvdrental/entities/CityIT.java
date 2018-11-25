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
public class CityIT {
    
    public static final Logger logger = Logger.getLogger(CityIT.class.getName());
    
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
        Country turkey = em.find(Country.class, Short.valueOf("97"));
        transaction.commit();
        City bolu = new City("Bolu", new Date());
        Address address1 = new Address("Borazanlar M.", "Merkez", "049583738822", new Date());
        Address address2 = new Address("Tabaklar M.", "Merkez", "0987364477", new Date());
        bolu.addAddress(address1);
        bolu.addAddress(address2);
        bolu.setCountry(turkey);
        
        transaction.begin();
        em.persist(bolu);
        transaction.commit();
        
        transaction.begin();
        City foundCityNewlyCreated = em.find(City.class, bolu.getCityId());
        assertNotNull(foundCityNewlyCreated);
        assertEquals(foundCityNewlyCreated.getCityId(), bolu.getCityId());
        assertEquals(foundCityNewlyCreated.getAddressList().size(), 2);
        transaction.commit();
        
        logger.log(Level.SEVERE, "CityIT-testPersist passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        City foundCityId10 = em.find(City.class, Short.valueOf("10"));
        assertNotNull(foundCityId10);
        assertEquals(foundCityId10.getCityId(), Short.valueOf("10"));
        transaction.commit();
        logger.log(Level.SEVERE, "CityIT-testFind passed.");
    }
    
    @Test
    public void testMerge() {
        transaction.begin();
        City foundCityId10 = em.find(City.class, Short.valueOf("10"));
        foundCityId10.setCity("Ankara");
        foundCityId10.addAddress(new Address("Mahalle", "Çankaya", "03128765543", new Date()));
        em.merge(foundCityId10);
        transaction.commit();
        
        transaction.begin();
        City foundCityId10Updated = em.find(City.class, Short.valueOf("10"));
        assertEquals(foundCityId10Updated.getCity(), "Ankara");
        assertEquals(foundCityId10Updated.getAddressList().size(), 2);
        transaction.commit();
        logger.log(Level.SEVERE, "CityIT-testMerge passed.");
    }
    
    @Test
    public void testRemove() {
        transaction.begin();
        City foundCityId48 = em.find(City.class, Short.valueOf("48"));
        int addressListSize = foundCityId48.getAddressList().size();
        logger.log(Level.SEVERE, "Address List Size = {0}", addressListSize);
        int [] cityIdArray = new int[addressListSize];
        for (int i=0; i<addressListSize; i++) {
            cityIdArray[i] = foundCityId48.getAddressList().get(i).getAddressId();
        }
        em.remove(foundCityId48);
        transaction.commit();
        
        transaction.begin();
        City foundCityId48AfterRemove = em.find(City.class, Short.valueOf("48"));
        for(int i=0; i<cityIdArray.length; i++) {
            City city = em.find(City.class, Short.valueOf(String.valueOf(i)));
            assertNull(city);
        }
        assertNull(foundCityId48AfterRemove);
        transaction.commit();
        logger.log(Level.SEVERE, "CityIT-testRemove passed.");   
    }
}
