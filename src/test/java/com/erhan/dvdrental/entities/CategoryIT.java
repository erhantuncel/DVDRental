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
public class CategoryIT {
    
    private static final Logger logger = Logger.getLogger(Category.class.getName());
    
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
        Category adventure = new Category("Adventure", new Date());
        
        transaction.begin();
        em.persist(adventure);
        transaction.commit();
        
        transaction.begin();
        Category foundCategoryNewlyCreated = em.find(Category.class, adventure.getCategoryId());
        assertNotNull(foundCategoryNewlyCreated);
        assertEquals(foundCategoryNewlyCreated.getName(), "Adventure");
        transaction.commit();
        logger.log(Level.SEVERE, "CategoryIT-testPersist passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        Category foundCategoryId10 = em.find(Category.class, Short.valueOf("10"));
        assertNotNull(foundCategoryId10);
        assertEquals(foundCategoryId10.getCategoryId(), Short.valueOf("10"));
        transaction.commit();
        logger.log(Level.SEVERE, "CategoryIT-testFind passed.");
                
    }
    
    @Test
    public void testMerge() {
        transaction.begin();
        Category foundCategoryId10 = em.find(Category.class, Short.valueOf("10"));
        foundCategoryId10.setName("Western");
        em.merge(foundCategoryId10);
        transaction.commit();
        
        transaction.begin();
        Category foundCategoryId10Updated = em.find(Category.class, Short.valueOf("10"));
        assertEquals(foundCategoryId10Updated.getName(), "Western");
        transaction.commit();
        logger.log(Level.SEVERE, "CategoryIT-testMerge passed.");
    }
    
    @Test
    public void testRemove() {
        transaction.begin();
        Category foundCategoryId10 = em.find(Category.class, Short.valueOf("10"));
        em.remove(foundCategoryId10);
        transaction.commit();
        
        transaction.begin();
        Category foundCategoryId10AfterRemove = em.find(Category.class, Short.valueOf("10"));
        assertNull(foundCategoryId10AfterRemove);
        transaction.commit();
        logger.log(Level.SEVERE, "CategoryIT-testRemove passed.");
    }
    
    
}
