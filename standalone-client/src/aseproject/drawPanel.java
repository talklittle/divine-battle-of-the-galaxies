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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
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
 * @author _yy
 */
public class drawPanel extends JPanel implements KeyListener {

    public static final long MOVEMENT_INPUT_DELAY_MILLIS = 100;

    BufferedImage buffer;
    public boolean nFlag_gameOver = false;
    GridLayout layout = new GridLayout(16, 12);
    private long lastSuccessfulMoveTime = 0;
    private String username;
    private GameEntityFacadeRemote GameSession;
//    PlayerEntity player;

    private Timer myTimer;

    public drawPanel() {
        GameSession = lookupGameEntityFacadeRemote();
        GameSession.gameBoard();
        //GameSession.initGameBoard();
        this.setIgnoreRepaint(true);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setLayout(layout);

    }

    public void activate() {
        //initTimer();
    }

    public void Initialize(String username) {
        this.username = username;
//        player = (PlayerEntity) GameSession.find(username);
        buffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
    }

    public void drawBuffer() throws InterruptedException {
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
                    PlayerEntity player = (PlayerEntity) entity;
                    img = ImageIO.read(new File("assets/sprite-blue.png"));

                    b.drawImage(img, entity.getX(), entity.getY(), null);
                }
                if (entity instanceof MonsterEntity) {
                    img = ImageIO.read(new File("assets/freeze-red.png"));
                    b.drawImage(img, entity.getX(), entity.getY(), null);
                }
                if (entity instanceof MonsterEggEntity) {
                    MonsterEggEntity egg = (MonsterEggEntity) entity;
                    if (egg.getType().equals("kill")) {
                        img = ImageIO.read(new File("assets/freeze-kiwi.png"));
                    } else {
                        img = ImageIO.read(new File("assets/kill-yellow.png"));
                    }
                    b.drawImage(img, entity.getX(), entity.getY(), null);
                }
                if (entity instanceof StarEntity) {
                    img = ImageIO.read(new File("assets/star.png"));
                    b.drawImage(img, entity.getX(), entity.getY(), null);
                }

            } catch (IOException ex) {
                Logger.getLogger(drawPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        b.setColor(Color.red);
        String[][] occ = GameSession.getOcc();
        for(int i=0; i<16; i++) {
            for(int j=0; j<12; j++) {
                if(occ[i][j] != null) b.drawString(occ[i][j], i*50, j*50+25);
            }
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

    class monsterTask extends TimerTask {
    	public void run() {
            List entities = GameSession.findAll();
            Iterator iter = entities.iterator();
            Random rand = new Random();
            int direction;
            while(iter.hasNext()) {
                GameEntity entity = (GameEntity) iter.next();
                if(entity instanceof MonsterEggEntity) {
                    direction = rand.nextInt(1920)%4;
                    switch(direction) {
                        case 0://up
                            GameSession.moveUp(entity.getId());
                            break;
                        case 1://down
                            GameSession.moveDown(entity.getId());
                            break;
                        case 2://left
                            GameSession.moveLeft(entity.getId());
                            break;
                        case 3://right
                            GameSession.moveRight(entity.getId());
                            break;
                    }
                }
            }
    	}
    }

    public void initTimer() {
        myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new monsterTask(), 1000, 1000);
    }

    private GameEntityFacadeRemote lookupGameEntityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (GameEntityFacadeRemote) c.lookup("java:global/aseProject/aseProject-ejb/GameEntityFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
