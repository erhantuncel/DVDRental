package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.City;
import com.erhan.dvdrental.jpa.facade.CityFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("cityController")
@SessionScoped
public class CityController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private CityFacade cityFacade;
    List<City> cityList = null;

    public CityFacade getCityFacade() {
        return cityFacade;
    }

    public void setCityFacade(CityFacade cityFacade) {
        this.cityFacade = cityFacade;
    }

    public List<City> getCityList() {
        return getCityFacade().findAll();
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }
    
    public City getCity(Short id) {
        return getCityFacade().find(id);
    }
}
