package com.erhan.dvdrental.jsf;

public enum FilmRatings {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");
    
    private String label;

    private FilmRatings(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
}
