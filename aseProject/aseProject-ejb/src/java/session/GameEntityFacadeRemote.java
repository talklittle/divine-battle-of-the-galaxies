/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.GameEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface GameEntityFacadeRemote {

    void create(GameEntity gameEntity);

    void edit(GameEntity gameEntity);

    void remove(GameEntity gameEntity);

    GameEntity find(Object id);

    List<GameEntity> findAll();

    List<GameEntity> findRange(int[] range);

    void initGameBoard();

    int count();

}
