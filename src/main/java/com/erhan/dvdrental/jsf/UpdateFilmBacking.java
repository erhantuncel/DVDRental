package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Actor;
import com.erhan.dvdrental.entities.Category;
import com.erhan.dvdrental.entities.Film;
import com.erhan.dvdrental.jpa.facade.ActorFacade;
import com.erhan.dvdrental.jpa.facade.CategoryFacade;
import com.erhan.dvdrental.jpa.facade.FilmFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
    
    @EJB
    private ActorFacade actorFacade;
    
    private Film selectedFilm;
    private List<Category> selectedCategories;
    private Actor actor;
    
    private String actorFirstName;
    private String actorLastName;
    private Set<Actor> actorList;
    private Set<Actor> newActorList;
    
    
    public UpdateFilmBacking() {
    }

    @PostConstruct
    public void init() {
        actor = new Actor();
        actor.setLastUpdate(new Date());
        actorList = new HashSet<>();
        newActorList = new HashSet<>();
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

    public ActorFacade getActorFacade() {
        return actorFacade;
    }

    public void setActorFacade(ActorFacade actorFacade) {
        this.actorFacade = actorFacade;
    }
    
    public Film getSelectedFilm() {
        return selectedFilm;
    }

    public void setSelectedFilm(Film selectedFilm) {
        this.selectedFilm = selectedFilm;
        setSelectedCategories(selectedFilm.getCategoryList());
        setActorList(selectedFilm.getActorList());
        System.out.println("setSelectedFilm is invoked.");
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

    public Set<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(Set<Actor> actorList) {
        this.actorList = actorList;
    }
    
    public FilmRatings[] getFilmRatings() {
        return FilmRatings.values();
    }

    public List<Category> completeCategory(String query) {
        return getCategoryFacade().findAll();
    }
    
    public String reInitActor() {
        this.newActorList.add(this.actor);
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
        if(newActorList != null && newActorList.size() > 0) {
            int actorInDb = 0;
            for(Actor newActor : newActorList) {
                List<Actor> foundActorList = actorFacade.findByLastName(newActor.getLastName());
                if(foundActorList != null && foundActorList.size() > 0) {
                    for(Actor actorInFoundActorList : foundActorList) {
                        if(actorInFoundActorList.getFirstName().equals(newActor.getFirstName())) {
                            selectedFilm.getActorList().remove(newActor);
                            selectedFilm.getActorList().add(actorInFoundActorList);
                            actorInFoundActorList.getFilms().add(selectedFilm);
                            actorInDb++;
                        }
                    }
                }
                newActor.getFilms().add(selectedFilm);
            }
        }
        selectedFilm.setLastUpdate(new Date());
        filmFacade.edit(selectedFilm);
        System.out.println(selectedFilm.getTitle() + " is updated.");
    }

}
