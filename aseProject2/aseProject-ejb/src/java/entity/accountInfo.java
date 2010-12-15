/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity representing account (username and password)
 * @author _yy
 */
@Entity
public class accountInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;


    private String psw;

    /**
     * get account password
     * @return
     */
    public String getPsw() {
        return psw;
    }

    /**
     * set account password
     * @param psw
     */
    public void setPsw(String psw) {
        this.psw = psw;
    }

    /**
     * get account id (username)
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * set account id (username)
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the
        // case the id fields are not set
        if (!(object instanceof accountInfo)) {
            return false;
        }
        accountInfo other = (accountInfo) object;
        if ((this.id == null && other.id != null)
            || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.accountInfo[id=" + id + "]";
    }

}
