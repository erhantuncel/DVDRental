package com.erhan.dvdrental.jsf.converter;

import com.erhan.dvdrental.entities.Category;
import com.erhan.dvdrental.jsf.CategoryController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("categoryConverter")
public class CategoryConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.length() == 0) {
            return null;
        }
        CategoryController categoryController = (CategoryController) context.getApplication().getELResolver()
                .getValue(context.getELContext(), null, "categoryController");
        return categoryController.getCategory(Short.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null ) {
            return "";
        }
        if(value instanceof Category) {
            Category o = (Category) value;
            return o.getCategoryId().toString();
        } else {
            return "0";
        }
    }

}
