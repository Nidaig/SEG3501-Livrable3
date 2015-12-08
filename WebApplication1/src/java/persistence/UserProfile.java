/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import beans.UserProfileBean;

/**
 *
 * @author ssome
 */
@Entity
@Table(name="UserProfile7223444")
public class UserProfile implements Serializable {
    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    @Id
    private String userID;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isSupervisor;
    public UserProfile() {
        
    }
    
    public UserProfile(String userID, String email, String password, String firstName, String lastName, boolean isSupervisor) {
        this.userID=userID;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        
    }
    
   public String getUserID(){
        return userID;
    }
    
    public void setUserID(String id){
        this.userID=id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserProfile)) {
            return false;
        }
        UserProfile other = (UserProfile) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.UserProfile[ userID=" + userID + " ]";
    }

    /**
     * @return the password
     */
     public boolean getIsSupervisor(){
        return isSupervisor;
    }
    
    public void setIsSupervisor(boolean supp){
        this.isSupervisor=supp;
    }
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email=email;
    }
    
    
   
    /**
     * 
     * @param userData
     * @return true if this User matches the userData bean
     */
    public boolean matches(UserProfileBean userData) {
        if (!"".equals(userData.getUserID()) && !this.getUserID().trim().equals(userData.getUserID().trim())) {
            return false;
        } 
        return true;
    }
}

    