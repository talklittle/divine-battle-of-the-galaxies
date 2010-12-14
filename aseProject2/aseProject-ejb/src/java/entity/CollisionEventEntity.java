/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 * An Entity used to represent collision events,
 * used by game clients to generate sound effects etc.
 * @author Andrew
 */
@Entity
public class CollisionEventEntity extends GameEntity
                                  implements Serializable {

    public static final int COLLISION_PLAYER_STAR = 0;
    public static final int COLLISION_PLAYER_KILL = 1;
    public static final int COLLISION_PLAYER_FREEZE = 2;
    public static final int COLLISION_PLAYER_PLAYER = 3;
    public static final int COLLISION_PLAYER_EGG = 4;

    private static final long serialVersionUID = 1L;

    private int collisionType;
    private long eventTime;
    private long eventDurationMillis;
    // The 2 GameEntities involved in collision
    private String gameEntityId1;
    private String gameEntityId2;

    public CollisionEventEntity () {
        
    }

    public CollisionEventEntity(int x, int y,
                                int collisionType,
                                String id1, String id2,
                                long eventTimeMillis,
                                long durationMillis) {
        super(x, y);
        this.collisionType = collisionType;
        this.gameEntityId1 = id1;
        this.gameEntityId2 = id2;
        this.eventTime = eventTimeMillis;
        this.eventDurationMillis = durationMillis;
    }

    public int getCollisionType() {
        return collisionType;
    }

    public void setCollisionType(int collisionType) {
        this.collisionType = collisionType;
    }

    public long getEventDurationMillis() {
        return eventDurationMillis;
    }

    public void setEventDurationMillis(long eventDurationMillis) {
        this.eventDurationMillis = eventDurationMillis;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getGameEntityId1() {
        return gameEntityId1;
    }

    public void setGameEntityId1(String gameEntityId1) {
        this.gameEntityId1 = gameEntityId1;
    }

    public String getGameEntityId2() {
        return gameEntityId2;
    }

    public void setGameEntityId2(String gameEntityId2) {
        this.gameEntityId2 = gameEntityId2;
    }



}
