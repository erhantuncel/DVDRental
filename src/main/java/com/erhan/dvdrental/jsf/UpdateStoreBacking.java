package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.City;
import com.erhan.dvdrental.entities.Country;
import com.erhan.dvdrental.entities.Store;
import com.erhan.dvdrental.jpa.facade.StoreFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("updateStoreBacking")
@ViewScoped
public class UpdateStoreBacking implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private StoreFacade storeFacade;
    private List<City> cityListBasedOnCountry = new ArrayList<>();
    
    private Store selectedStore;

    public StoreFacade getStoreFacade() {
        return storeFacade;
    }

    public void setStoreFacade(StoreFacade storeFacade) {
        this.storeFacade = storeFacade;
    }

    public List<City> getCityListBasedOnCountry() {
        return cityListBasedOnCountry;
    }

    public void setCityListBasedOnCountry(List<City> cityListBasedOnCountry) {
        this.cityListBasedOnCountry = cityListBasedOnCountry;
    }

    public Store getSelectedStore() {
        return selectedStore;
    }

    public void setSelectedStore(Store selectedStore) {
        this.selectedStore = selectedStore;
        cityListBasedOnCountry = selectedStore.getAddress().getCity().getCountry().getCityList();
    }
    
    public void countrySelectOneMenuValueChanged(ValueChangeEvent event) {
        Country country = (Country) event.getNewValue();
        setCityListBasedOnCountry(country.getCityList());
    }
    
    public void hideUpdateStoreDialog() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("stores.xhtml");
    }

    public void updateStore() {
        System.out.println("Phone = " + selectedStore.getAddress().getPhone());
        selectedStore.setLastUpdate(new Date());
        getStoreFacade().edit(selectedStore);
    }
}
