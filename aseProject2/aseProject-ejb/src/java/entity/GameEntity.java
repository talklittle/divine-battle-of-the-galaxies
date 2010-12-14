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

    private int x, y, height, width, speed;
    private boolean isUp;
    private boolean isDown;
    private boolean isLeft;
    private boolean isRight;
    private boolean collision;
    private boolean stop;
    private boolean isMoving;

    public boolean isCollision() {
        return collision;
    }

    public boolean isIsDown() {
        return isDown;
    }

    public boolean isIsLeft() {
        return isLeft;
    }

    public boolean isIsMoving() {
        return isMoving;
    }

    public boolean isIsRight() {
        return isRight;
    }

    public boolean isIsUp() {
        return isUp;
    }

    public GameEntity() {
    }

    public GameEntity(int x, int y)
    {
        this.x = x;
        this.y = y;
        speed = 10;
        height = 50;
        width = 50;
        isUp = false;
        isDown = false;
        isLeft = false;
        isRight = false;
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

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setIsDown(boolean isDown) {
        this.isDown = isDown;
    }

    public void setIsLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
    }

    public void setIsUp(boolean isUp) {
        this.isUp = isUp;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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
            if (isUp && y > 0) {
                y -= speed;
                if (y % 50 == 0) {
                    isMoving = false;
                    isUp = false;
                }
            }
            if (isDown && y < 550) {
                y += speed;
                if (y % 50 == 0) {
                    isMoving = false;
                    isDown = false;
                }
            }
            if (isLeft && x > 0) {
                x -= speed;
                if (x % 50 == 0) {
                    isMoving = false;
                    isLeft = false;
                }
            }
            if(isRight && x < 750) {
                x += speed;
                if (x % 50 == 0) {
                    isMoving = false;
                    isRight = false;
                }
            }
        }

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
