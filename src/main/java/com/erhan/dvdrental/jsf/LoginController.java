package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Staff;
import com.erhan.dvdrental.jpa.facade.StaffFacade;
import java.io.Serializable;
import java.security.Principal;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {

    private static final Logger log = Logger.getLogger(LoginController.class.getName());
    
    @Inject
    StaffFacade staffFacade;
    
    private String username;
    private String password;
    
    private Staff staff;
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(username, password);
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed!", null));
            return "login";
        }
        
        Principal principal = request.getUserPrincipal();
        this.staff = staffFacade.findByUserName(principal.getName());
        log.info("Authentication done for user : " + principal.getName());
        
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        sessionMap.put("Staff", staff);
        
        if(request.isUserInRole("Administrator")) {
            sessionMap.put("userRole", "Administrator");
            return "views/index?faces-redirect=true";
        }else if(request.isUserInRole("Employee")) {
            sessionMap.put("userRole", "Employee");
            return "views/index?faces-redirect=true";
        } else {
            return "login";
        }
    }
    
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        try {
            this.staff = null;
            request.logout();
            ((HttpSession)context.getExternalContext().getSession(false)).invalidate();
            log.info("Logout done for user : " + principal.getName());
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout the user!", e);
        }
        return "/login?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Staff getAuthenticatedStaff() {
        return staff;
    }
}
