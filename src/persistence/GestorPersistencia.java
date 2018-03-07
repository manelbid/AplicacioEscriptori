/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author ferranb
 */
public class GestorPersistencia {
    EntityManagerFactory emf;
    EntityManager entityManager;
    
    private String uPersistencia;

    public GestorPersistencia(String uPersistencia){
        this.uPersistencia = uPersistencia;
    }
     
    public void obrir() throws UtilitatPersistenciaException {
        try{
            System.out.println("OBRIR :" + uPersistencia);
            emf = Persistence.createEntityManagerFactory(uPersistencia);                   
            //emf.getProperties().values();
            entityManager = emf.createEntityManager();
            iniciaTransaccio();
            
        }catch(Exception ex){
            tractarExcepcio(ex);
        }
    }
    
    public void tancar() {
        try{
            entityManager.close();
            emf.close();
        }catch(Exception e){
            
        }
    }
    
    public void gravaCanvis() throws UtilitatPersistenciaException {
        try{
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
        }catch(Exception e){
           tractarExcepcio(e);
        }
    }
    
    public void anullaCanvis() throws UtilitatPersistenciaException {
        try{
            entityManager.getTransaction().rollback();
            entityManager.getTransaction().begin();
        }catch(Exception e){
           tractarExcepcio(e);
        }
    }
    
    private void iniciaTransaccio() {
        try{
            entityManager.getTransaction().begin();
        }catch(Exception e){
           tractarExcepcio(e);
       }
    }
    
    private void tractarExcepcio(Exception e) {
        e.printStackTrace();
    }
    
    public List<Login> findUsersByNameAndPass(String dades) throws UtilitatPersistenciaException {
        Query q = entityManager.createNamedQuery("Login.findByUsernameAndPass");
        q.setParameter("username",dades.split("-")[1]);
        q.setParameter("pass",dades.split("-")[2]);
        return (List<Login>)q.getResultList(); 
    }
    
    public List<Login> findUsersByNameAndPassAndType(String dades) throws UtilitatPersistenciaException {
        Query q = entityManager.createNamedQuery("Login.findByUsernameAndPassAndType");
        q.setParameter("username",dades.split("-")[1]);
        q.setParameter("pass",dades.split("-")[2]);
        q.setParameter("type", dades.split("-")[3]);
        return (List<Login>)q.getResultList(); 
    }
    
    public void inserirLogin(Login log) throws UtilitatPersistenciaException {
        try{
            entityManager.persist(log);
            entityManager.flush();        
        }catch(Exception e){
            tractarExcepcio(e);            
        }
    }
    
    public Boolean userIsEmpty(String dades){
        Query q = entityManager.createNamedQuery("Login.findByUsernameAndType");
        q.setParameter("username",dades.split("-")[1]);     
        q.setParameter("type",dades.split("-")[3]);
        boolean res = q.getResultList().isEmpty();        
        return res;
    }

    public void updateSessionCode(String sessionCode, String dades){        
        Query q = entityManager.createQuery("UPDATE Login l SET l.lastsessioncode = :lastsessioncode WHERE l.username = :username AND l.type = :type");
        q.setParameter("lastsessioncode", sessionCode);
        q.setParameter("username", dades.split("-")[1]);
        q.setParameter("type", dades.split("-")[3]);
        q.executeUpdate();
        entityManager.flush();
    }
    
    public void updateIsConnected(String dades){        
        Query q = entityManager.createQuery("UPDATE Login l SET l.isconnected = :isconnected WHERE l.username = :username AND l.type = :type");
        q.setParameter("isconnected", Boolean.TRUE);
        q.setParameter("username", dades.split("-")[1]);
        q.setParameter("type", dades.split("-")[3]);
        q.executeUpdate();
        entityManager.flush();
    }
    
    public void updateIsNotConnected(String dades){        
        Query q = entityManager.createQuery("UPDATE Login l SET l.isconnected = :isconnected WHERE l.username = :username AND l.type = :type");
        q.setParameter("isconnected", Boolean.FALSE);
        q.setParameter("username", dades.split("-")[1]);
        q.setParameter("type", dades.split("-")[3]);
        q.executeUpdate();
        entityManager.flush();
    }

}
