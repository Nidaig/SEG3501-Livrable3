/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import beans.UserProfileBean;
import beans.TeamBean;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author ssome
 */
public class DBHelper {
    public static UserProfile findUser(EntityManager em,String id) {
        UserProfile u = em.find(UserProfile.class, id);
        return u;
    }
    
    public static List findUsersByID(EntityManager em,String id) {
        Query query = em.createQuery(
                "SELECT u FROM UserProfile u" +
                " WHERE u.userID = :userID");
        query.setParameter("userID",id);
        return performUserQuery(query);
    }
    
   
    
    
    private static List performUserQuery(final Query query) {
        List resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } 
        ArrayList<UserProfile> results = new ArrayList<>();
        results.addAll(resultList);
        return results;
    }

   public static boolean addUser(EntityManager em, UserTransaction utx, UserProfileBean userbean) {
        try {
            utx.begin();
            UserProfile nuser = new UserProfile();
            nuser.setEmail(userbean.getEmail());
            nuser.setFirstName(userbean.getFirstName());
            nuser.setIsSupervisor(userbean.getIsSupervisor());
            nuser.setPassword(userbean.getPassword());
            nuser.setLastName(userbean.getLastName());
            nuser.setUserID(userbean.getUserID());
            em.persist(nuser);
            utx.commit();
            return true;
        } catch (IllegalArgumentException | NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            ex.printStackTrace();
        }
        return false;
    }
   
       public static Team findTeam(EntityManager em,String id) {
        Team u = em.find(Team.class, id);
        return u;
    }
    
    public static List findTeamByID(EntityManager em,String id) {
        Query query = em.createQuery(
                "SELECT u FROM Team u" +
                " WHERE u.teamID = :teamID");
        query.setParameter("teamID",id);
        return performTeamQuery(query);
    }
    
   
    
    
    private static List performTeamQuery(final Query query) {
        List resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } 
        ArrayList<Team> results = new ArrayList<>();
        results.addAll(resultList);
        return results;
    }

   public static boolean addTeam(EntityManager em, UserTransaction utx, TeamBean teambean) {
        try {
            utx.begin();
            Team nteam = new Team();
            nteam.setCandidates(teambean.getCandidates());
            nteam.setName(teambean.getName());
            nteam.setTeamID(teambean.getTeamID());
            nteam.setTeamMembers(teambean.getTeamMembers());
            
            em.persist(nteam);
            utx.commit();
            return true;
        } catch (IllegalArgumentException | NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
