/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ferranb
 */
@Entity
@Table(name = "login")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Login.findAll", query = "SELECT l FROM Login l")
    , @NamedQuery(name = "Login.findByUsername", query = "SELECT l FROM Login l WHERE l.username = :username")
    , @NamedQuery(name = "Login.findByPass", query = "SELECT l FROM Login l WHERE l.pass = :pass")
    , @NamedQuery(name = "Login.findByType", query = "SELECT l FROM Login l WHERE l.type = :type")
    , @NamedQuery(name = "Login.findByIsconnected", query = "SELECT l FROM Login l WHERE l.isconnected = :isconnected")
    , @NamedQuery(name = "Login.findByUsernameAndPass", query = "SELECT l FROM Login l WHERE l.username = :username AND l.pass = :pass")
})
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Column(name = "pass")
    private String pass;
    @Column(name = "type")
    private String type;
    @Column(name = "isconnected")
    private Boolean isconnected;

    public Login() {
    }

    public Login(String username) {
        this.username = username;
    }
    
    public Login(String username, String pass, String type, Boolean isconnected) {
        this.username = username;
        this.pass = pass;
        this.type = type;
        this.isconnected = isconnected;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsconnected() {
        return isconnected;
    }

    public void setIsconnected(Boolean isconnected) {
        this.isconnected = isconnected;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Login)) {
            return false;
        }
        Login other = (Login) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Login[ username=" + username + " ]";
    }
    
}
