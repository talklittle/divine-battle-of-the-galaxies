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

    // the constants representing collision type
    // i.e. which types of entities are involved in the collision
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

    /**
     * default constructor
     */
    public CollisionEventEntity () {
        
    }

    /**
     * constructor initializing the important fields.
     * @param x
     * @param y
     * @param collisionType
     * @param id1
     * @param id2
     * @param eventTimeMillis
     * @param durationMillis
     */
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

    /**
     *
     * @return the type of collision
     */
    public int getCollisionType() {
        return collisionType;
    }

    /**
     *
     * @param collisionType the type of collision
     */
    public void setCollisionType(int collisionType) {
        this.collisionType = collisionType;
    }

    /**
     *
     * @return the event duration in milliseconds
     */
    public long getEventDurationMillis() {
        return eventDurationMillis;
    }

    /**
     *
     * @param eventDurationMillis the event duration in milliseconds
     */
    public void setEventDurationMillis(long eventDurationMillis) {
        this.eventDurationMillis = eventDurationMillis;
    }

    /**
     *
     * @return the time when event began
     */
    public long getEventTime() {
        return eventTime;
    }

    /**
     *
     * @param eventTime the time when event began
     */
    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    /**
     *
     * @return id of the first entity involved in collision
     */
    public String getGameEntityId1() {
        return gameEntityId1;
    }

    /**
     *
     * @param gameEntityId1 id of the first entity involved
     */
    public void setGameEntityId1(String gameEntityId1) {
        this.gameEntityId1 = gameEntityId1;
    }

    /**
     *
     * @return id of the first entity involved in collision involved
     */
    public String getGameEntityId2() {
        return gameEntityId2;
    }

    /**
     *
     * @param gameEntityId2 id of the first entity involved
     */
    public void setGameEntityId2(String gameEntityId2) {
        this.gameEntityId2 = gameEntityId2;
    }



}
