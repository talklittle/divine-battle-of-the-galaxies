/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.GameEntity;
import entity.MonsterEggEntity;
import entity.StarEntity;
import java.util.HashMap;
import java.util.Random;
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
    public static final int INIT_STARS = 5;
    public static final int INIT_EGGS_KILL = 5;
    public static final int INIT_EGGS_FREEZE = 5;
    private int numStars = 0;
    private int numEggs = 0;
    private int numMonsters = 0;
    private HashMap<String, GameEntity> gameBoard = new HashMap<String, GameEntity>();
    private boolean[][] gameBoardOcc = new boolean[16][12];

    protected EntityManager getEntityManager() {
        return em;
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
            if (!gameBoardOcc[x / 50][y / 50]) {
                gameBoard.put(star.getId(), star);
                gameBoardOcc[x / 50][y / 50] = true;
            } else {
                i -= 1;
            }
            em.persist(star);
        }
        // initialize MonsterEggs
        MonsterEggEntity egg;
        for (int i = 0; i < INIT_EGGS_KILL; i++) {
            x = widthRand.nextInt(15) * 50;
            y = heightRand.nextInt(11) * 50;
            egg = new MonsterEggEntity(x, y, "kill");
            egg.setId("egg-" + numEggs);
            numEggs += 1;
            if (!gameBoardOcc[x / 50][y / 50]) {
                gameBoard.put("egg-" + egg.getId(), egg);
                gameBoardOcc[x / 50][y / 50] = true;
            } else {
                i -= 1;
            }
            em.persist(egg);
        }
        for (int i = 0; i < INIT_EGGS_FREEZE; i++) {
            x = widthRand.nextInt(15) * 50;
            y = heightRand.nextInt(11) * 50;
            egg = new MonsterEggEntity(x, y, "freeze");
            egg.setId("egg-" + numEggs);
            numEggs += 1;
            if (!gameBoardOcc[x / 50][y / 50]) {
                gameBoard.put("egg-" + egg.getId(), egg);
                gameBoardOcc[x / 50][y / 50] = true;
            } else {
                i -= 1;
            }
            em.persist(egg);
        }
    }
}
