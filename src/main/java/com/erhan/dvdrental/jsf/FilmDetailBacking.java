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
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("filmDetailBacking")
@ViewScoped
public class FilmDetailBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FilmFacade filmFacade;
    
    private Film selectedFilm;
    
    private List<InventoryInfoForFilmDetail> inventoryInfoList = new ArrayList<>();

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

    public List<InventoryInfoForFilmDetail> getInventoryInfoList() {
        return inventoryInfoList;
    }

    public void setInventoryInfoList(List<InventoryInfoForFilmDetail> inventoryInfoList) {
        this.inventoryInfoList = inventoryInfoList;
    }

    public void prepareInventoryInfoForFilm() {
        getInventoryInfoList().clear();
        List<Inventory> inventoryList = getSelectedFilm().getInventoryList();
        Map<Store, List<Inventory>> inventoryGroupByStore = inventoryList.stream().collect(Collectors.groupingBy(Inventory::getStore));
        for (Store s : inventoryGroupByStore.keySet()) {
            List<Inventory> inventoryListForStore = inventoryGroupByStore.get(s);
            int nonRentalCount = inventoryListForStore.size();
            for(Inventory i : inventoryListForStore) {
                for(Rental r : i.getRentalList()) {
                    if(r.getReturnDate() == null) {
                        nonRentalCount = nonRentalCount - 1;
                    }
                }
            }
            InventoryInfoForFilmDetail info = new InventoryInfoForFilmDetail(s, inventoryListForStore.size(), nonRentalCount);
            getInventoryInfoList().add(info);
            System.out.println("Store = " + s.getAddress().getCity().getCity() + "-" + s.getAddress().getCity().getCountry().getCountry() + 
                    "Count = " + inventoryListForStore.size() + "Non-Rental = " + nonRentalCount);
        }
    }
}
