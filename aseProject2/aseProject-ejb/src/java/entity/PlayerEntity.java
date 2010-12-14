/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author _yy
 */
@Entity
public class PlayerEntity extends GameEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    //logIn and createAccount and changePassword
    private String password;
//    // updateHistory and showStatistics
//    private long gametotal;
//    // updateHistory and showStatistics
//    private long gamewin;
//    // updateHistory and showStatistics
//    private long starstotal;
    // long is not suitable for duration
    // updateHistory and showStatistics
    private int duration;
    
    // variables for the current game
    // gameover
    private String mode;
    // collectStars and killPlayer and collectStat and gamescoreboard
    private int stars;
    //gameStart
    private String color;
    //Start and character moveControl and regenerate
    private int x;
    private int y;
//    // collectMonster monsterTimeout
//    private Vector Mon;
    // freezePlayer and freezeTimeout
    private boolean frozen;
    private long frozentime;
    private long newGameTime;
    private long gameOverTime;


    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setFrozentime(long f_time) {
        this.frozentime = f_time;
    }

    public long getFrozentime() {
        return frozentime;
    }

    public long getGameOverTime() {
        return gameOverTime;
    }

    public void setGameOverTime(long gameOverTime) {
        this.gameOverTime = gameOverTime;
    }

    public long getNewGameTime() {
        return newGameTime;
    }

    public void setNewGameTime(long newGameTime) {
        this.newGameTime = newGameTime;
    }



}
