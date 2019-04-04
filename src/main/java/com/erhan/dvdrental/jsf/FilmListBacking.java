package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Film;
import com.erhan.dvdrental.jpa.facade.FilmFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("filmListBacking")
@ViewScoped
public class FilmListBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FilmFacade filmFacade;
    
    private List<Film> filmList = new ArrayList<Film>();
    
    public FilmListBacking() {
    }

    public FilmFacade getFilmFacade() {
        return filmFacade;
    }

    public void setFilmFacade(FilmFacade filmFacade) {
        this.filmFacade = filmFacade;
    }

    public List<Film> getFilmList() {
        this.filmList = getFilmFacade().findAll();
        return this.filmList;
    }

    public void setFilmList(List<Film> filmList) {
        this.filmList = filmList;
    }
    
    
    
    

}
