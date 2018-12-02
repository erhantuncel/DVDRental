/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Film;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author erhan
 */
@RunWith(MockitoJUnitRunner.class)
public class FilmFacadeTest {

    private static final Logger LOG = Logger.getLogger(FilmFacadeTest.class.getName());
    
    private static final String TITLE = "Whiplash";
    private static final Integer RELEASE_YEAR = 2018;
    private static final BigDecimal RENTAL_RATE = new BigDecimal("10.22");
    private static final Short LENGTH = Short.valueOf("120");
    private static final String RATING = "G";
    private static final String SPECIAL_FEATURES =  "Behind the Scenes";
    
    
    @InjectMocks
    FilmFacade filmFacade;
    
    @Mock
    EntityManager em;
    
    @Test
    public void testFindByTitle() {
        List<Film> filmList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(filmList);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Film.FIND_BY_TITLE)).thenReturn(mockedQuery);
        List<Film> films = this.filmFacade.findByTitle(TITLE);
        assertThat(films, is(not(nullValue())));
        LOG.log(Level.SEVERE, "FilmFacadeTest-testFindByTitle passed.");
    }
    
    @Test
    public void testFindByReleaseYear() {
        List<Film> filmList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(filmList);
        when(mockedQuery.setParameter(anyString(), anyInt())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Film.FIND_BY_RELEASE_YEAR)).thenReturn(mockedQuery);
        List<Film> films = this.filmFacade.findByReleaseYear(RELEASE_YEAR);
        assertThat(films, is(not(nullValue())));
        LOG.log(Level.SEVERE, "FilmFacadeTest-testFindByReleaseYear passed");
    }
    
    @Test
    public void testFindByRentalRate() {
        List<Film> filmList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(filmList);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Film.FIND_BY_RENTAL_RATE)).thenReturn(mockedQuery);
        List<Film> films = this.filmFacade.findByRentalRate(RENTAL_RATE);
        assertThat(films, is(not(nullValue())));
        LOG.log(Level.SEVERE, "FilmFacadeTest-testFindByRentalRate passed");
    }
    
    @Test
    public void testFindByLength() {
        List<Film> filmList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(filmList);
        when(mockedQuery.setParameter(anyString(), anyShort())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Film.FIND_BY_LENGTH)).thenReturn(mockedQuery);
        List<Film> films = this.filmFacade.findByLength(LENGTH);
        assertThat(films, is(not(nullValue())));
        LOG.log(Level.SEVERE, "FilmFacadeTest-testFindByLength passed");
    }
    
    @Test
    public void testFindByRating() {
        List<Film> filmList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(filmList);
        when(mockedQuery.setParameter(anyString(), anyString())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Film.FIND_BY_RATING)).thenReturn(mockedQuery);
        List<Film> films = this.filmFacade.findByRating(RATING);
        assertThat(films, is(not(nullValue())));
        LOG.log(Level.SEVERE, "FilmFacadeTest-testFindByRating passed");
    }
    
    @Test
    public void testFindBySpecialFeatures() {
        List<Film> filmList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(filmList);
        when(mockedQuery.setParameter(anyString(), anyString())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Film.FIND_BY_SPECIAL_FEATURES)).thenReturn(mockedQuery);
        List<Film> films = this.filmFacade.findBySpecialFeatures(SPECIAL_FEATURES);
        assertThat(films, is(not(nullValue())));
        LOG.log(Level.SEVERE, "FilmFacadeTest-testFindBySpecialFeatures passed");
    }
    
}
