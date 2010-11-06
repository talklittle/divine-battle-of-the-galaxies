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
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
        buffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
//        try {
//            // Lookup the GameServerBeanRemote
//            InitialContext context = new InitialContext();
//            gameServer = (GameServerBeanRemote)
//                    context.lookup("java:global/aseProject/aseProject-ejb/GameServerBean");
//        } catch (Exception e) {
//            System.err.println("shoot, there is Topic init exception " + e.getMessage());
//            e.printStackTrace();
//        }
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
                img = ImageIO.read(new File("aseproject-app-client/assets/freeze-blue.png"));
                b.drawImage(img, entity.getX(), entity.getY(), null);
            } catch (IOException ex) {
                Logger.getLogger(drawPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println(entity.getId());

//            if (entity instanceof PlayerEntity) {
//                PlayerEntity playerEntity = (PlayerEntity) entity;
//                String color = playerEntity.getColor();
//                BufferedImage img = null;
//
//                try {
//                    img = ImageIO.read(new File("assets/sprite-green.png"));
//                    b.drawImage(img, playerEntity.getX(), playerEntity.getY(), null);
//                    
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else if (entity instanceof MonsterEntity) {
//                MonsterEntity monsterEntity = (MonsterEntity) entity;
//                String type = monsterEntity.getType();
//                String color = monsterEntity.getColor();
//                BufferedImage img = null;
//
//                try {
//                    img = ImageIO.read(new File("assets/" + type + "-" + color + ".png"));
//                    b.drawImage(img, monsterEntity.getX(), monsterEntity.getY(), null);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else if (entity instanceof MonsterEggEntity) {
//                MonsterEggEntity monsterEggEntity = (MonsterEggEntity) entity;
//                String type = monsterEggEntity.getType();
//                BufferedImage img = null;
//
//                try {
//                    img = ImageIO.read(new File("assets/" + type + "-item.png"));
//                    b.drawImage(img, monsterEggEntity.getX(), monsterEggEntity.getY(), null);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else if (entity instanceof StarEntity) {
//                StarEntity starEntity = (StarEntity) entity;
//                BufferedImage img = null;
//
//                try {
//                    img = ImageIO.read(new File("assets/star.png"));
//                    b.drawImage(img, starEntity.getX(), starEntity.getY(), null);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
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

    public void keyTyped(KeyEvent e) {
//        int key = e.getKeyCode();
//
//        // send a message to server (do client-side validation too)!!!
//        if (key == KeyEvent.VK_LEFT) {
//            gameServer.moveLeft(username);
//        }
//        if (key == KeyEvent.VK_RIGHT) {
//            gameServer.moveRight(username);
//        }
//        if (key == KeyEvent.VK_UP) {
//            gameServer.moveUp(username);
//        }
//        if (key == KeyEvent.VK_DOWN) {
//            gameServer.moveDown(username);
//        }
//        if (key == KeyEvent.VK_F12) {
//            nFlag_gameOver = true;
//        }
        System.out.println("NOT SUPPORTED YET");
    }

    public void keyPressed(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void keyReleased(KeyEvent e) {
        // nothing
    }

    private GameEntityFacadeRemote lookupGameEntityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (GameEntityFacadeRemote) c.lookup("java:comp/env/GameEntityFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
