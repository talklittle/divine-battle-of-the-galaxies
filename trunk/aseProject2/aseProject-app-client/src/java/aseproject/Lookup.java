/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aseproject;

import facade.PlayerEntityFacadeRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import session.GameEntityFacadeRemote;
import session.accountInfoFacadeRemote;

/**
 *
 * @author Andrew
 */
public class Lookup {
    public static GameEntityFacadeRemote lookupGameEntityFacadeRemote() {
        try {
            return _lookupGameEntityFacadeRemote();
        } catch (NamingException ne) {
            Logger.getLogger(Lookup.class.getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public static GameEntityFacadeRemote _lookupGameEntityFacadeRemote() throws NamingException {
        Context c = new InitialContext();
        return (GameEntityFacadeRemote) c.lookup("java:global/aseProject2/aseProject-ejb/GameEntityFacade");
    }

    public static PlayerEntityFacadeRemote lookupPlayerEntityFacadeRemote() {
        try {
            return _lookupPlayerEntityFacadeRemote();
        } catch (NamingException ne) {
            Logger.getLogger(Lookup.class.getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public static PlayerEntityFacadeRemote _lookupPlayerEntityFacadeRemote() throws NamingException {
        Context c = new InitialContext();
        return (PlayerEntityFacadeRemote) c.lookup("java:global/aseProject2/aseProject-ejb/PlayerEntityFacade");
    }

    public static accountInfoFacadeRemote lookupaccountInfoFacadeRemote() {
        try {
            return _lookupaccountInfoFacadeRemote();
        } catch (NamingException ne) {
            Logger.getLogger(Lookup.class.getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public static accountInfoFacadeRemote _lookupaccountInfoFacadeRemote() throws NamingException {
        Context c = new InitialContext();
        return (accountInfoFacadeRemote) c.lookup("java:global/aseProject2/aseProject-ejb/accountInfoFacade");
    }


}
