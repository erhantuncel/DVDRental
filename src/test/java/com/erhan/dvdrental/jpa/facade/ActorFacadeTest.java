/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Actor;
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
public class ActorFacadeTest {

    private static final Logger LOG = Logger.getLogger(ActorFacadeTest.class.getName());
    
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    
    @InjectMocks
    ActorFacade actorFacade;
    
    @Mock
    EntityManager em;
    
    @Test
    public void testFindByFirstName() {
        List<Actor> actors = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(actors);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Actor.FIND_BY_FIRST_NAME)).thenReturn(mockedQuery);
        actors = this.actorFacade.findByFirstName(FIRST_NAME);
        assertThat(actors, is(not(nullValue())));
        LOG.log(Level.SEVERE, "ActorFacadeTest-testFindByFirstName passed.");
    }
    
    @Test
    public void testFindByLastName() {
        List<Actor> actors = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(actors);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Actor.FIND_BY_LAST_NAME)).thenReturn(mockedQuery);
        actors = this.actorFacade.findByLastName(LAST_NAME);
        assertThat(actors, is(not(nullValue())));
        LOG.log(Level.SEVERE, "ActorFacadeTest-testFindByLastName passed.");
    }
    
}
