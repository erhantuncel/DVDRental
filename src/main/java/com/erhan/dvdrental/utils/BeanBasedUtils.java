package com.erhan.dvdrental.utils;

import javax.faces.context.FacesContext;

public class BeanBasedUtils {
    
    public static <T> T findBeanFromCurrentContext(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        return (T) context.getApplication()
                .evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
    }
}
