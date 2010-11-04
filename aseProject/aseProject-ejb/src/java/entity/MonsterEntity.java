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
public class MonsterEntity extends GameEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private Point location;

    private String type; //generateItem
    private int lifespan; //generateItem
    private String ownerId; //collectMonster
    private String color; //collectMonster
    //whether picked up by a player
//    private boolean mode; //collectMonster
//    private int x; //generateItem and moveMonster
//    private int y; //generateItem and moveMonster

    public MonsterEntity(int x, int y, String type, int lifespan, String ownerId, String color) {
        super(x, y);
        this.type = type;
        this.lifespan = lifespan;
        this.ownerId = ownerId;
        this.color = color;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!(object instanceof MonsterEntity)) {
            return false;
        }
        MonsterEntity other = (MonsterEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MonsterEntity[id=" + id + "]";
    }

}
