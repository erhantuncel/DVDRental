package com.erhan.dvdrental.jsf.converter;

import com.erhan.dvdrental.entities.Country;
import com.erhan.dvdrental.jsf.CountryController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("countryConverter")
public class CountryConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.length() == 0) {
            return null;
        } 
       CountryController countryController = (CountryController) context.getApplication().getELResolver()
                .getValue(context.getELContext(), null, "countryController");
        return countryController.getCountry(Short.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) {
            return "";
        }
        if(value instanceof Country) {
            Country o = (Country) value;
            return o.getCountryId().toString();
        } else {
            return "0";
        }
    }

}
