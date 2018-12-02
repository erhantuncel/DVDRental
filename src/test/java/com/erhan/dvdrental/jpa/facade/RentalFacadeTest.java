/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Rental;
import java.util.ArrayList;
import java.util.Date;
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
public class RentalFacadeTest {

    private static final Logger LOG = Logger.getLogger(RentalFacadeTest.class.getName());
    
    private static final Date RENTAL_DATE = new Date();
    private static final Date RETURN_DATE = new Date();
    
    @InjectMocks
    RentalFacade rentalFacade;
    
    @Mock
    EntityManager em;
    
    @Test
    public void testFindByRentalDate() {
        List<Rental> rentalList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(rentalList);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Rental.FIND_BY_RENTAL_DATE)).thenReturn(mockedQuery);
        rentalList = this.rentalFacade.findByRentalDate(RENTAL_DATE);
        assertThat(rentalList, is(not(nullValue())));
        LOG.log(Level.SEVERE, "RentalFacadeTest-testFindByRentalDate passed.");
    }

    @Test
    public void testFindByReturnDate() {
        List<Rental> rentalList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(rentalList);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Rental.FIND_BY_RETURN_DATE)).thenReturn(mockedQuery);
        rentalList = this.rentalFacade.findByReturnDate(RETURN_DATE);
        assertThat(rentalList, is(not(nullValue())));
        LOG.log(Level.SEVERE, "RentalFacadeTest-testFindByReturnDate passed.");
    }    
}
