
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.GameEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 * Remote interface for GameEntityFacade
 * @author _yy
 */
@Remote
public interface GameEntityFacadeRemote {

    String[][] getOcc();

    void create(GameEntity gameEntity);

    void edit(GameEntity gameEntity);

    void remove(GameEntity gameEntity);

    GameEntity find(Object id);

    List<GameEntity> findAll();

    List<GameEntity> findRange(int[] range);

    void initGameBoard();

    void clearGameBoard();

    void cleanOldCollisionEvents();

    boolean moveUp(String username);

    boolean moveLeft(String username);

    boolean moveDown(String username);

    boolean moveRight(String username);

    void gameBoard();

    boolean gameOver();

    int count();
}
