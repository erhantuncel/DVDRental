package com.erhan.dvdrental.jsf;

import java.math.BigDecimal;

public class BillItem {
    
    public static enum ItemType {
        RENTAL,
        OVERDUE,
        REPLACEMENT
    }
    
    private ItemType itemType;
    private String itemDescription;
    private Integer inventoryId;
    private String filmTitle;
    private Integer amount;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

    public BillItem() {
    }

    public BillItem(ItemType itemType, Integer inventoryId, String filmTitle, Integer amount, BigDecimal unitPrice) {
        this.itemType = itemType;
        this.inventoryId = inventoryId;
        this.filmTitle = filmTitle;
        this.amount = amount;
        this.unitPrice = unitPrice;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getItemDescription() {
        String description = new String();
        switch(itemType) {
            case RENTAL :
                description = "Kiralama";
                break;
            case OVERDUE :
                description = "Gecikme";
                break;
            case REPLACEMENT :
                description = "Envanter yenileme";
                break;
        }
        return description;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(amount));
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "BillItem{" + "itemDescription=" + itemDescription + ", inventoryId=" + inventoryId + ", filmTitle=" + filmTitle + ", amount=" + amount + ", unitPrice=" + unitPrice.toString() + ", totalPrice=" + totalPrice.toString() + '}';
    }
}
