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
import beans.TeamBean;
/**
 *
 * @author ssome
 */
@Entity
@Table(name="Team7223444")
public class Team implements Serializable {
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
    private String teamID;
    private String name;
    private UserProfile[] teamMembers;
    private UserProfile[] candidates;
    public Team() {
        
    }
    
    public Team(String teamID, String name,UserProfile[] teamMembers,UserProfile[] candidates) {
        this.teamID=teamID;
        this.name = name;
        this.teamMembers = teamMembers;
        this.candidates = candidates;
        
    }
    
   public String getTeamID(){
        return teamID;
    }
    
    public void setTeamID(String id){
        this.teamID=id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamID != null ? teamID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.teamID == null && other.teamID != null) || (this.teamID != null && !this.teamID.equals(other.teamID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Team[ teamID=" + teamID + " ]";
    }

    /**
     * @return the password
     */
         
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
    public boolean matches(TeamBean teamData) {
        if (!"".equals(teamData.getTeamID()) && !this.getTeamID().trim().equals(teamData.getTeamID().trim())) {
            return false;
        } 
        return true;
    }
}