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
public class LanguageIT {
    
    Logger logger = Logger.getLogger(LanguageIT.class.getName());
    
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
        
        Language french = new Language("Turkish", new Date());
        
        transaction.begin();
        em.persist(french);
        transaction.commit();
        
        transaction.begin();
        Language foundNewlyCreatedLanguage = em.find(Language.class, french.getLanguageId());
        assertNotNull(foundNewlyCreatedLanguage);
        assertEquals(foundNewlyCreatedLanguage.getName(), "Turkish");
        transaction.commit();
        
        logger.log(Level.SEVERE, "LanguageIT-testPersist passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        Language foundLanguageId5 = em.find(Language.class, Short.valueOf("5"));
        assertNotNull(foundLanguageId5);
        assertEquals(foundLanguageId5.getLanguageId(), Short.valueOf("5"));
        transaction.commit();
        logger.log(Level.SEVERE, "LanguageIT-testFind passed.");
    }
    
    @Test
    public void testMerge() {
        transaction.begin();
        Language foundLanguageId5 = em.find(Language.class, Short.valueOf("5"));
        foundLanguageId5.setName("Türkçe");
        em.merge(foundLanguageId5);
        transaction.commit();
        
        transaction.begin();
        Language foundLanguageId5Updated = em.find(Language.class, Short.valueOf("5"));
        assertEquals(foundLanguageId5Updated.getName(), "Türkçe");
        transaction.commit();
        logger.log(Level.SEVERE, "LanguageIT-testMerge passed.");
    }
    
    @Test
    public void tesRemove() {
        transaction.begin();
        Language foundLanguageId5 = em.find(Language.class, Short.valueOf("5"));
        em.remove(foundLanguageId5);
        transaction.commit();
        
        transaction.begin();
        Language foundLanguageId5AfterRemove = em.find(Language.class, Short.valueOf("5"));
        assertNull(foundLanguageId5AfterRemove);
        transaction.commit();
        logger.log(Level.SEVERE, "LanguageIT-testRemove passed.");
    }
}
