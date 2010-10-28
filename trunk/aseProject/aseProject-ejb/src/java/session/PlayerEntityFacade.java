/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.PlayerEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class PlayerEntityFacade extends AbstractFacade<PlayerEntity> implements PlayerEntityFacadeRemote {
    @PersistenceContext(unitName = "aseProject-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PlayerEntityFacade() {
        super(PlayerEntity.class);
    }

}
