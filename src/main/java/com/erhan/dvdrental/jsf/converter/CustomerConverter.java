package com.erhan.dvdrental.jsf.converter;

import com.erhan.dvdrental.entities.Customer;
import com.erhan.dvdrental.jsf.CustomerController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("customerConverter")
public class CustomerConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null || value.trim().length() > 0) {
            CustomerController customerController = (CustomerController) context.getApplication().getELResolver()
                    .getValue(context.getELContext(), null, "customerController");
            return customerController.getCustomer(Short.valueOf(value));
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null) {
            return String.valueOf(((Customer) value).getCustomerId());
        } else {
            return null;
        }
    }

}
