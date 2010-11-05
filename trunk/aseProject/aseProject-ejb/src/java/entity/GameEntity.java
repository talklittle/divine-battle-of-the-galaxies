/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.awt.Rectangle;

/**
 *
 * @author Administrator
 */
public class GameEntity {
    int x, y , height, width, speed;
    public boolean up, down, left, right, collision, stop;
    public boolean isMoving;

    public GameEntity() {
    }

    public GameEntity(int x, int y)
    {
        this.x=x;
        this.y=y;
        speed =10;
        height=50;
        width=50;
        up=false;
        down=false;
        left=false;
        right=false;
        collision = false;
    }

    public boolean isDown() {
        return down;
    }

    public int getHeight() {
        return height;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isStop() {
        return stop;
    }

    public boolean isUp() {
        return up;
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
            if (up&&y>0) {
                y-=speed;
                if (y % 50 == 0) {
                    isMoving = false;
                    up = false;
                }
            }
            if (down&&y<550) {
                y+=speed;
                if (y % 50 == 0) {
                    isMoving = false;
                    down = false;
                }
            }
            if (left&&x>0) {
                x-=speed;
                if (x % 50 == 0) {
                    isMoving = false;
                    left = false;
                }
            }
            if(right&&x<750) {
                x+=speed;
                if (x % 50 == 0) {
                    isMoving = false;
                    right = false;
                }
            }
        }

    }

}
