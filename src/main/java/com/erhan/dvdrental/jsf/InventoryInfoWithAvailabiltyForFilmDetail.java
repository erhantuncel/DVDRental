package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Inventory;

public class InventoryInfoWithAvailabiltyForFilmDetail {

    private Inventory inventory;
    private Boolean availableForRent;

    public InventoryInfoWithAvailabiltyForFilmDetail() {
    }

    public InventoryInfoWithAvailabiltyForFilmDetail(Inventory inventory, Boolean availableForRent) {
        this.inventory = inventory;
        this.availableForRent = availableForRent;
    }
    
    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Boolean getAvailableForRent() {
        return availableForRent;
    }

    public void setAvailableForRent(Boolean availableForRent) {
        this.availableForRent = availableForRent;
    }
    
    
}
