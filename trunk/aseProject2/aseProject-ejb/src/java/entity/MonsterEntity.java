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

    private String type;
    private String ownerId;
    private String color;

    private int timeToHatch;    //ms

    public MonsterEntity() {
        
    }

    public MonsterEntity(int x, int y, String type) {
        super(x,y);
        this.type = type;
        this.timeToHatch = 10000;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
