/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.DBHelper;
import beans.TeamBean;
import beans.UserProfileBean;
import javax.faces.event.ActionEvent;
import persistence.Team;
import persistence.UserProfile;
//fait pas nicolas
/**
 *
 * @author Nick
 */
@Named(value = "controleur")
@RequestScoped

public class Controleur implements Serializable {
    @Inject
    private UserProfileBean user;
    private TeamBean team;
    private static int teamNumbers=0;
    private static int memberMax;
    private static int memberMin;

    @PersistenceContext
    EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;
    
    /**
     * Creates a new instance of Controleur
     */
    public Controleur() {
    }
    
    public void selectTeam(String tID){
        
    }
    
    public void setMin(int x){
        memberMin=x;
    }
    public void setMax(int x){
        memberMax=x;
    }
    
    public void acceptMember(UserProfile user){
        if(team.getSize()<memberMax){
            team.acceptCandidate(user);
        }
        else{
            return;
        }
    }
    
     public void lookupUser() {
       List<UserProfile> results = new ArrayList();
       if (!"".equals(user.getUserID())) {
            // lookup by id
            results = getUserById(em,user);
       }
       user.setLookupResults(results);
    }
    public void addUser() {
        if (DBHelper.addUser(em,utx,user)) {
            user.setAddstatus("The User Was Successfuly Added");
        } else {
            user.setAddstatus("Addition of the User Failed");
        }
    }
    
    public void chooseTeam(Team t){
        team.updateTeamBean(t);
    }
    
    public void chooseUser(UserProfile u){
        user.updateUserBean(u);
    }
    
    
     public void lookupTeam() {
       List<Team> results = new ArrayList();
       if (!"".equals(team.getTeamID())) {
            // lookup by id
            results = getTeamById(em,team);
       }
       team.setLookupResults(results);
    }
    public void addTeam(ActionEvent actionEvent) {
        if(team.getTeamID()==null){
            team.setTeamID("1"+teamNumbers);
            teamNumbers++;
        }
        if (DBHelper.addTeam(em,utx,team)) {
            team.setAddstatus("The team Was Successfuly Added");
        } else {
            team.setAddstatus("Addition of the team Failed");
        }
        team.addTeamMember(user.getUser(actionEvent));
    }
    
        /**
     * Find a user by id and check if any that the other fields are valid
     */
    private List<UserProfile> getUserById(EntityManager em,UserProfileBean userData) {
        ArrayList<UserProfile> result = new ArrayList<>();
        UserProfile user = DBHelper.findUser(em,userData.getUserID());
        if (user != null && user.matches(userData)) {
            result.add(user);  
        }
        return result;
    }
    
    

    
    private List<Team> getTeamById(EntityManager em,TeamBean teamData) {
        ArrayList<Team> result = new ArrayList<>();
        Team team = DBHelper.findTeam(em,teamData.getTeamID());
        if (team != null && team.matches(teamData)) {
            result.add(team);  
        }
        return result;
    }
   /* private List<UserProfile> getUsersByName(EntityManager em,UserProfileBean userData) {
       List<UserProfile> allresults = DBHelper.findUsersByName(em,userData.getName());
       if (allresults == null) return null;
       return checkResults(allresults,userData);          
    }

    
    
    private List<UserProfile> checkUserResults(List<UserProfile> allresults,UserProfileBean userData) {
        ArrayList<UserProfile> results = new ArrayList<>();
        for (UserProfile user: allresults) {
            if (user.matches(userData)) results.add(user);
        }
        return results;
    }*/
    
    
}
