package com.erhan.dvdrental.jsf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("errorController")
@RequestScoped
public class ErrorController implements Serializable {
    Map<String, String> errorMap = new HashMap<>();
    
    public Map<String, String> getErrorMap() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
        Integer errorCode = (Integer) requestMap.get("javax.servlet.error.status_code");
        String errorDescription = (String) requestMap.get("javax.servlet.error.message");
        String errorPath = (String) requestMap.get("javax.servlet.error.request_uri");
        errorMap.put("errorCode", errorCode.toString());
        errorMap.put("errorPath", errorPath);
        errorMap.put("errorDescription", errorDescription);
        return this.errorMap;
    }
}
