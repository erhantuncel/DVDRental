package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Category;
import com.erhan.dvdrental.jpa.facade.CategoryFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("categoryController")
@SessionScoped
public class CategoryController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CategoryFacade categoryFacade;
    private List<Category> categoryList = null;

    public CategoryController() {
    }

    public CategoryFacade getCategoryFacade() {
        return categoryFacade;
    }

    public void setCategoryFacade(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
    }

    public List<Category> getCategoryList() {
        return getCategoryFacade().findAll();
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Category getCategory(Short id) {
        return getCategoryFacade().find(id);
    }
    
}
