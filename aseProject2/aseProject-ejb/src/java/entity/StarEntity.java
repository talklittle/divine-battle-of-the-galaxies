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
public class StarEntity extends GameEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int x, y;

    public StarEntity() {
        
    }

    public StarEntity(int x, int y) {
        super(x,y);
    }

}
