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
import persistence.Team;

/**
 *
 * @author ssome
 */
@Named(value = "teamBean")
@SessionScoped
public class TeamBean implements Serializable {
    /**
     * Internal class to represent images prior to persisting
     */
    private String teamID;
    private String name;
    private UserProfile[] teamMembers;
    private UserProfile[] candidates;
    private List<Team> lookupResults;
    @PersistenceContext(unitName = "TMSPU7223444")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    /**
     * Creates a new instance of UserProfileBean
     */
    public TeamBean() {
        teamMembers=null;
        candidates=null;
    }
    
    public String getTeamID(){
        return teamID;
    }
    
    public void setTeamID(String id){
        this.teamID=id;
    }
    public String getName(){
        return teamID;
    }
    
    public void setName(String name){
        this.name=name;
    }
    public  UserProfile[] getTeamMembers(){
        return teamMembers;
    }
    
    public void setTeamMembers(UserProfile[] members){
        this.teamMembers=members;
    }
    
    public void addTeamMember(UserProfile user){
        if(user==null){
            return;
        }
        int size = teamMembers.length;

        for(int i=0;i<size;i++){
            if(teamMembers[i]==user){
                //user already a member of the team
                return;
            }
        }
        UserProfile[] temp=new UserProfile[size+1];
        for(int i=0;i<size;i++){
            temp[i]=teamMembers[i];
        }
        temp[size]=user;
        teamMembers=temp;
        return;
    }
    public UserProfile[] getCandidates(){
        return candidates;
    }
    
    public void setCandidates(UserProfile[] candidates){
        this.candidates=candidates;
    }
    
    public void addCandidate(UserProfile user){
        int size = candidates.length;

        for(int i=0;i<size;i++){
            if(candidates[i]==user){
                //user already a candadate of the team
                return;
            }
        }
        UserProfile[] temp=new UserProfile[size+1];
        for(int i=0;i<size;i++){
            temp[i]=candidates[i];
        }
        temp[size]=user;
        candidates=temp;
        return;
    }
    public void removeCandidate(UserProfile user){
            int size = candidates.length;
            boolean flag=true;
            UserProfile[] temp=new UserProfile[size-1];

            for(int i=0;i<size;i++){
                if(candidates[i]==user){
                    flag=false;
                }
                else{
                    temp[i]=candidates[i];

                }
        }
            if(flag){
                return;
            }
            else{
                candidates=temp;
                return;
            }
            

        
    }
    
       public void setLookupResults(List<Team> results) {
        this.lookupResults = results;
    }
    
    public List<Team> getLookupResults() {
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
    
    
    public void updateTeamBean(Team team){
        this.setTeamID(team.getTeamID());
        this.setName(team.getName());
        this.setTeamMembers(team.getTeamMembers());
        this.setCandidates(team.getCandidates());
        
        return;
    }
    
    /**
     * Add the user to the database
     * @param actionEvent
     * @return 
     */
    public String searchUser(ActionEvent actionEvent){
        
       Query query = em.createQuery(
                "SELECT u FROM Team u" +
                " WHERE u.teamID = :teamID");
            query.setParameter("teamID",teamID);
            
            updateTeamBean((Team) query.getSingleResult());
        
            return null;
    }
    
    
    
    public String doRegister(ActionEvent actionEvent) {
        Team profile = new Team(teamID, name, teamMembers, candidates);
        
        try {
           persist(profile); 
           String msg = "Team Profile Created Successfully";
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
           FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
           FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
           FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
        } catch(RuntimeException e) {
           String msg = "Error While Creating Team Profile";
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
