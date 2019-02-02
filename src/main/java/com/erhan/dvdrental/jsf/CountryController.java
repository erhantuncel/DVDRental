package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Country;
import com.erhan.dvdrental.jpa.facade.CountryFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("countryController")
@SessionScoped
public class CountryController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private CountryFacade countryFacade;
    private List<Country> countryList = null;

    public CountryController() {
    }
    
    public CountryFacade getCountryFacade() {
        return countryFacade;
    }

    public void setCountryFacade(CountryFacade countryFacade) {
        this.countryFacade = countryFacade;
    }

    public Country getCountry(Short id) {
        return getCountryFacade().find(id);
    }
    
    
    public List<Country> getCountryList() {
        return getCountryFacade().findAll();
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    
}
