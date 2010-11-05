/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import javax.ejb.Remote;

/**
 *
 * @author Janessa
 */
@Remote
public interface GameServerBeanRemote {

    boolean moveUp(String username);
    boolean moveLeft(String username);
    boolean moveDown(String username);
    boolean moveRight(String username);

    
}
