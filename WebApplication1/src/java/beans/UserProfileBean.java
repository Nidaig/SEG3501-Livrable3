/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.persistence.Query;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.UserProfile;

/**
 *
 * @author ssome
 */
@Named(value = "userProfileBean")
@SessionScoped
public class UserProfileBean implements Serializable {
    /**
     * Internal class to represent images prior to persisting
     */
    private String userID;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isSupervisor;
    private List<UserProfile> lookupResults;
    private String addstatus;

    @PersistenceContext(unitName = "TMSPU7223444")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    /**
     * Creates a new instance of UserProfileBean
     */
    public UserProfileBean() {
    }
    
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

    public String getUserID(){
        return userID;
    }
    
    public void setUserID(String id){
        this.userID=id;
    }
    /**
     * @return the phone
     */
    
     public String getAddstatus() {
        return addstatus;
    }

    public void setAddstatus(String addstatus) {
        this.addstatus = addstatus;
    }
    
    public void setLookupResults(List<UserProfile> results) {
        this.lookupResults = results;
    }
    
    public List<UserProfile> getLookupResults() {
        return lookupResults;
    }
    // show results if any
    public boolean getShowResults() {
        return (lookupResults != null) && !lookupResults.isEmpty();
    }
    // show message if no result
    public boolean getShowMessage() {
        return (lookupResults != null) && lookupResults.isEmpty();
    }
    
    public void updateUserBean(UserProfile user){
        this.setUserID(user.getUserID());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
        this.setIsSupervisor(user.getIsSupervisor());
        return;
    }
    
    /**
     * Add the user to the database
     * @param actionEvent
     * @return 
     */
    public String searchUser(ActionEvent actionEvent){
        
       Query query = em.createQuery(
                "SELECT u FROM UserProfile u" +
                " WHERE u.userID = :userID");
            query.setParameter("userID",userID);
            
            updateUserBean((UserProfile) query.getSingleResult());
        
            return null;
    }
    
    
    
    public String doRegister(ActionEvent actionEvent) {
        UserProfile profile = new UserProfile(userID, email, password, firstName, lastName, isSupervisor);
        
        try {
           persist(profile); 
           String msg = "User Profile Created Successfully";
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
           FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
           FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
           FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
        } catch(RuntimeException e) {
           String msg = "Error While Creating User Profile";
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
           FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
        }
        return null;
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

}
