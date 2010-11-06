
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.GameEntity;
import entity.MonsterEggEntity;
import entity.PlayerEntity;
import entity.StarEntity;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class GameEntityFacade extends AbstractFacade<GameEntity> implements GameEntityFacadeRemote {

    @PersistenceContext(unitName = "aseProject-ejbPU")
    private EntityManager em;
    public static final long PUBLISH_INTERVAL_MILLIS = 50;
    public static final int INIT_STARS = 15;
    public static final int INIT_EGGS_KILL = 5;
    public static final int INIT_EGGS_FREEZE = 5;
    private int numStars = 0;
    private int numEggs = 0;
    private int numMonsters = 0;
    private String[][] gameBoardOcc = new String[16][12];
    private Timer myTimer;

    protected EntityManager getEntityManager() {
        return em;
    }

    public String[][] getOcc() {
        return gameBoardOcc;
    }

    public GameEntityFacade() {
        super(GameEntity.class);
    }

    public void initGameBoard() {
        // TODO initialize the Game Board
        // initialize Stars
        Random widthRand = new Random(19580427);
        Random heightRand = new Random(19580427);
        int x, y;
        StarEntity star;
        for (int i = 0; i < INIT_STARS; i++) {
            x = widthRand.nextInt(15) * 50;
            y = heightRand.nextInt(11) * 50;
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
        // initialize MonsterEggs
        MonsterEggEntity egg;
        for (int i = 0; i < INIT_EGGS_KILL; i++) {
            x = widthRand.nextInt(15) * 50;
            y = heightRand.nextInt(11) * 50;
            egg = new MonsterEggEntity(x, y, "kill");
            egg.setId("egg-" + numEggs);
            numEggs += 1;
            if (gameBoardOcc[x / 50][y / 50] == null) {
                gameBoardOcc[x / 50][y / 50] = egg.getId();
            } else {
                i -= 1;
            }
            create(egg);
        }
        for (int i = 0; i < INIT_EGGS_FREEZE; i++) {
            x = widthRand.nextInt(15) * 50;
            y = heightRand.nextInt(11) * 50;
            egg = new MonsterEggEntity(x, y, "freeze");
            egg.setId("egg-" + numEggs);
            numEggs += 1;
            if (gameBoardOcc[x / 50][y / 50] == null) {
                gameBoardOcc[x / 50][y / 50] = egg.getId();
            } else {
                i -= 1;
            }
            create(egg);
        }

        //setup timer for monsters
        //initTimer();
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
        while (it.hasNext()) {
            GameEntity elem = (GameEntity) it.next();
            gameBoardOcc[elem.getX() / 50][elem.getY() / 50] = elem.getId();
//            if (elem instanceof PlayerEntity) {
//                gameBoardOcc[elem.getX() / 50][elem.getY() / 50] = "player";
//            }
//            if (elem instanceof MonsterEggEntity) {
//                MonsterEggEntity elem_egg = (MonsterEggEntity) elem;
//                gameBoardOcc[elem.getX() / 50][elem.getY() / 50] = elem_egg.getType();
//            }
//            if (elem instanceof MonsterEntity) {
//                gameBoardOcc[elem.getX() / 50][elem.getY() / 50] = "monster";
//            }
//            if (elem instanceof StarEntity) {
//                gameBoardOcc[elem.getX() / 50][elem.getY() / 50] = "star";
//            }

        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 12; j++) {
                System.out.println(i + " " + j + " " + gameBoardOcc[i][j]);
            }
        }
    }

    public boolean playerLogic(PlayerEntity player, int toX, int toY) {
        if (gameBoardOcc[toX / 50][toY / 50].contains("star")) {
            player.setStars(player.getStars() + 1);
            StarEntity star = (StarEntity) find(gameBoardOcc[toX / 50][toY / 50]);
            remove(star);
        }

        if (gameBoardOcc[toX / 50][toY / 50].contains("egg")) {
            MonsterEggEntity egg = (MonsterEggEntity) find(gameBoardOcc[toX / 50][toY / 50]);
            if (egg.getType().equals("kill")) {
                System.out.println("U GOT KILLED");
                player.setX(0);
                player.setY(0);
                player.setStars(0);

                System.out.println(player.getId());
                System.out.println(player.getX());
                System.out.println(player.getY());
                edit(player);
                System.out.println(player.getId());
                System.out.println(player.getX());
                System.out.println(player.getY());

                return true;
            } else {
                player.setFrozen(true);
            }
        }
        return true;
    }

    public boolean monsterLogic(MonsterEggEntity monster, int toX, int toY) {
        if (gameBoardOcc[toX / 50][toY / 50] != null && gameBoardOcc[toX / 50][toY / 50].contains("star")) {
            System.out.println("star encountered, don't move");
            return false;
        } else {
            gameBoardOcc[toX / 50][toY / 50] = monster.getId();
            return true;
        }
    }

    public boolean moveUp(String id) {
        GameEntity entity = (GameEntity) em.find(GameEntity.class, id);
        int fromX = entity.getX();
        int fromY = entity.getY();

        int toX = fromX;
        int toY = fromY - 50;
        if (!isValidMove(fromX, fromY, toX, toY)) {
            return false;
        }

        boolean moveOk = true;
        if (gameBoardOcc[toX / 50][toY / 50] != null) {
            System.out.println(gameBoardOcc[toX / 50][toY / 50]);
            if (entity instanceof PlayerEntity) {
                playerLogic((PlayerEntity) entity, toX, toY);
                return true;
            }
        }
        if(entity instanceof MonsterEggEntity) {
            moveOk = moveOk && monsterLogic((MonsterEggEntity) entity, toX, toY);
            if(moveOk) gameBoardOcc[fromX/50][fromY/50] = null;
        }

        entity.setX(toX);
        entity.setY(toY);
        if (moveOk) {
            edit(entity);
        }
        return true;
    }

    public boolean moveLeft(String id) {
        GameEntity entity = (GameEntity) em.find(GameEntity.class, id);
        int fromX = entity.getX();
        int fromY = entity.getY();

        int toX = fromX - 50;
        int toY = fromY;
        if (!isValidMove(fromX, fromY, toX, toY)) {
            return false;
        }

        boolean moveOk = true;
        if (entity instanceof PlayerEntity && gameBoardOcc[toX / 50][toY / 50] != null) {
            System.out.println(gameBoardOcc[toX / 50][toY / 50]);
            playerLogic((PlayerEntity) entity, toX, toY);
            return true;
        } else if (entity instanceof MonsterEggEntity) {
            moveOk = moveOk && monsterLogic((MonsterEggEntity) entity, toX, toY);
            if (moveOk) {
                gameBoardOcc[fromX / 50][fromY / 50] = null;
            }
        }
        else if(entity instanceof MonsterEggEntity) {
            moveOk = moveOk && monsterLogic((MonsterEggEntity) entity, toX, toY);
            if(moveOk) gameBoardOcc[fromX/50][fromY/50] = null;
        }
        entity.setX(toX);
        entity.setY(toY);
        if (moveOk) {
            edit(entity);
        }
        return true;
    }

    public boolean moveDown(String id) {
        GameEntity entity = (GameEntity) em.find(GameEntity.class, id);
        int fromX = entity.getX();
        int fromY = entity.getY();

        int toX = fromX;
        int toY = fromY + 50;

        if (!isValidMove(fromX, fromY, toX, toY)) {
            return false;
        }

        boolean moveOk = true;
        if (gameBoardOcc[toX / 50][toY / 50] != null) {
            System.out.println(gameBoardOcc[toX / 50][toY / 50]);
            if (entity instanceof PlayerEntity) {
                playerLogic((PlayerEntity) entity, toX, toY);
                return true;
            }
        }
        if(entity instanceof MonsterEggEntity) {
            moveOk = moveOk && monsterLogic((MonsterEggEntity) entity, toX, toY);
            if(moveOk) gameBoardOcc[fromX/50][fromY/50] = null;
        }
        entity.setX(toX);
        entity.setY(toY);
        if (moveOk) {
            edit(entity);
        }
        return true;
    }

    public boolean moveRight(String id) {
        GameEntity entity = (GameEntity) find(id);
        int fromX = entity.getX();
        int fromY = entity.getY();

        int toX = fromX + 50;
        int toY = fromY;
//
        if (!isValidMove(fromX, fromY, toX, toY)) {
            return false;
        }

        boolean moveOk = true;
        if (gameBoardOcc[toX / 50][toY / 50] != null) {
            System.out.println(gameBoardOcc[toX / 50][toY / 50]);
            if (entity instanceof PlayerEntity) {
                playerLogic((PlayerEntity) entity, toX, toY);
                return true;
            }
        }
        if(entity instanceof MonsterEggEntity) {
            moveOk = moveOk && monsterLogic((MonsterEggEntity) entity, toX, toY);
            if(moveOk) gameBoardOcc[fromX/50][fromY/50] = null;
        }
        entity.setX(toX);
        entity.setY(toY);
        if (moveOk) {
            edit(entity);
        }
        return true;
    }
}
