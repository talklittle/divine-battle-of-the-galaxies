/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Andrew
 */
@Entity
public class MonsterEntity extends GameEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int x, y;

    private String type; //generateItem
    private int lifespan; //ms
    private String ownerId; //collectMonster
    private String color; //collectMonster
    //whether picked up by a player
//    private boolean mode; //collectMonster
//    private int x; //generateItem and moveMonster
//    private int y; //generateItem and moveMonster

    public MonsterEntity() {
        
    }

    public MonsterEntity(int x, int y, String type, String ownerId, String color) {
        super(x,y);
        this.type = type;
        this.ownerId = ownerId;
        this.color = color;
        this.lifespan = 20000;
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


}
