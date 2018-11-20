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
public class ActorIT {
    
    Logger logger = Logger.getLogger(ActorIT.class.getName());
    
    private EntityManager em;
    private EntityTransaction transaction;
    private Actor tomHanks;
    
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
        
        this.tomHanks = new Actor("Tom", "Hanks", new Date());
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testParse() {
        transaction.begin();
        em.persist(tomHanks);
        transaction.commit();
        
        transaction.begin();
        Actor foundActorNewlyCreated = em.find(Actor.class, tomHanks.getActorId());
        assertNotNull(foundActorNewlyCreated);
        assertEquals(foundActorNewlyCreated.getFirstName(), "Tom");
        assertEquals(foundActorNewlyCreated.getLastName(), "Hanks");
        transaction.commit();
        logger.log(Level.SEVERE, "ActorIT-testParse passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        Actor foundActorId10 = em.find(Actor.class, Short.valueOf("10"));
        assertNotNull(foundActorId10);
        assertEquals(foundActorId10.getActorId(), Short.valueOf("10"));
        transaction.commit();
        logger.log(Level.SEVERE, "ActorIT-testFind passed.");
    }
    
    @Test
    public void testMerge() {
        transaction.begin();
        Actor foundActorId10 = em.find(Actor.class, Short.valueOf("10"));
        foundActorId10.setFirstName("TOM");
        foundActorId10.setLastName("HANKS");
        em.merge(foundActorId10);
        transaction.commit();
        
        transaction.begin();
        Actor foundActorId10Updated = em.find(Actor.class, Short.valueOf("10"));
        assertEquals(foundActorId10Updated.getFirstName(), "TOM");
        assertEquals(foundActorId10Updated.getLastName(), "HANKS");
        transaction.commit();
        logger.log(Level.SEVERE, "ActorIT-testMerge passed.");
    }
    
    @Test
    public void testRemove() {
        transaction.begin();
        Actor foundActorId10 = em.find(Actor.class, Short.valueOf("10"));
        em.remove(foundActorId10);
        transaction.commit();
        
        transaction.begin();
        Actor foundActorAfterRemove = em.find(Actor.class, Short.valueOf("10"));
        assertNull(foundActorAfterRemove);
        transaction.commit();
        logger.log(Level.SEVERE, "ActorIT-testRemove passed.");
    }
}
