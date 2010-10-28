/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Administrator
 */
@Entity
public class GameEntity implements Serializable, MessageDrivenBean, MessageListener {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Timer for creating mosters from "egg" items, create the Timer when user picks up the egg.
    // Monster lifespan, create the Timer when you create the monster
    // Server timer for item creation
    // Timer for sending out the Map (framerate)
    // Timer for player frozen. create the Timer when player gets frozen.

    @Override
    public void ejbRemove() throws EJBException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {

        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GameEntity)) {
            return false;
        }
        GameEntity other = (GameEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.gameEntity[id=" + id + "]";
    }

}
