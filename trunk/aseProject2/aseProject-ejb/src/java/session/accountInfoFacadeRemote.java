/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.accountInfo;
import java.util.List;
import javax.ejb.Remote;

/**
 * Remote interface for accountInfoFacade
 * @author _yy
 */
@Remote
public interface accountInfoFacadeRemote {

    void create(accountInfo accountInfo);

    void edit(accountInfo accountInfo);

    void remove(accountInfo accountInfo);

    accountInfo find(Object id);

    List<accountInfo> findAll();

    List<accountInfo> findRange(int[] range);

    int count();

}
