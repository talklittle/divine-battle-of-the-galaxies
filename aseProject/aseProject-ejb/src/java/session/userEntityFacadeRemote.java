/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.userEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface userEntityFacadeRemote {

    void create(userEntity userEntity);

    void edit(userEntity userEntity);

    void remove(userEntity userEntity);

    userEntity find(Object id);

    List<userEntity> findAll();

    List<userEntity> findRange(int[] range);

    int count();

}
