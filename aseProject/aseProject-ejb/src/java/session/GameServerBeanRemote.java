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

    boolean moveAction(String username, int fromX, int fromY, int toX, int toY);
    
}
