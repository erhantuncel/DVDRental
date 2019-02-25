package com.erhan.dvdrental.jsf;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class StaffController implements Serializable {

    private static final long serialVersionUID = 1L;

    public StreamedContent getStaffPictureAsStreamedContent(byte[] picture) {
        DefaultStreamedContent imageStream = null;
        if(picture != null) {
            imageStream = new DefaultStreamedContent(new ByteArrayInputStream(picture), "image/png");
        }
        return imageStream;
    }
    
    

}
