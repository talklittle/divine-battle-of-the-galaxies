/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.accountInfo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Facade for accountInfo
 * @author _yy
 */
@Stateless
public class accountInfoFacade extends AbstractFacade<accountInfo>
                               implements accountInfoFacadeRemote {
    @PersistenceContext(unitName = "aseProject-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public accountInfoFacade() {
        super(accountInfo.class);
    }

}
