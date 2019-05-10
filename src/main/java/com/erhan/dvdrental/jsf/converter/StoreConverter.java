package com.erhan.dvdrental.jsf.converter;

import com.erhan.dvdrental.entities.Store;
import com.erhan.dvdrental.jsf.StoreController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("storeConverter")
public class StoreConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.length() == 0 ) {
            return null;
        }
        StoreController storeController = (StoreController) context.getApplication().getELResolver()
                .getValue(context.getELContext(), null, "storeController");
        return storeController.getStore(Short.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) {
            return "";
        }
        if(value instanceof Store) {
            Store o = (Store) value;
            return o.getStoreId().toString();
        } else {
            return "0";
        }
    }

}
