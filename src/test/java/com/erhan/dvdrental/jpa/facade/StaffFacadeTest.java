/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Staff;
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
public class StaffFacadeTest {

    private static final Logger LOG = Logger.getLogger(StaffFacadeTest.class.getName());
    
    private static final String FIRST_NAME = "Ali";
    private static final String LAST_NAME = "Veli";
    private static final String EMAIL = "aliveli@abc.com";
    private static final String USER_NAME = "aliveli";
    
    @InjectMocks
    StaffFacade staffFacade;
    
    @Mock
    EntityManager em;
    
    @Test
    public void testFindByFirstName() {
        List<Staff> staffList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(staffList);
        when(mockedQuery.setParameter(anyString(), anyString())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Staff.FIND_BY_FIRST_NAME)).thenReturn(mockedQuery);
        staffList = this.staffFacade.findByFirstName(FIRST_NAME);
        assertThat(staffList, is(not(nullValue())));
        LOG.log(Level.SEVERE, "StaffFacadeTest-testFindByFirstName passed");
    }

    @Test
    public void testFindByLastName() {
        List<Staff> staffList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(staffList);
        when(mockedQuery.setParameter(anyString(), anyString())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Staff.FIND_BY_LAST_NAME)).thenReturn(mockedQuery);
        staffList = this.staffFacade.findByLastName(LAST_NAME);
        assertThat(staffList, is(not(nullValue())));
        LOG.log(Level.SEVERE, "StaffFacadeTest-testFindByLastName passed");
    }

    @Test
    public void testFindByEmail() {
        List<Staff> staffList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(staffList);
        when(mockedQuery.setParameter(anyString(), anyString())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Staff.FIND_BY_EMAIL)).thenReturn(mockedQuery);
        staffList = this.staffFacade.findByEmail(EMAIL);
        assertThat(staffList, is(not(nullValue())));
        LOG.log(Level.SEVERE, "StaffFacadeTest-testFindByEmail passed");
    }

    @Test
    public void testFindByUserName() {
        List<Staff> staffList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(staffList);
        when(mockedQuery.setParameter(anyString(), anyString())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Staff.FIND_BY_USER_NAME)).thenReturn(mockedQuery);
        staffList = this.staffFacade.findByUserName(USER_NAME);
        assertThat(staffList, is(not(nullValue())));
        LOG.log(Level.SEVERE, "StaffFacadeTest-testFindByEmail passed");
    }  
}
