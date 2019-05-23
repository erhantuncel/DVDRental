package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Rental;
import com.erhan.dvdrental.entities.Staff;
import com.erhan.dvdrental.jpa.facade.FilmFacade;
import com.erhan.dvdrental.jpa.facade.RentalFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
    private List<Rental> lastSixtyRentals = null;
    
    @PostConstruct
    public void init() {
        if(overdueRentals == null) {
            overdueRentals = overdueRentalList();
        }  
        if(lastSixtyRentals == null) {
            lastSixtyRentals = findLastSixtyRentals();
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

    public List<Rental> getLastSixtyRentals() {
        return lastSixtyRentals;
    }

    public void setLastSixtyRentals(List<Rental> lastSixtyRentals) {
        this.lastSixtyRentals = lastSixtyRentals;
    }

    public List<Rental> overdueRentalList() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Staff currentStaff = (Staff) sessionMap.get("Staff");
        List<Rental> rentalListReturnDateNull = rentalFacade.findReturnDateIsNullByStore(currentStaff.getStoreId());
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
    
    public List<Rental> findLastSixtyRentals() {
        List<Rental> lastSixtyRentalList = rentalFacade.findLastSixtyRentals();
        return lastSixtyRentalList;
    }
}
