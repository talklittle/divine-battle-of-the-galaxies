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
public class MonsterEntity extends GameEntity
                           implements Serializable {
    private static final long serialVersionUID = 1L;

    private String type;
    private String ownerId;
    private String color;

    private int timeToHatch;    //ms

    /**
     * default constructor
     */
    public MonsterEntity() {
        
    }

    /**
     * initialize the x, y, and monster type
     * @param x
     * @param y
     * @param type
     */
    public MonsterEntity(int x, int y, String type) {
        super(x, y);
        this.type = type;
        this.timeToHatch = 10000;
    }

    /**
     * get the owner id of the player (UNUSED)
     * @return
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * set the owner id of the player (UNUSED)
     * @param ownerId
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * get the time for monster egg to hatch (UNUSED)
     * @return
     */
    public int getTimeToHatch() {
        return timeToHatch;
    }

    /**
     * set the time for monster egg to hatch (UNUSED)
     * @param timeToHatch
     */
    public void setTimeToHatch(int timeToHatch) {
        this.timeToHatch = timeToHatch;
    }

    /**
     * get the type of monster (kill or freeze)
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * set the type of monster (kill or freeze)
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * get the color of the monster
     * @return
     */
    public String getColor() {
        return color;
    }

    /**
     * set the color of the monster
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }
}
