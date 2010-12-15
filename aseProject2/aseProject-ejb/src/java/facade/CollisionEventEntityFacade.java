/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import entity.CollisionEventEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Facade for CollisionEventEntity
 * @author _yy
 */
@Stateless
public class CollisionEventEntityFacade
       extends AbstractFacade<CollisionEventEntity>
       implements CollisionEventEntityFacadeRemote {

    @PersistenceContext(unitName = "aseProject-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CollisionEventEntityFacade() {
        super(CollisionEventEntity.class);
    }

}
