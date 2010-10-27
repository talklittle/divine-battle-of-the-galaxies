/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.userEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class userEntityFacade extends AbstractFacade<userEntity> implements userEntityFacadeRemote {
    @PersistenceContext(unitName = "aseProject-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public userEntityFacade() {
        super(userEntity.class);
    }

}
