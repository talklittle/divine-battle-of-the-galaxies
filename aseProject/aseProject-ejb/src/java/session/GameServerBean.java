/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.GameEntity;
import entity.MonsterEntity;
import entity.PlayerEntity;
import facade.PlayerEntityFacadeRemote;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerHandle;
import javax.ejb.TimerService;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Janessa
 */
@Stateful
public class GameServerBean implements GameServerBeanRemote, TimedObject {

    public static final long PUBLISH_INTERVAL_MILLIS = 50;

    private static PlayerEntityFacadeRemote playerFacade = lookupPlayerEntityFacadeRemote();

    private static TopicSession session;
    private static TopicPublisher publisher;

    private SessionContext sessionContext;
    private TimerHandle timerHandle = null;

    private ArrayList<GameEntity> gameBoard = new ArrayList<GameEntity>();
    
    public GameServerBean() {
        try {
            // Connect to a topic
            Context context = new InitialContext();
            TopicConnectionFactory factory = (TopicConnectionFactory)
                    context.lookup("GameBoardTopicFactory");
            Topic topic = (Topic) context.lookup("GameBoardTopic");
            TopicConnection connect = factory.createTopicConnection();
            session = connect.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
            publisher = session.createPublisher(topic);

            // Set up the EJB Timer
            if (timerHandle == null) {
//                SessionContext sc = (SessionContext)
//                        context.lookup("java:comp/EJBContext");
                TimerService ts = sessionContext.getTimerService();
                TimerConfig tc = new TimerConfig();
                Timer timer = ts.createIntervalTimer(5000, PUBLISH_INTERVAL_MILLIS, null);
                timerHandle = timer.getHandle();
            }

        } catch (NamingException ex) {
            Logger.getLogger(GameServerBean.class.getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        } catch (JMSException ex) {
            Logger.getLogger(GameServerBean.class.getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(GameServerBean.class.getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(GameServerBean.class.getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        } catch (EJBException ex) {
            Logger.getLogger(GameServerBean.class.getName()).log(Level.SEVERE, "exception caught", ex);
            throw new RuntimeException(ex);
        }
    }

    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        return fromX % 50 == 0 && fromY % 50 == 0
            && toX >= 0 && toY >= 0
            && toX <= 750 && toY <= 550
            && (toX - fromX == 50 || toX - fromX == -50)
            && (toY - fromY == 50 || toY - fromY == -50);
    }

    public boolean moveUp(String username) {
        PlayerEntity player = playerFacade.find(username);
        int playerX = player.getX();
        int playerY = player.getY();

        int toX = playerX;
        int toY = playerY - 50;

        if (!isValidMove(playerX, playerY, toX, toY))
            return false;



        // TODO update the Game Board



        return true;
    }

    public boolean moveLeft(String username) {
        PlayerEntity player = playerFacade.find(username);
        int playerX = player.getX();
        int playerY = player.getY();

        int toX = playerX - 50;
        int toY = playerY;

        if (!isValidMove(playerX, playerY, toX, toY))
            return false;



        // TODO update the Game Board



        return true;
    }

    public boolean moveDown(String username) {
        PlayerEntity player = playerFacade.find(username);
        int playerX = player.getX();
        int playerY = player.getY();

        int toX = playerX;
        int toY = playerY + 50;

        if (!isValidMove(playerX, playerY, toX, toY))
            return false;



        // TODO update the Game Board



        return true;
    }

    public boolean moveRight(String username) {
        PlayerEntity player = playerFacade.find(username);
        int playerX = player.getX();
        int playerY = player.getY();

        int toX = playerX + 50;
        int toY = playerY;

        if (!isValidMove(playerX, playerY, toX, toY))
            return false;



        // TODO update the Game Board



        return true;
    }

    @Override
    public void ejbTimeout(Timer timer) {
        // publish the current map to clients
        try {
            publisher.publish(session.createObjectMessage(gameBoard));
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }

    @Resource
    private void setSessionContext(SessionContext ctx) {
        sessionContext = ctx;
    }

    public void update() {
        for (GameEntity entity : gameBoard) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) entity;
                playerEntity.move();
            } else if (entity instanceof MonsterEntity) {
                MonsterEntity monsterEntity = (MonsterEntity) entity;
                monsterEntity.move();
            }

        }
    }


    private static PlayerEntityFacadeRemote lookupPlayerEntityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlayerEntityFacadeRemote) c.lookup("java:comp/env/PlayerEntityFacade");
        } catch (NamingException ne) {
            Logger.getLogger(GameServerBean.class.getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
