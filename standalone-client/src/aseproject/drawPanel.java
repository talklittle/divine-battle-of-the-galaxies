/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aseproject;

import entity.GameEntity;
import entity.MonsterEggEntity;
import entity.MonsterEntity;
import entity.PlayerEntity;
import entity.StarEntity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.swing.*;
import session.GameEntityFacadeRemote;

/**
 *
 * @author Administrator
 */
public class drawPanel extends JPanel implements KeyListener {

    public static final long MOVEMENT_INPUT_DELAY_MILLIS = 100;
    BufferedImage buffer;
//    HashMap<String, GameEntity> gameBoard = new HashMap<String, GameEntity>();
    public boolean nFlag_gameOver = false;
    GridLayout layout = new GridLayout(16, 12);
    private long lastSuccessfulMoveTime = 0;
//    private GameServerBeanRemote gameServer;
    private String username;
    private GameEntityFacadeRemote GameSession;

    public drawPanel() {
        GameSession = lookupGameEntityFacadeRemote();
//        GameSession.initGameBoard();
        this.setIgnoreRepaint(true);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setLayout(layout);

    }

    public void Initialize(String username) {
        this.username = username;
        buffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
    }

    public void drawBuffer() {
        Graphics2D b = buffer.createGraphics();
        b.setColor(Color.black);
        b.fillRect(0, 0, 800, 600);

        b.setColor(Color.yellow);
        for (int i = 0; i < 16; i++) {
            b.drawLine(i * 50, 0, i * 50, 600);
        }
        for (int i = 0; i < 12; i++) {
            b.drawLine(0, i * 50, 800, i * 50);
        }

        // TODO draw entities on buffer b
        List gameEntities = GameSession.findAll();

        Iterator iter = gameEntities.iterator();
        while (iter.hasNext()) {
            GameEntity entity = (GameEntity) iter.next();
            BufferedImage img = null;
            try {
                if (entity instanceof PlayerEntity) {
                    img = ImageIO.read(new File("assets/sprite-blue.png"));
                    b.drawImage(img, entity.getX(), entity.getY(), null);
                }
                if (entity instanceof MonsterEntity) {
                    img = ImageIO.read(new File("assets/freeze-red.png"));
                    b.drawImage(img, entity.getX(), entity.getY(), null);
                }
                if (entity instanceof MonsterEggEntity) {
                    img = ImageIO.read(new File("assets/kill-yellow.png"));
                    b.drawImage(img, entity.getX(), entity.getY(), null);
                }
                if (entity instanceof StarEntity) {
                    img = ImageIO.read(new File("assets/star.png"));
                    b.drawImage(img, entity.getX(), entity.getY(), null);
                }

            } catch (IOException ex) {
                Logger.getLogger(drawPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(entity.getId());

        }
        b.dispose();
    }

    public void drawScreen() {
        Graphics2D g = (Graphics2D) this.getGraphics();
        g.drawImage(buffer, 0, 0, this);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }

    public void startGame(String username) {
        Initialize(username);
        while (true) {
            try {
                drawBuffer();
                drawScreen();
                Thread.sleep(15);
                if (nFlag_gameOver == true) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        // send a message to server (do client-side validation too)!!!
        if (key == KeyEvent.VK_LEFT) {
            GameSession.moveLeft(username);
        }
        if (key == KeyEvent.VK_RIGHT) {
            GameSession.moveRight(username);
        }
        if (key == KeyEvent.VK_UP) {
            GameSession.moveUp(username);
        }
        if (key == KeyEvent.VK_DOWN) {
            GameSession.moveDown(username);
        }
        if (key == KeyEvent.VK_F12) {
            nFlag_gameOver = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // nothing
    }

    private GameEntityFacadeRemote lookupGameEntityFacadeRemote() {
        try {
//            Hashtable props = new Hashtable();
//            props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
//            props.put(Context.PROVIDER_URL, "iiop://192.168.1.101:3700");

//            Properties props = new Properties();
//            props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
//            props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
//            props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
//            props.setProperty("org.omg.CORBA.ORBInitialHost", "192.168.1.101");
//            props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

//            Context c = new InitialContext(props);
            Context c = new InitialContext();

//            //Get all the names in the initial context
//            NamingEnumeration children = c.list("");
//
//            while(children.hasMore()) {
//                NameClassPair ncPair = (NameClassPair)children.next();
//                System.out.print(ncPair.getName() + " (type ");
//                System.out.println(ncPair.getClassName() + ")");
//            }

            return (GameEntityFacadeRemote) c.lookup("java:global/aseProject/aseProject-ejb/GameEntityFacade");
//            return (GameEntityFacadeRemote) c.lookup("java:comp/env/GameEntityFacade");
//            return (GameEntityFacadeRemote)
//                    c.lookup("corbaname:iiop:192.168.1.101:3700#aseProject/aseProject-ejb/session/GameEntityFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}