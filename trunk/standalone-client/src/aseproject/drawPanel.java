/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aseproject;

import entity.CollisionEventEntity;
import entity.GameEntity;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    public String winner = "";
    public String winnerColor = "";
    GridLayout layout = new GridLayout(16, 12);
    private long lastSuccessfulMoveTime = 0;
    private String username;
    private GameEntityFacadeRemote GameSession;
//    PlayerEntity player;

    private HashSet<CollisionEventEntity> seenCollisionEvents = new HashSet<CollisionEventEntity>();
    private static final Object seenCollisionEventsLock = new Object();

    public drawPanel() {
        GameSession = Lookup.lookupGameEntityFacadeRemote();
        GameSession.gameBoard();
        //GameSession.initGameBoard();
        this.setIgnoreRepaint(true);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setLayout(layout);

    }

    public void Initialize(String username) {
        System.out.println("drawpanel: "+username);
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

        // draw entities on buffer b
        List gameEntities = GameSession.findAll();

        Iterator iter = gameEntities.iterator();
        while (iter.hasNext()) {
            GameEntity entity = (GameEntity) iter.next();
            BufferedImage img = null;
            try {
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    img = ImageIO.read(new File("assets/sprite-" + player.getColor() + ".png"));
                    if (player.getStars() == 10) {
                        nFlag_gameOver = true;
//                        GameSession.gameOver(nFlag_gameOver);
                        winner = player.getId();
                        winnerColor = player.getColor();
                    }
                    //b.drawString("STAR" + player.getStars(), player.getX() + 50, player.getY() + 50);
                    b.drawImage(img, entity.getX(), entity.getY(), null);
                } else if (entity instanceof MonsterEntity) {
                    MonsterEntity egg = (MonsterEntity) entity;
                    if (egg.getType().equals("kill")) {
                        img = ImageIO.read(new File("assets/kill-"+egg.getColor()+".png"));
                    } else {
                        img = ImageIO.read(new File("assets/freeze-"+egg.getColor()+".png"));
                    }
                    b.drawImage(img, entity.getX(), entity.getY(), null);
                } else if (entity instanceof StarEntity) {
                    img = ImageIO.read(new File("assets/star.png"));
                    b.drawImage(img, entity.getX(), entity.getY(), null);
                } else if (entity instanceof CollisionEventEntity) {
                    CollisionEventEntity collision = (CollisionEventEntity) entity;

                    synchronized (seenCollisionEventsLock) {
                        if (!isEventAlreadySeen(collision)) {
                            handleCollisionEvent(collision);
                        }
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(drawPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        /*b.setColor(Color.red);
        String[][] occ = GameSession.getOcc();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 12; j++) {
                if (occ[i][j] != null) {
                    b.drawString(occ[i][j], i * 50, j * 50 + 25);
                }
            }
        }*/
        b.dispose();
    }

    public void drawScreen() {
        Graphics2D g = (Graphics2D) this.getGraphics();
        g.drawImage(buffer, 0, 0, this);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }

    private boolean isEventAlreadySeen(CollisionEventEntity event) {
        boolean seen;
        seen = seenCollisionEvents.contains(event);
        return seen;
    }

    private void handleCollisionEvent(CollisionEventEntity event) {
        int type = event.getCollisionType();
        int x = event.getX();
        int y = event.getY();
        String id1 = event.getGameEntityId1();
        String id2 = event.getGameEntityId2();

        seenCollisionEvents.add(event);
        
        // play sounds for events involving this player
        if (username.equals(id1) || username.equals(id2)) {
            switch (type) {
//                case CollisionEventEntity.COLLISION_PLAYER_EGG:
//                    SoundEffects.playSound("playerGetEgg.wav");
//                    break;
                case CollisionEventEntity.COLLISION_PLAYER_KILL:
                    SoundEffects.playSound("44430__thecheeseman__hurt3.wav");
                    break;
                case CollisionEventEntity.COLLISION_PLAYER_FREEZE:
                    // XXX For now, Freeze is same as Kill
                    SoundEffects.playSound("44430__thecheeseman__hurt3.wav");
                    break;
                case CollisionEventEntity.COLLISION_PLAYER_PLAYER:
                    SoundEffects.playSound("17934__zippi1__sound_hello1.wav");
                    break;
                case CollisionEventEntity.COLLISION_PLAYER_STAR:
                    SoundEffects.playSound("ting.wav");
                    break;
            }
        }
    }

    public void startGame(String username) {
        Initialize(username);
        boardPanel parent = (boardPanel)this.getParent();
        while (true) {
            try {
                parent.iPanel.updateInfo();
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
}
