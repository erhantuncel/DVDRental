package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Language;
import com.erhan.dvdrental.jpa.facade.LanguageFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("languageController")
@SessionScoped
public class LanguageController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private LanguageFacade languageFacade;
    private Language language;
    private List<Language> languageList = null;

    public LanguageController() {
    }
    
    public LanguageFacade getLanguageFacade() {
        return languageFacade;
    }

    public void setLanguageFacade(LanguageFacade languageFacade) {
        this.languageFacade = languageFacade;
    }

    public Language getLanguage(Short id) {
        return getLanguageFacade().find(id);
    }

    public List<Language> getLanguageList() {
        return getLanguageFacade().findAll();
    }

    public void setLanguageList(List<Language> languageList) {
        this.languageList = languageList;
    }
}
