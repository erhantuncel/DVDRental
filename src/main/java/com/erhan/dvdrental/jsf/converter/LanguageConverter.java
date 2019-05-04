package com.erhan.dvdrental.jsf.converter;

import com.erhan.dvdrental.entities.Language;
import com.erhan.dvdrental.jsf.LanguageController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("languageConverter")
public class LanguageConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.length() == 0) {
            return null;
        }
        LanguageController languageController = (LanguageController) context.getApplication().getELResolver()
                .getValue(context.getELContext(), null, "languageController");
        return languageController.getLanguage(Short.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) {
            return "";
        }
        if(value instanceof Language) {
            Language o = (Language) value;
            return o.getLanguageId().toString();
        } else {
            return "0";
        }
    }

}
