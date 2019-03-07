package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Store;
import com.erhan.dvdrental.jpa.facade.StoreFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;  

@Named("storeListBacking")
@ViewScoped
public class StoreListBacking implements Serializable{

    private static final long serialVersionUID = 1L;

    @EJB
    private StoreFacade storeFacade;
    private List<Store> storeList = null;
    
    private List<Store> filteredStores;

    public StoreFacade getStoreFacade() {
        return storeFacade;
    }

    public void setStoreFacade(StoreFacade storeFacade) {
        this.storeFacade = storeFacade;
    }

    public List<Store> getStoreList() {
        if(storeList == null) {
            storeList = getStoreFacade().findAll();
        }
        return storeList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }

    public List<Store> getFilteredStores() {
        return filteredStores;
    }

    public void setFilteredStores(List<Store> filteredStores) {
        this.filteredStores = filteredStores;
    }
}