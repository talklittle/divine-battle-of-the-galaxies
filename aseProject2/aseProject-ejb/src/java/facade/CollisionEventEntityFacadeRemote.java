/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import entity.CollisionEventEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author _yy
 */
@Remote
public interface CollisionEventEntityFacadeRemote {

    void create(CollisionEventEntity collisionEventEntity);

    void edit(CollisionEventEntity collisionEventEntity);

    void remove(CollisionEventEntity collisionEventEntity);

    CollisionEventEntity find(Object id);

    List<CollisionEventEntity> findAll();

    List<CollisionEventEntity> findRange(int[] range);

    int count();

}
