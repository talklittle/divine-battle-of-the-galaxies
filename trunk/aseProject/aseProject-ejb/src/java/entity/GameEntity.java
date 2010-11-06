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
 * @author Administrator
 */
@Entity
public class GameEntity implements Serializable {
    @Id
    private String id;

    private int x, y , height, width, speed;
    private boolean isUp;
    private boolean isDown;
    private boolean isLeft;
    private boolean isRight;
    private boolean collision;
    private boolean stop;
    private boolean isMoving;

    public GameEntity() {
    }

    public GameEntity(int x, int y)
    {
        this.x=x;
        this.y=y;
        speed =10;
        height=50;
        width=50;
        isUp=false;
        isDown=false;
        isLeft=false;
        isRight=false;
        collision = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDown() {
        return isDown;
    }

    public int getHeight() {
        return height;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public boolean isRight() {
        return isRight;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isStop() {
        return stop;
    }

    public boolean isUp() {
        return isUp;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(getX(), getY(), getWidth(),getHeight());

    }

    public void move()
    {
        if (isMoving) {
            if (isUp&&y>0) {
                y-=speed;
                if (y % 50 == 0) {
                    isMoving = false;
                    isUp = false;
                }
            }
            if (isDown&&y<550) {
                y+=speed;
                if (y % 50 == 0) {
                    isMoving = false;
                    isDown = false;
                }
            }
            if (isLeft&&x>0) {
                x-=speed;
                if (x % 50 == 0) {
                    isMoving = false;
                    isLeft = false;
                }
            }
            if(isRight&&x<750) {
                x+=speed;
                if (x % 50 == 0) {
                    isMoving = false;
                    isRight = false;
                }
            }
        }

    }

}
