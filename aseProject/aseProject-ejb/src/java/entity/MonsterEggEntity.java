/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Andrew
 */
public class MonsterEggEntity extends GameEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private int x, y;

    private String type;
    private String ownerId;
    private int timeToHatch;

    public MonsterEggEntity(int x, int y, String type) {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getTimeToHatch() {
        return timeToHatch;
    }

    public void setTimeToHatch(int timeToHatch) {
        this.timeToHatch = timeToHatch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!(object instanceof MonsterEggEntity)) {
            return false;
        }
        MonsterEggEntity other = (MonsterEggEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MonsterEggEntity[id=" + id + "]";
    }

}
