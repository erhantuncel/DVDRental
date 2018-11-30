/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Category;
import java.util.ArrayList;
import java.util.List;
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
public class CategoryFacadeTest {

    private static final Logger LOG = Logger.getLogger(CategoryFacadeTest.class.getName());
    
    public static final String NAME = "Drama";
    
    @InjectMocks
    CategoryFacade categoryFacade;
    
    @Mock
    EntityManager em;
    
    @Test
    public void testFindByName() {
        List<Category> categoryList = new ArrayList<>();
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(categoryList);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(this.em.createNamedQuery(Category.FIND_BY_NAME)).thenReturn(mockedQuery);
        categoryList = this.categoryFacade.findByName(NAME);
        assertThat(categoryList, is(not(nullValue())));
    }
}
