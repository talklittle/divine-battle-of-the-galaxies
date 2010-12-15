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

    /**
     * get player's color
     * @return
     */
    public String getColor() {
        return color;
    }

    /**
     * set player's color
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * get frozen duration
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * set frozen duration
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * is the player frozen?
     * @return
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * the player is frozen
     * @param frozen
     */
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * what mode is player in (game over?) (UNUSED)
     * @return
     */
    public String getMode() {
        return mode;
    }

    /**
     * player mode (game over?) (UNUSED)
     * @param mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * get the number of stars player is holding
     * @return
     */
    public int getStars() {
        return stars;
    }

    /**
     * set the number of stars player is holding
     * @param stars
     */
    public void setStars(int stars) {
        this.stars = stars;
    }

    /**
     * set the time when player got frozen
     * @param f_time
     */
    public void setFrozentime(long f_time) {
        this.frozentime = f_time;
    }

    /**
     * get the time when player got frozen
     * @return
     */
    public long getFrozentime() {
        return frozentime;
    }

    /**
     * get the time when player entered game over
     * @return
     */
    public long getGameOverTime() {
        return gameOverTime;
    }

    /**
     * set the time when player entered game over
     * @param gameOverTime
     */
    public void setGameOverTime(long gameOverTime) {
        this.gameOverTime = gameOverTime;
    }

    /**
     * get the time when player started new game
     * @return
     */
    public long getNewGameTime() {
        return newGameTime;
    }

    /**
     * set the time when player started new game
     * @param newGameTime
     */
    public void setNewGameTime(long newGameTime) {
        this.newGameTime = newGameTime;
    }



}
