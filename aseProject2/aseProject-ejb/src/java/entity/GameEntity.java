/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.awt.Rectangle;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author _yy
 */
@Entity
public class GameEntity implements Serializable {

    @Id
    private String id;

    private int x, y;

    /**
     * default constructor
     */
    public GameEntity() {
    }

    /**
     * initialize the position
     * @param x
     * @param y
     */
    public GameEntity(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return the id (a String)
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id the id (a String)
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @param x the x position on grid
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @param y the y position on grid
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @return the x position on grid
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return the y position on grid
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameEntity other = (GameEntity) obj;
        if ((this.id == null) ? (other.id != null)
                              : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

}
