/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhan.dvdrental.entities;

import java.util.Date;
import java.util.List;
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
public class CountryIT {
    
    Logger  logger = Logger.getLogger(CountryIT.class.getName());
    
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
        
        City bolu = new City("Bolu", new Date());
        Country turkiye = new Country("Türkiye", new Date());
        turkiye.addCity(bolu);
        
        transaction.begin();
        em.persist(turkiye);
        transaction.commit();
        
        transaction.begin();
        Country foundCountryNewlyCreated = em.find(Country.class, turkiye.getCountryId());
        assertNotNull(foundCountryNewlyCreated);
        assertEquals(foundCountryNewlyCreated.getCountry(), "Türkiye");
        transaction.commit();
        logger.log(Level.SEVERE, "CountryIT-testPersist passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        Country foundCountryId10 = em.find(Country.class, Short.valueOf("10"));
        assertNotNull(foundCountryId10);
        assertEquals(foundCountryId10.getCountryId(), Short.valueOf("10"));
        transaction.commit();
        logger.log(Level.SEVERE, "CountryT-testFind passed.");
    }
    
    @Test
    public void testMerge() {
        transaction.begin();
        Country foundCountryId10 = em.find(Country.class, Short.valueOf("10"));
        List<City> cityList = foundCountryId10.getCityList();
        cityList.get(0).setCity("YeniSehir");
        foundCountryId10.setCountry("Azerbaycan");
        transaction.commit();
        
        transaction.begin();
        Country foundCountryId10Updated = em.find(Country.class, Short.valueOf("10"));
        City yeniSehir = foundCountryId10.getCityList().get(0);
        assertEquals(foundCountryId10Updated.getCountry(), "Azerbaycan");
        assertEquals(yeniSehir.getCity(), "YeniSehir");
        transaction.commit();
        logger.log(Level.SEVERE, "CountryIT-testMerge passed.");
    }
    
    @Test
    public void testRemove() {
        transaction.begin();
        Country foundCountryId10 = em.find(Country.class, Short.valueOf("10"));
        List<City> cityListForCountryId10 = foundCountryId10.getCityList();
        em.remove(foundCountryId10);
        transaction.commit();
        
        transaction.begin();
        Country foundCountryId10AfterRemove = em.find(Country.class, Short.valueOf("10"));
        assertNull(foundCountryId10AfterRemove);
        for(int i=0; i<cityListForCountryId10.size(); i++) {
            City city = em.find(City.class, cityListForCountryId10.get(i).getCityId());
            assertNull(city);
        }
        
        transaction.commit();
        logger.log(Level.SEVERE, "CountryIT-testRemove passed.");
    }
}
