package com.erhan.dvdrental.jpa.facade;

import com.erhan.dvdrental.entities.Film;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class FilmFacade extends AbstractFacade<Film> {
    
    @PersistenceContext(unitName = "dvdrental")
    private EntityManager em;

    public FilmFacade() {
        super(Film.class);
    }

    @Override
    protected EntityManager getEntityManager() {
       return em;
    }
    
    public List<Film> findByTitle(String title) {
        return em.createNamedQuery(Film.FIND_BY_TITLE)
                .setParameter("title", title).getResultList();
    }
    
    public List<Film> findByReleaseYear(Integer releaseYear) {
        return em.createNamedQuery(Film.FIND_BY_RELEASE_YEAR)
                .setParameter("releaseYear", releaseYear).getResultList();
    }
    
    public List<Film> findByRentalRate(BigDecimal rentalRate) {
        return em.createNamedQuery(Film.FIND_BY_RENTAL_RATE)
                .setParameter("rentalRate", rentalRate).getResultList();
    }
    
    public List<Film> findByLength(Short length) {
        return em.createNamedQuery(Film.FIND_BY_LENGTH)
                .setParameter("length", length).getResultList();
    }
    
    public List<Film> findByRating(String rating) {
        return em.createNamedQuery(Film.FIND_BY_RATING)
                .setParameter("rating", rating).getResultList();
    }
    
    public List<Film> findBySpecialFeatures(String specialFeatures) {
        return em.createNamedQuery(Film.FIND_BY_SPECIAL_FEATURES)
                .setParameter("specialFeatures", specialFeatures).getResultList();
    }
    
    public List<Film> findLastUpdatedFiveFilms() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Film> q = criteriaBuilder.createQuery(Film.class);
        Root<Film> root = q.from(Film.class);
        q.select(root);
        q.orderBy(criteriaBuilder.desc(root.get("lastUpdate")));
        TypedQuery<Film> query = em.createQuery(q);
        query.setMaxResults(5);
        return query.getResultList();
    }
}
