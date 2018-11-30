/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Customer;
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
public class CustomerFacadeTest {

    private static final Logger LOG = Logger.getLogger(CustomerFacadeTest.class.getName());
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    
    @InjectMocks
    CustomerFacade customerFacade;
    
    @Mock
    EntityManager em;
    
    @Test
    public void testFindByFirstName() {
        List<Customer> customerList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(customerList);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Customer.FIND_BY_FIRST_NAME)).thenReturn(mockedQuery);
        List<Customer> customers = this.customerFacade.findByFirstName(FIRST_NAME);
        assertThat(customers, is(not(nullValue())));
        LOG.log(Level.SEVERE, "CustomerFacadeTest-testFindByFirstName passed.");
    }
    
    @Test
    public void testFindByLastName() {
        List<Customer> customerList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(customerList);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Customer.FIND_BY_LAST_NAME)).thenReturn(mockedQuery);
        List<Customer> customers = this.customerFacade.findByLastName(LAST_NAME);
        assertThat(customers, is(not(nullValue())));
        LOG.log(Level.SEVERE, "CustomerFacadeTest-testFindByLastName passed.");
    }
    
    @Test
    public void testFindByEmail() {
        List<Customer> customerList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(customerList);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Customer.FIND_BY_EMAIL)).thenReturn(mockedQuery);
        List<Customer> customers = this.customerFacade.findByEmail(EMAIL);
        assertThat(customers, is(not(nullValue())));
        LOG.log(Level.SEVERE, "CustomerFacadeTest-testFindByEmail passed.");
    }
    
}
