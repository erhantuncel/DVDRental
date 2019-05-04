package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Actor;
import com.erhan.dvdrental.entities.Category;
import com.erhan.dvdrental.entities.Film;
import com.erhan.dvdrental.jpa.facade.CategoryFacade;
import com.erhan.dvdrental.jpa.facade.FilmFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("updateFilmBacking")
@ViewScoped
public class UpdateFilmBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FilmFacade filmFacade;
    
    @EJB
    private CategoryFacade categoryFacade;
    
    private Film selectedFilm;
    private List<Category> selectedCategories;
    private Actor actor;
    
    private String actorFirstName;
    private String actorLastName;
    private List<Actor> actorList;

    public UpdateFilmBacking() {
    }

    @PostConstruct
    public void init() {
        actor = new Actor();
        actor.setLastUpdate(new Date());
        actorList = new ArrayList<>();
    }
    public FilmFacade getFilmFacade() {
        return filmFacade;
    }

    public void setFilmFacade(FilmFacade filmFacade) {
        this.filmFacade = filmFacade;
    }

    public CategoryFacade getCategoryFacade() {
        return categoryFacade;
    }

    public void setCategoryFacade(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
    }
    
    public Film getSelectedFilm() {
        return selectedFilm;
    }

    public void setSelectedFilm(Film selectedFilm) {
        this.selectedFilm = selectedFilm;
        setSelectedCategories(selectedFilm.getCategoryList());
        setActorList(selectedFilm.getActorList());
    }

    public List<Category> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<Category> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public Actor getActor() {
        return actor;
    }

    public String getActorFirstName() {
        return actorFirstName;
    }

    public void setActorFirstName(String actorFirstName) {
        this.actorFirstName = actorFirstName;
    }

    public String getActorLastName() {
        return actorLastName;
    }

    public void setActorLastName(String actorLastName) {
        this.actorLastName = actorLastName;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }
    
    public FilmRatings[] getFilmRatings() {
        return FilmRatings.values();
    }

    public List<Category> completeCategory(String query) {
        return getCategoryFacade().findAll();
    }
    
    public String reInitActor() {
        this.actor = new Actor();
        actor.setLastUpdate(new Date());
        System.out.println("re-initialization Actor");
        return null;
    }
    
    public void hideUpdateFilmDialog() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("films.xhtml");
    }
    
    public void updateFilm() {
        System.out.println("updateFilm is invoked.");
        selectedFilm.setCategoryList(selectedCategories);
        selectedFilm.setActorList(actorList);
        selectedFilm.setLastUpdate(new Date());
        filmFacade.edit(selectedFilm);
        System.out.println(selectedFilm.getTitle() + " is updated.");
    }

}
