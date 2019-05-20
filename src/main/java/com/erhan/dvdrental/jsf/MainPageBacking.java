package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Film;
import com.erhan.dvdrental.entities.Rental;
import com.erhan.dvdrental.jpa.facade.FilmFacade;
import com.erhan.dvdrental.jpa.facade.RentalFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("mainPageBacking")
@ViewScoped
public class MainPageBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private RentalFacade rentalFacade;
    
    @EJB
    private FilmFacade filmFacade;
    
    private List<Rental> overdueRentals = null;
    private List<Rental> lastFiveRentals = null;
    private List<Film> lastUpdatedFiveFilms = null;
    
    @PostConstruct
    public void init() {
        if(overdueRentals == null) {
            overdueRentals = overdueRentalList();
        }  
        if(lastFiveRentals == null) {
            lastFiveRentals = findLastFiveRentals();
        }
        if(lastUpdatedFiveFilms == null) {
            lastUpdatedFiveFilms = findLastUpdatedFiveFilms();
        }
    }
    
    public MainPageBacking() {
    }
    
    public RentalFacade getRentalFacade() {
        return rentalFacade;
    }

    public void setRentalFacade(RentalFacade rentalFacade) {
        this.rentalFacade = rentalFacade;
    }

    public FilmFacade getFilmFacade() {
        return filmFacade;
    }

    public void setFilmFacade(FilmFacade filmFacade) {
        this.filmFacade = filmFacade;
    }

    public List<Rental> getOverdueRentals() {
        return overdueRentals;
    }

    public void setOverdueRentals(List<Rental> overdueRentals) {
        this.overdueRentals = overdueRentals;
    }

    public List<Rental> getLastFiveRentals() {
        return lastFiveRentals;
    }

    public void setLastFiveRentals(List<Rental> lastFiveRentals) {
        this.lastFiveRentals = lastFiveRentals;
    }

    public List<Film> getLastUpdatedFiveFilms() {
        return lastUpdatedFiveFilms;
    }

    public void setLastUpdatedFiveFilms(List<Film> lastUpdatedFiveFilms) {
        this.lastUpdatedFiveFilms = lastUpdatedFiveFilms;
    }

    public List<Rental> overdueRentalList() {
        List<Rental> rentalListReturnDateNull = rentalFacade.findByReturnDateIsNull();
        List<Rental> overdueRentalList = new ArrayList<>();
        if(rentalListReturnDateNull != null && rentalListReturnDateNull.size() > 0) {
            for(Rental rental : rentalListReturnDateNull ) {
                short rentalDuration = rental.getInventory().getFilm().getRentalDuration();
                long diff = new Date().getTime() - rental.getRentalDate().getTime();
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                if(days > rentalDuration) {
                    overdueRentalList.add(rental);
                }
            }
        }
        return overdueRentalList;
    }
    
    public List<Rental> findLastFiveRentals() {
        List<Rental> lastFiveRentalList = rentalFacade.findLastFiveRentals();
        return lastFiveRentalList;
    }
    
    public List<Film> findLastUpdatedFiveFilms() {
        List<Film> lastUpdatedFiveFilmList = filmFacade.findLastUpdatedFiveFilms();
        return lastUpdatedFiveFilmList;
    }
}
