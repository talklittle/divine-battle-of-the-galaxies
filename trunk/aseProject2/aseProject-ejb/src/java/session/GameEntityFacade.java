
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
public class GameEntityFacade extends AbstractFacade<GameEntity> implements GameEntityFacadeRemote {

    @PersistenceContext(unitName = "aseProject-ejbPU")
    private EntityManager em;
    public static final long PUBLISH_INTERVAL_MILLIS = 50;
    public static final int INIT_STARS = 15;
    public static final int INIT_MONSTERS_KILL = 5;
    public static final int INIT_MONSTERS_FREEZE = 5;
    private int numStars = 0;
    private int numMonsters = 0;
    private long lastCollisionTime = 0;
    private static final Object collisionTimeLock = new Object();
    private String[][] gameBoardOcc = new String[16][12];
    private Timer myTimer;
    public boolean gameOverFlag;

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
        // TODO initialize the Game Board
        // initialize Stars
        Random rand = new Random();
        int x, y;
        StarEntity star;
        for (int i = 0; i < INIT_STARS; i++) {
            x = rand.nextInt(15) * 50;
            y = rand.nextInt(11) * 50;
            star = new StarEntity(x, y);
            star.setId("star-" + numStars);
            numStars += 1;
            if (gameBoardOcc[x / 50][y / 50] == null) {
                gameBoardOcc[x / 50][y / 50] = star.getId();
            } else {
                i -= 1;
            }
            create(star);
        }
        // initialize Monsters
        MonsterEntity monster;
        for (int i = 0; i < INIT_MONSTERS_KILL; i++) {
            x = rand.nextInt(15) * 50;
            y = rand.nextInt(11) * 50;
            monster = new MonsterEntity(x, y, "kill");
            monster.setId("monster-" + numMonsters);
            monster.setColor(Colors.COLOR_STRINGS[rand.nextInt(Colors.COLOR_STRINGS.length)]);
            numMonsters += 1;
            if (gameBoardOcc[x / 50][y / 50] == null) {
                gameBoardOcc[x / 50][y / 50] = monster.getId();
            } else {
                i -= 1;
            }
            create(monster);
        }
        for (int i = 0; i < INIT_MONSTERS_FREEZE; i++) {
            x = rand.nextInt(15) * 50;
            y = rand.nextInt(11) * 50;
            monster = new MonsterEntity(x, y, "freeze");
            monster.setId("monster-" + numMonsters);
            monster.setColor(Colors.COLOR_STRINGS[rand.nextInt(Colors.COLOR_STRINGS.length)]);
            numMonsters += 1;
            if (gameBoardOcc[x / 50][y / 50] == null) {
                gameBoardOcc[x / 50][y / 50] = monster.getId();
            } else {
                i -= 1;
            }
            create(monster);
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
                CollisionEventEntity collisionEvent = (CollisionEventEntity) gameEntity;
                long expireTime = collisionEvent.getEventTime() + collisionEvent.getEventDurationMillis();
                if (time >= expireTime) {
                    remove(collisionEvent);
                }
            }
        }
    }

    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        //boundaries
        if (toX < 0 || toY < 0 || toX >= 800 || toY >= 600) {
            return false;
        }
        return true;
    }

    @Override
    public void gameBoard() {
        List gameEntities = findAll();
        Iterator it = gameEntities.iterator();
        gameBoardOcc = new String[16][12];
        while (it.hasNext()) {
            GameEntity elem = (GameEntity) it.next();
            if (!(elem instanceof CollisionEventEntity)) {
                gameBoardOcc[elem.getX() / 50][elem.getY() / 50] = elem.getId();
            }
        }
        /*for (int i = 0; i < 16; i++) {
        for (int j = 0; j < 12; j++) {
        System.out.println(i + " " + j + " " + gameBoardOcc[i][j]);
        }
        }*/
    }

    public boolean playerLogic(PlayerEntity player, int toX, int toY) {
        if (!player.isFrozen()) {
            if (gameBoardOcc[toX / 50][toY / 50] == null) {
                player.setX(toX);
                player.setY(toY);
                edit(player);
            } else {
                if (gameBoardOcc[toX / 50][toY / 50].startsWith("star")) {
                    StarEntity star = (StarEntity) find(gameBoardOcc[toX / 50][toY / 50]);

                    CollisionEventEntity collision = new CollisionEventEntity(toX, toY,
                            CollisionEventEntity.COLLISION_PLAYER_STAR,
                            player.getId(),
                            star.getId(),
                            System.currentTimeMillis(),
                            500);
                    long currentTimeMillis = System.currentTimeMillis();
                    synchronized (collisionTimeLock) {
                        if (currentTimeMillis <= lastCollisionTime) {
                            currentTimeMillis = lastCollisionTime + 1;
                        }
                        collision.setId("collision-" + currentTimeMillis);
                    }
                    create(collision);

                    player.setStars(player.getStars() + 1);
                    player.setX(toX);
                    player.setY(toY);
                    edit(player);

                    remove(star);
                    int starx = 0;
                    int stary = 0;
                    do {
                        Random starnew = new Random();
                        starx = starnew.nextInt(16);
                        stary = starnew.nextInt(12);
                    } while (gameBoardOcc[starx][stary] != null);
                    star.setX(starx * 50);
                    star.setY(stary * 50);
                    create(star);
                }

                if (gameBoardOcc[toX / 50][toY / 50].startsWith("monster")) {
                    MonsterEntity monster = (MonsterEntity) find(gameBoardOcc[toX / 50][toY / 50]);
                    if (monster.getType().equals("kill")) {
                        System.out.println("U GOT KILLED");

                        CollisionEventEntity collision = new CollisionEventEntity(toX, toY,
                                CollisionEventEntity.COLLISION_PLAYER_KILL,
                                player.getId(),
                                monster.getId(),
                                System.currentTimeMillis(),
                                500);
                        long currentTimeMillis = System.currentTimeMillis();
                        synchronized (collisionTimeLock) {
                            if (currentTimeMillis <= lastCollisionTime) {
                                currentTimeMillis = lastCollisionTime + 1;
                            }
                            collision.setId("collision-" + currentTimeMillis);
                        }
                        create(collision);

                        player.setX(0);
                        player.setY(0);
                        player.setStars(0);
                        edit(player);
                    } else {
                        System.out.println("U GOT FROZEN");
                        CollisionEventEntity collision = new CollisionEventEntity(toX, toY,
                                CollisionEventEntity.COLLISION_PLAYER_FREEZE,
                                player.getId(),
                                monster.getId(),
                                System.currentTimeMillis(),
                                500);
                        long currentTimeMillis = System.currentTimeMillis();
                        synchronized (collisionTimeLock) {
                            if (currentTimeMillis <= lastCollisionTime) {
                                currentTimeMillis = lastCollisionTime + 1;
                            }
                            collision.setId("collision-" + currentTimeMillis);
                        }
                        create(collision);

                        player.setFrozen(true);
                        player.setFrozentime(System.currentTimeMillis());
                        edit(player);
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean monsterLogic(MonsterEntity monster, int toX, int toY) {
        if (gameBoardOcc[toX / 50][toY / 50] != null) {
            if (gameBoardOcc[toX / 50][toY / 50].startsWith("star")) {
                System.out.println("star encountered, don't move");
                return false;
            } else if (gameBoardOcc[toX / 50][toY / 50].startsWith("monster")) {
                System.out.println("monster encountered, don't move");
                return false;
            } else {
                PlayerEntity player = (PlayerEntity) find(gameBoardOcc[toX / 50][toY / 50]);
                if (player != null) {
                    if (monster.getType().equals("kill")) {
                        System.out.println("U GOT KILLED");
                        CollisionEventEntity collision = new CollisionEventEntity(toX, toY,
                                CollisionEventEntity.COLLISION_PLAYER_KILL,
                                player.getId(),
                                monster.getId(),
                                System.currentTimeMillis(),
                                500);
                        long currentTimeMillis = System.currentTimeMillis();
                        synchronized (collisionTimeLock) {
                            if (currentTimeMillis <= lastCollisionTime) {
                                currentTimeMillis = lastCollisionTime + 1;
                            }
                            collision.setId("collision-" + currentTimeMillis);
                        }
                        create(collision);

                        player.setX(0);
                        player.setY(0);
                        player.setStars(0);
                        edit(player);
                    } else {
                        if (!player.isFrozen()) {
                            System.out.println("U GOT FROZEN");
                            CollisionEventEntity collision = new CollisionEventEntity(toX, toY,
                                    CollisionEventEntity.COLLISION_PLAYER_FREEZE,
                                    player.getId(),
                                    monster.getId(),
                                    System.currentTimeMillis(),
                                    500);
                            long currentTimeMillis = System.currentTimeMillis();
                            synchronized (collisionTimeLock) {
                                if (currentTimeMillis <= lastCollisionTime) {
                                    currentTimeMillis = lastCollisionTime + 1;
                                }
                                collision.setId("collision-" + currentTimeMillis);
                            }
                            create(collision);

                            player.setFrozen(true);
                            player.setFrozentime(System.currentTimeMillis());
                            edit(player);
                        }
                    }
                }
                return true;
            }
        } else {
            gameBoardOcc[toX / 50][toY / 50] = monster.getId();
            return true;
        }
    }

    @Override
    public boolean moveUp(String id) {
        gameBoard();
        GameEntity entity = (GameEntity) em.find(GameEntity.class, id);
        if (entity == null) {
            return false;
        }

        int fromX = entity.getX();
        int fromY = entity.getY();

        int toX = fromX;
        int toY = fromY - 50;
        if (!isValidMove(fromX, fromY, toX, toY)) {
            return false;
        }

        boolean moveOk = true;

        // DEBUG
        if (gameBoardOcc[toX / 50][toY / 50] != null) {
            System.out.println(gameBoardOcc[toX / 50][toY / 50]);
        }

        if (entity instanceof PlayerEntity) {
            return playerLogic((PlayerEntity) entity, toX, toY);
        }
        if (entity instanceof MonsterEntity) {
            moveOk = moveOk && monsterLogic((MonsterEntity) entity, toX, toY);
            if (moveOk) {
                gameBoardOcc[fromX / 50][fromY / 50] = null;
            }
        }

        entity.setX(toX);
        entity.setY(toY);
        if (moveOk) {
            edit(entity);
        }
        return true;
    }

    @Override
    public boolean moveLeft(String id) {
        gameBoard();
        GameEntity entity = (GameEntity) em.find(GameEntity.class, id);
        if (entity == null) {
            return false;
        }

        int fromX = entity.getX();
        int fromY = entity.getY();

        int toX = fromX - 50;
        int toY = fromY;
        if (!isValidMove(fromX, fromY, toX, toY)) {
            return false;
        }

        boolean moveOk = true;

        // DEBUG
        if (gameBoardOcc[toX / 50][toY / 50] != null) {
            System.out.println(gameBoardOcc[toX / 50][toY / 50]);
        }

        if (entity instanceof PlayerEntity) {
            return playerLogic((PlayerEntity) entity, toX, toY);
        }
        if (entity instanceof MonsterEntity) {
            moveOk = moveOk && monsterLogic((MonsterEntity) entity, toX, toY);
            if (moveOk) {
                gameBoardOcc[fromX / 50][fromY / 50] = null;
            }
        }
        entity.setX(toX);
        entity.setY(toY);
        if (moveOk) {
            edit(entity);
        }
        return true;
    }

    @Override
    public boolean moveDown(String id) {
        gameBoard();
        GameEntity entity = (GameEntity) em.find(GameEntity.class, id);
        if (entity == null) {
            return false;
        }

        int fromX = entity.getX();
        int fromY = entity.getY();

        int toX = fromX;
        int toY = fromY + 50;

        if (!isValidMove(fromX, fromY, toX, toY)) {
            return false;
        }

        boolean moveOk = true;

        // DEBUG
        if (gameBoardOcc[toX / 50][toY / 50] != null) {
            System.out.println(gameBoardOcc[toX / 50][toY / 50]);
        }

        if (entity instanceof PlayerEntity) {
            return playerLogic((PlayerEntity) entity, toX, toY);
        }
        if (entity instanceof MonsterEntity) {
            moveOk = moveOk && monsterLogic((MonsterEntity) entity, toX, toY);
            if (moveOk) {
                gameBoardOcc[fromX / 50][fromY / 50] = null;
            }
        }
        entity.setX(toX);
        entity.setY(toY);
        if (moveOk) {
            edit(entity);
        }
        return true;
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

        int toX = fromX + 50;
        int toY = fromY;

        if (!isValidMove(fromX, fromY, toX, toY)) {
            return false;
        }

        boolean moveOk = true;

        // DEBUG
        if (gameBoardOcc[toX / 50][toY / 50] != null) {
            System.out.println(gameBoardOcc[toX / 50][toY / 50]);
        }

        if (entity instanceof PlayerEntity) {
            return playerLogic((PlayerEntity) entity, toX, toY);
        }
        if (entity instanceof MonsterEntity) {
            moveOk = moveOk && monsterLogic((MonsterEntity) entity, toX, toY);
            if (moveOk) {
                gameBoardOcc[fromX / 50][fromY / 50] = null;
            }
        }
        entity.setX(toX);
        entity.setY(toY);
        if (moveOk) {
            edit(entity);
        }
        return true;
    }
    
    @Override
    public boolean gameOver() {
        return true;
    }
}
