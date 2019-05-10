package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Film;
import com.erhan.dvdrental.entities.Inventory;
import com.erhan.dvdrental.entities.Store;
import com.erhan.dvdrental.jpa.facade.FilmFacade;
import com.erhan.dvdrental.jpa.facade.InventoryFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("addFilmToInventoryBacking")
@ViewScoped
public class AddFilmToInventoryBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private InventoryFacade inventoryFacade;
    
    @EJB
    private FilmFacade filmFacade;
    
    private int count;
    private Film selectedFilm;
    private Store store;

    public AddFilmToInventoryBacking() {
    }

    public InventoryFacade getInventoryFacade() {
        return inventoryFacade;
    }

    public void setInventoryFacade(InventoryFacade inventoryFacade) {
        this.inventoryFacade = inventoryFacade;
    }

    public FilmFacade getFilmFacade() {
        return filmFacade;
    }

    public void setFilmFacade(FilmFacade filmFacade) {
        this.filmFacade = filmFacade;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Film getSelectedFilm() {
        return selectedFilm;
    }

    public void setSelectedFilm(Film selectedFilm) {
        this.selectedFilm = selectedFilm;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    
    public void hideAddRemoveInventoryDialog() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("films.xhtml");
    }
    
    public void addFilmToInventory() {
        System.out.println("Add film to inventory method is invoked.");
        List<Inventory> inventoryList = new ArrayList<>();
        for(int i=0; i<count; i++) {
            Inventory inventory = new Inventory(new Date());
            inventory.setFilm(selectedFilm);
            inventory.setStore(store);
            inventoryFacade.create(inventory);
            inventoryList.add(inventory);
        }
        selectedFilm.getInventoryList().addAll(inventoryList);
        filmFacade.edit(selectedFilm);
        System.out.println(inventoryList.size() + " " + selectedFilm.getTitle() + " added to " 
                + store.getAddress().getCity().getCity() + " - " 
                + store.getAddress().getCity().getCountry().getCountry() + " inventory");
        
    }
}
