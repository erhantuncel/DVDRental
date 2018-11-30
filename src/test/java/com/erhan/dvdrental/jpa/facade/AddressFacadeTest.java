/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Address;
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
public class AddressFacadeTest {

    private static final Logger LOG = Logger.getLogger(AddressFacadeTest.class.getName());
    
    private static final String PHONE = "09873646644";
    
    @InjectMocks
    AddressFacade addressFacade;
    
    @Mock
    EntityManager em;
    
    @Test
    public void testFindByPhone() {
        List<Address> addressList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(addressList);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(this.addressFacade.getEntityManager().createNamedQuery(Address.FIND_BY_PHONE)).thenReturn(mockedQuery);
        addressList = this.addressFacade.findByPhone(PHONE);
        assertThat(addressList, is(not(nullValue())));
        LOG.log(Level.SEVERE, "AddressFacadeTest-testFindByPhone passed.");
    }
    
}
