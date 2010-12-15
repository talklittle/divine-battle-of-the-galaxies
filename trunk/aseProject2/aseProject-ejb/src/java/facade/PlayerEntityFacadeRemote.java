/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import entity.PlayerEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 * Remote interface for PlayerEntityFacade
 * @author _yy
 */
@Remote
public interface PlayerEntityFacadeRemote {

    void create(PlayerEntity userEntity);

    void edit(PlayerEntity userEntity);

    void remove(PlayerEntity userEntity);

    PlayerEntity find(Object id);

    List<PlayerEntity> findAll();

    List<PlayerEntity> findRange(int[] range);

    int count();

}
