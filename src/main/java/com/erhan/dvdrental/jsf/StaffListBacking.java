package com.erhan.dvdrental.jsf;

import com.erhan.dvdrental.entities.Staff;
import com.erhan.dvdrental.jpa.facade.StaffFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("staffListBacking")
@ViewScoped
public class StaffListBacking implements Serializable{

    @EJB
    private StaffFacade staffFacade;
    private List<Staff> staffList = null;
    
    private List<Staff> filteredStaffs;

    public StaffFacade getStaffFacade() {
        return staffFacade;
    }

    public void setStaffFacade(StaffFacade staffFacade) {
        this.staffFacade = staffFacade;
    }
    
    public List<Staff> getStaffList() {
        if (staffList == null) {
            staffList = staffFacade.findAll();
        }
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public List<Staff> getFilteredStaffs() {
        return filteredStaffs;
    }

    public void setFilteredStaffs(List<Staff> filteredStaffs) {
        this.filteredStaffs = filteredStaffs;
    }
    
    
    
}
