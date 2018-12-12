package com.erhan.dvdrental.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_groups")
public class UserGroup implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final String ADMINISTRATOR_GROUP = "Administrator";
    public static final String EMPLOYEE_GROUP = "Employee";
    
    @Id
    @Column(name = "username", nullable = false, length = 16)
    private String username;
    
    @Column(name = "groupname", nullable = false, length = 255)
    private String groupname;

    public UserGroup() {
    }
    
    public UserGroup(String username, String groupname) {
        this.username = username;
        this.groupname = groupname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.username);
        hash = 67 * hash + Objects.hashCode(this.groupname);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserGroup other = (UserGroup) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.groupname, other.groupname)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserGroups{" + "username=" + username + ", groupname=" + groupname + '}';
    }
}
