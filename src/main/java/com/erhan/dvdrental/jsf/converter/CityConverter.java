package com.erhan.dvdrental.jsf.converter;

import com.erhan.dvdrental.entities.City;
import com.erhan.dvdrental.jsf.CityController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("cityConverter")
public class CityConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.length() == 0) {
            return null;
        }
        CityController cityController = (CityController) context.getApplication().getELResolver()
                .getValue(context.getELContext(), null, "cityController");
        return cityController.getCity(Short.valueOf(value));
        
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) {
            return "";
        }
        if(value instanceof City) {
            City o = (City) value;
            return o.getCityId().toString();
        } else {
            return "0";
        }
    }

}
