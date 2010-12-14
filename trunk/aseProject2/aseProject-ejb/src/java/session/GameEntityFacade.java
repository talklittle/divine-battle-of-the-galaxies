
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.CollisionEventEntity;
import entity.GameEntity;
import entity.MonsterEntity;
import entity.PlayerEntity;
import entity.StarEntity;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.Colors;

/**
 *
 * @author _yy
 */
@Stateless
public class GameEntityFacade extends AbstractFacade<GameEntity>
                              implements GameEntityFacadeRemote {

    @PersistenceContext(unitName = "aseProject-ejbPU")
    private EntityManager em;
    public static final long PUBLISH_INTERVAL_MILLIS = 50;
    public static final int INIT_STARS = 15;
    public static final int INIT_MONSTERS_KILL = 5;
    public static final int INIT_MONSTERS_FREEZE = 5;
    public static final int GRID_WIDTH = 16;
    public static final int GRID_HEIGHT = 12;

    private int numStars = 0;
    private int numMonsters = 0;
    private long lastCollisionTime = 0;
    private String[][] gameBoardOcc = new String[16][12];
    private Timer myTimer;
    public boolean gameOverFlag;

    private static final Random random = new Random();

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public String[][] getOcc() {
        return gameBoardOcc;
    }

    public GameEntityFacade() {
        super(GameEntity.class);
    }

    @Override
    public void initGameBoard() {
        // initialize Stars
        int x, y;
        StarEntity star;
        for (int i = 0; i < INIT_STARS; i++) {
            x = random.nextInt(GRID_WIDTH);
            y = random.nextInt(GRID_HEIGHT);
            star = new StarEntity(x, y);
            star.setId("star-" + numStars);
            if (gameBoardOcc[x][y] == null) {
                gameBoardOcc[x][y] = star.getId();
                numStars += 1;
                create(star);
            } else {
                i -= 1;
            } 
        }
        // initialize Monsters
        MonsterEntity monster;
        for (int i = 0; i < INIT_MONSTERS_KILL; i++) {
            x = random.nextInt(GRID_WIDTH);
            y = random.nextInt(GRID_HEIGHT);
            monster = new MonsterEntity(x, y, "kill");
            monster.setId("monster-" + numMonsters);
            monster.setColor(Colors.COLOR_STRINGS[
                    random.nextInt(Colors.COLOR_STRINGS.length)]);
            if (gameBoardOcc[x][y] == null) {
                gameBoardOcc[x][y] = monster.getId();
                numMonsters += 1;
                create(monster);
            } else {
                i -= 1;
            }
        }
        for (int i = 0; i < INIT_MONSTERS_FREEZE; i++) {
            x = random.nextInt(GRID_WIDTH);
            y = random.nextInt(GRID_HEIGHT);
            monster = new MonsterEntity(x, y, "freeze");
            monster.setId("monster-" + numMonsters);
            monster.setColor(Colors.COLOR_STRINGS[
                    random.nextInt(Colors.COLOR_STRINGS.length)]);
            if (gameBoardOcc[x][y] == null) {
                gameBoardOcc[x][y] = monster.getId();
                numMonsters += 1;
                create(monster);
            } else {
                i -= 1;
            }
            
        }

        //setup timer for monsters
        //initTimer();
    }

    @Override
    public void clearGameBoard() {
        List<GameEntity> gameEntities = findAll();
        for (GameEntity gameEntity : gameEntities) {
            if (!(gameEntity instanceof PlayerEntity)) {
                remove(gameEntity);
            }
        }
    }

    @Override
    public void cleanOldCollisionEvents() {
        long time = System.currentTimeMillis();
        List<GameEntity> gameEntities = findAll();
        for (GameEntity gameEntity : gameEntities) {
            if (gameEntity instanceof CollisionEventEntity) {
                CollisionEventEntity collisionEvent
                        = (CollisionEventEntity) gameEntity;
                long expireTime = collisionEvent.getEventTime()
                     + collisionEvent.getEventDurationMillis();
                if (time >= expireTime) {
                    remove(collisionEvent);
                }
            }
        }
    }

    public boolean isValidMove(int fromX, int fromY,
                               int toX, int toY) {
        int dx = Math.abs(fromX - toX);
        int dy = Math.abs(fromY - toY);
        //boundaries
        return toX >= 0 && toY >= 0 &&
               toX < GRID_WIDTH && toY < GRID_HEIGHT &&
               ((dx == 1 && dy == 0) || (dx == 0 && dy == 1));
    }

    @Override
    public void gameBoard() {
        List gameEntities = findAll();
        Iterator it = gameEntities.iterator();
        gameBoardOcc = new String[GRID_WIDTH][GRID_HEIGHT];
        while (it.hasNext()) {
            GameEntity elem = (GameEntity) it.next();
            if (!(elem instanceof CollisionEventEntity)) {
                gameBoardOcc[elem.getX()][elem.getY()] = elem.getId();
            }
        }
        /*for (int i = 0; i < 16; i++) {
        for (int j = 0; j < 12; j++) {
        System.out.println(i + " " + j + " " + gameBoardOcc[i][j]);
        }
        }*/
    }

    public boolean playerLogic(PlayerEntity player,
                               int toX, int toY) {
        if (player.isFrozen()) {
            //System.out.println("FROZEN CAN'T MOVE");
            return false;
        }
        if (gameBoardOcc[toX][toY] == null) {
            player.setX(toX);
            player.setY(toY);
            edit(player);
        } else {
            if (gameBoardOcc[toX][toY].startsWith("star")) {
                StarEntity star = (StarEntity)
                           find(gameBoardOcc[toX][toY]);

                CollisionEventEntity collision
                       = new CollisionEventEntity(toX, toY,
                       CollisionEventEntity.COLLISION_PLAYER_STAR,
                       player.getId(),
                       star.getId(),
                       System.currentTimeMillis(),
                       500);
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis <= lastCollisionTime) {
                    currentTimeMillis = lastCollisionTime + 1;
                }
                collision.setId("collision-" + currentTimeMillis);
                lastCollisionTime = currentTimeMillis;
                create(collision);

                player.setStars(player.getStars() + 1);
                player.setX(toX);
                player.setY(toY);
                edit(player);

                remove(star);
                int starx = 0;
                int stary = 0;
                do {
                    starx = random.nextInt(16);
                    stary = random.nextInt(12);
                } while (gameBoardOcc[starx][stary] != null);
                star.setX(starx);
                star.setY(stary);
                create(star);
            }

            if (gameBoardOcc[toX][toY].startsWith("monster")) {
                MonsterEntity monster = (MonsterEntity)
                        find(gameBoardOcc[toX][toY]);
                if (monster.getType().equals("kill")) {
                    monsterKillPlayer(monster, player, toX, toY);
                } else {
                    monsterFreezePlayer(monster, player, toX, toY);
                }
            }
        }
        return true;
    }

    public boolean monsterLogic(MonsterEntity monster,
                                int toX, int toY) {
        if (gameBoardOcc[toX][toY] != null) {
            if (gameBoardOcc[toX][toY].startsWith("star")) {
                return false;
            } else if (gameBoardOcc[toX][toY].startsWith("monster")) {
                return false;
            } else {
                PlayerEntity player = (PlayerEntity)
                        find(gameBoardOcc[toX][toY]);
                if (player != null) {
                    if (monster.getType().equals("kill")) {
                        monsterKillPlayer(monster, player, toX, toY);
                    } else {
                        if (!player.isFrozen()) {
                            monsterFreezePlayer(monster, player,
                                                toX, toY);
                        }
                    }
                }
                monster.setX(toX);
                monster.setY(toY);
                edit(monster);
                return true;
            }
        } else {
            gameBoardOcc[toX][toY] = monster.getId();
            monster.setX(toX);
            monster.setY(toY);
            edit(monster);
            return true;
        }
    }

    private void monsterKillPlayer(MonsterEntity monster,
                                   PlayerEntity player,
                                   int toX, int toY) {
        //System.out.println("U GOT KILLED");
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis <= lastCollisionTime) {
            currentTimeMillis = lastCollisionTime + 1;
        }
        CollisionEventEntity collision
            = new CollisionEventEntity(toX, toY,
            CollisionEventEntity.COLLISION_PLAYER_KILL,
            player.getId(),
            monster.getId(),
            currentTimeMillis,
            500);
        collision.setId("collision-" + currentTimeMillis);
        lastCollisionTime = currentTimeMillis;
        create(collision);

        player.setX(0);
        player.setY(0);
        player.setStars(0);
        edit(player);
    }

    private void monsterFreezePlayer(MonsterEntity monster,
                                     PlayerEntity player,
                                     int toX, int toY) {
        //System.out.println("U GOT FROZEN");
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis <= lastCollisionTime) {
            currentTimeMillis = lastCollisionTime + 1;
        }
        CollisionEventEntity collision
                = new CollisionEventEntity(toX, toY,
                CollisionEventEntity .COLLISION_PLAYER_FREEZE,
                player.getId(),
                monster.getId(),
                System.currentTimeMillis(),
                500);
        collision.setId("collision-" + currentTimeMillis);
        lastCollisionTime = currentTimeMillis;
        create(collision);

        player.setFrozen(true);
        player.setFrozentime(currentTimeMillis);
        edit(player);
    }

    @Override
    public boolean moveUp(String id) {
        gameBoard();
        GameEntity entity = (GameEntity) find(id);
        if (entity == null) {
            return false;
        }

        int fromX = entity.getX();
        int fromY = entity.getY();
        int toX = fromX;
        int toY = fromY - 1;

        return move(entity, fromX, fromY, toX, toY);
    }

    @Override
    public boolean moveLeft(String id) {
        gameBoard();
        GameEntity entity = (GameEntity) find(id);
        if (entity == null) {
            return false;
        }

        int fromX = entity.getX();
        int fromY = entity.getY();
        int toX = fromX - 1;
        int toY = fromY;

        return move(entity, fromX, fromY, toX, toY);
    }

    @Override
    public boolean moveDown(String id) {
        gameBoard();
        GameEntity entity = (GameEntity) find(id);
        if (entity == null) {
            return false;
        }

        int fromX = entity.getX();
        int fromY = entity.getY();
        int toX = fromX;
        int toY = fromY + 1;

        return move(entity, fromX, fromY, toX, toY);
    }

    @Override
    public boolean moveRight(String id) {
        gameBoard();
        GameEntity entity = (GameEntity) find(id);
        if (entity == null) {
            return false;
        }

        int fromX = entity.getX();
        int fromY = entity.getY();
        int toX = fromX + 1;
        int toY = fromY;

        return move(entity, fromX, fromY, toX, toY);
    }

    private boolean move(GameEntity entity,
                         int fromX, int fromY, int toX, int toY) {
        if (!isValidMove(fromX, fromY, toX, toY)) {
            return false;
        }
        // DEBUG
        if (gameBoardOcc[toX][toY] != null) {
            System.out.println(gameBoardOcc[toX][toY]);
        }

        if (entity instanceof PlayerEntity) {
            return playerLogic((PlayerEntity) entity, toX, toY);
        }
        if (entity instanceof MonsterEntity) {
            if (monsterLogic((MonsterEntity) entity, toX, toY)) {
                gameBoardOcc[fromX][fromY] = null;
                edit(entity);
            }
        }
        return true;
    }
    
    @Override
    public boolean gameOver() {
        return true;
    }
}
