package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Store;

public class InventoryInfoForFilmDetail {
    private Store store;
    private int totalFilmCount;
    private int availableForRentCount;

    public InventoryInfoForFilmDetail() {
    }
    
    public InventoryInfoForFilmDetail(Store store, int totalFilmCount, int availableForRentCount) {
        this.store = store;
        this.totalFilmCount = totalFilmCount;
        this.availableForRentCount = availableForRentCount;
    }
    
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getTotalFilmCount() {
        return totalFilmCount;
    }

    public void setTotalFilmCount(int totalFilmCount) {
        this.totalFilmCount = totalFilmCount;
    }

    public int getAvailableForRentCount() {
        return availableForRentCount;
    }

    public void setAvailableForRentCount(int availableForRentCount) {
        this.availableForRentCount = availableForRentCount;
    }
}
