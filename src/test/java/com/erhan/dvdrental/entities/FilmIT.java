package com.erhan.dvdrental.entities;

import java.math.BigDecimal;
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

public class FilmIT {
    
    private static final Logger logger = Logger.getLogger(FilmIT.class.getName());
    
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
        Film film= new Film();
        
        film.setTitle("Whiplash");
        film.setDescription("A promising young drummer enrolls at a cut-throat music conservatory "
                + "where his dreams of greatness are mentored by an instructor who will stop "
                + "at nothing to realize a student's potential.");
        film.setReleaseYear(2014);
        film.setRentalDuration(Short.valueOf("3"));
        film.setRentalRate(new BigDecimal("4.99"));
        film.setLength(Short.valueOf("106"));
        film.setReplacementCost(new BigDecimal("19.99"));
        film.setLastUpdate(new Date());
        Language language = em.find(Language.class, Short.valueOf("1"));
        film.setLanguage(language);
        Category drama = em.find(Category.class, Short.valueOf("7"));
        film.getCategoryList().add(drama);
        Actor milesTeller = new Actor("Miles", "Teller", new Date());
        Actor jkSimmons = new Actor("J.K.", "Simmons", new Date());
        film.getActorList().add(jkSimmons);
        film.getActorList().add(milesTeller);
        
        transaction.begin();
        em.persist(film);
        transaction.commit();
        
        transaction.begin();
        Film foundFilmNewlyCreated = em.find(Film.class, film.getFilmId());
        assertNotNull(foundFilmNewlyCreated);
        assertEquals(foundFilmNewlyCreated.getActorList().size(), 2);
        assertEquals(foundFilmNewlyCreated.getCategoryList().size(), 1);
        transaction.commit();
        
        logger.log(Level.SEVERE, "FilmIT-testPersist passed.");
    }
    
    @Test
    public void testFind() {
        transaction.begin();
        Film foundFilmId10 = em.find(Film.class, Short.valueOf("10"));
        assertNotNull(foundFilmId10);
        assertEquals(foundFilmId10.getFilmId(), Short.valueOf("10"));
        transaction.commit();
        logger.log(Level.SEVERE, "FilmIT-testFind passed.");
    }
    
    @Test
    public void testMerge() {
        transaction.begin();
        Film foundFilmId10 = em.find(Film.class, Short.valueOf("10"));
        foundFilmId10.setReleaseYear(2015);
        em.merge(foundFilmId10);
        transaction.commit();
        
        transaction.begin();
        Film foundFilmId10Updated = em.find(Film.class, Short.valueOf("10"));
        assertEquals(foundFilmId10Updated.getReleaseYear(), new Integer(2015));
        transaction.commit();
        logger.log(Level.SEVERE, "FilmIT-testMerge passed.");
    }
    
    @Test
    public void testRemve() {
        transaction.begin();
        Film foundFilmId10 = em.find(Film.class, Short.valueOf("10"));
        em.remove(foundFilmId10);
        transaction.commit();
        
        transaction.begin();
        Film foundFilmId10AfterRemove = em.find(Film.class, Short.valueOf("10"));
        assertNull(foundFilmId10AfterRemove);
        transaction.commit();
        logger.log(Level.SEVERE, "FilmIT-testRemove passed.");
    }
    
}
