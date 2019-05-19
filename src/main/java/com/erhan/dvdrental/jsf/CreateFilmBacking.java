package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Actor;
import com.erhan.dvdrental.entities.Category;
import com.erhan.dvdrental.entities.Film;
import com.erhan.dvdrental.entities.Language;
import com.erhan.dvdrental.jpa.facade.ActorFacade;
import com.erhan.dvdrental.jpa.facade.FilmFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("createFilmBacking")
@ViewScoped
public class CreateFilmBacking implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EJB
    private FilmFacade filmFacade;
    
    @EJB
    private ActorFacade actorFacade;
    
    private String title;
    private Integer releaseYear;
    private Language language;
    private Language origiLanguage;
    private Short length;
    private String rating;
    private BigDecimal rentalRate;
    private Short rentalDuration;
    private BigDecimal replacementCost;
    private String description;
    private List<Category> categoryList;
    private Actor actor;
    private String actorFirstName;
    private String actorLastName;
    private Set<Actor> actorList;
    
    public CreateFilmBacking() {
    }
    
    @PostConstruct
    public void init() {
        actor = new Actor();
        actor.setLastUpdate(new Date());
        actorList = new HashSet<>();
    }
    
    public FilmFacade getFilmFacade() {
        return filmFacade;
    }

    public void setFilmFacade(FilmFacade filmFacade) {
        this.filmFacade = filmFacade;
    }

    public ActorFacade getActorFacade() {
        return actorFacade;
    }

    public void setActorFacade(ActorFacade actorFacade) {
        this.actorFacade = actorFacade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getOrigiLanguage() {
        return origiLanguage;
    }

    public void setOrigiLanguage(Language origiLanguage) {
        this.origiLanguage = origiLanguage;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Short getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(Short rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public BigDecimal getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
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

    public Set<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(Set<Actor> actorList) {
        this.actorList = actorList;
    }
    
    public String reInitActor() {
        this.actor = new Actor();
        actor.setLastUpdate(new Date());
        System.out.println("reInitActor is invoked");
        return null;
    }
    
    public String createFilm() {        
        Film film = new Film();
        film.setLastUpdate(new Date());
        film.setTitle(title);
        film.setDescription(description);
        film.setReleaseYear(releaseYear);
        film.setLanguage(language);
        film.setOriginalLanguage(origiLanguage);
        film.setLength(length);
        film.setRating(rating);
        film.setRentalRate(rentalRate);
        film.setRentalDuration(rentalDuration);
        film.setReplacementCost(replacementCost);
        film.setCategoryList(categoryList);
        Set<Actor> actorSetForUpdate = new HashSet<>();
        for(Actor actorForCheck : actorList) {
            List<Actor> foundActorList = getActorFacade().findByLastName(actorForCheck.getLastName());
            if(foundActorList != null && foundActorList.size() > 0) {
                for(Actor actorInFoundActorList : foundActorList) {
                    if(actorInFoundActorList.getFirstName().equals(actorForCheck.getFirstName())) {
                        actorSetForUpdate.add(actorInFoundActorList);
                    } else {
                        film.getActorList().add(actorForCheck);
                    }
                }
            } else {
                film.getActorList().add(actorForCheck);
            }
        }
        filmFacade.create(film);
        System.out.println("Film is created");
        
        if(actorSetForUpdate.size() > 0) {
            for(Actor actorForUpdate : actorSetForUpdate) {
                film.getActorList().add(actorForUpdate);
                actorForUpdate.getFilms().add(film);
            }
            filmFacade.edit(film);
            System.out.println("Film is updated.");
        }
        
        return "films?faces-redirect=true";
    } 
}
