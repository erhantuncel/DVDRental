package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Store;
import com.erhan.dvdrental.jpa.facade.StoreFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("storeController")
@SessionScoped
public class StoreController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    StoreFacade storeFacade;
    List<Store> storeList = null;

    public StoreController() {
    }

    public StoreFacade getStoreFacade() {
        return storeFacade;
    }

    public void setStoreFacade(StoreFacade storeFacade) {
        this.storeFacade = storeFacade;
    }

    public Store getStore(Short id) {
        return getStoreFacade().find(id);
    }
    
    public List<Store> getStoreList() {
        return getStoreFacade().findAll();
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }
    
    

}
