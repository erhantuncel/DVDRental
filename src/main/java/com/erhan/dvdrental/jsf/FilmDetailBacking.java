package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Film;
import com.erhan.dvdrental.entities.Inventory;
import com.erhan.dvdrental.entities.Rental;
import com.erhan.dvdrental.entities.Store;
import com.erhan.dvdrental.jpa.facade.FilmFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("filmDetailBacking")
@ViewScoped
public class FilmDetailBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FilmFacade filmFacade;
    
    private Film selectedFilm;
    private Store selectedStore;
    
    private List<Store> storeListForFilm = new ArrayList<>();
    private List<InventoryInfoWithAvailabiltyForFilmDetail> inventoryInfoListWithAvailability = new ArrayList<>();
    
    public FilmDetailBacking() {
    }
    
    public FilmFacade getFilmFacade() {
        return filmFacade;
    }

    public void setFilmFacade(FilmFacade filmFacade) {
        this.filmFacade = filmFacade;
    }
    
    public Film getSelectedFilm() {
        return selectedFilm;
    }

    public void setSelectedFilm(Film selectedFilm) {
        this.selectedFilm = selectedFilm;
    }

    public Store getSelectedStore() {
        return selectedStore;
    }

    public void setSelectedStore(Store selectedStore) {
        this.selectedStore = selectedStore;
    }

    public List<Store> getStoreListForFilm() {
        return storeListForFilm;
    }

    public void setStoreListForFilm(List<Store> storeListForFilm) {
        this.storeListForFilm = storeListForFilm;
    }

    public List<InventoryInfoWithAvailabiltyForFilmDetail> getInventoryInfoListWithAvailability() {
        return inventoryInfoListWithAvailability;
    }

    public void setInventoryInfoListWithAvailability(List<InventoryInfoWithAvailabiltyForFilmDetail> inventoryInfoListWithAvailability) {
        this.inventoryInfoListWithAvailability = inventoryInfoListWithAvailability;
    }

    public Map<Store, List<Inventory>> createInventoryMayByStore() {
        List<Inventory> inventoryList = getSelectedFilm().getInventoryList();
        Map<Store, List<Inventory>> inventoryGroupByStore = inventoryList.stream().collect(Collectors.groupingBy(Inventory::getStore));
        return inventoryGroupByStore;
    }
    
    public void prepareInventoryHashMapForFilmDetail() {
        populateStoreListBasedOnInvenotryList();
        populateInventoryListWithAvailabilityByStore(storeListForFilm.get(0));
        setSelectedStore(storeListForFilm.get(0));
    }
    
    public void handleStoreValueChanged(ValueChangeEvent event) {
        Store store = (Store) event.getNewValue();
        populateInventoryListWithAvailabilityByStore(store);
    }
    
    public void populateStoreListBasedOnInvenotryList() {
        getStoreListForFilm().clear();
        Map<Store, List<Inventory>> inventoryGroupByStore = createInventoryMayByStore();
        for(Store store : inventoryGroupByStore.keySet()) {
            storeListForFilm.add(store);
        }
    }
    
    public Boolean isInventoryAvailableToRent(Inventory inventory) {
        for(Rental rental : inventory.getRentalList()) {
            if(rental.getReturnDate() == null) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
    
    public void populateInventoryListWithAvailabilityByStore(Store store) {
        getInventoryInfoListWithAvailability().clear();
        Map<Store, List<Inventory>> inventoryMayByStore = createInventoryMayByStore();
        List<Inventory> inventoryList = inventoryMayByStore.get(store);
        for(Inventory inventory : inventoryList) {
            InventoryInfoWithAvailabiltyForFilmDetail inventoryInfo = 
                    new InventoryInfoWithAvailabiltyForFilmDetail(inventory, isInventoryAvailableToRent(inventory));
            inventoryInfoListWithAvailability.add(inventoryInfo);
        }
    }
}
