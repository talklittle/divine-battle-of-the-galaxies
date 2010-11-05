/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Administrator
 */
@Entity
public class PlayerEntity extends GameEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int id;
    private String username; // administrator collectAll and logIn and createAccount and changePassword and gamescoreboard
    private String password; //logIn and createAccount and changePassword
//    private long gametotal; //updateHistory and showStatistics
//    private long gamewin; //updateHistory and showStatistics
//    private long starstotal; //updateHistory and showStatistics
    //long is not suitable for duration
    private int duration; //updateHistory and showStatistics
    //variables for the current game
    private String mode; //gameover
    private int stars; //collectStars and killPlayer and collectStat and gamescoreboard
    private String color; //gameStart
    private int x; //Start and character moveControl and regenerate
    private int y; //Start and character moveControl and regenerate
//    private Vector Mon;  //collectMonster monsterTimeout
    private boolean frozen; // freezePlayer and freezeTimeout

    public PlayerEntity() {
        
    }

    public PlayerEntity(int x, int y, String mode) {
        super(x,y);
        this.mode = mode;
        this.frozen = false;
        this.duration = 0;
        this.stars = 0;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }

//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof userEntity)) {
//            return false;
//        }
//        userEntity other = (userEntity) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public String toString() {
        return "entity.userEntity[username=" + username + "]";
    }

}
