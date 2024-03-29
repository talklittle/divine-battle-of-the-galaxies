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
 * the panel where the game is shown, with grid and GameEntities
 * @author _yy
 */
public class drawPanel extends JPanel implements KeyListener {

    public static final long MOVEMENT_INPUT_DELAY_MILLIS = 100;
    public static final int SCREEN_PIXEL_WIDTH = 800;
    public static final int SCREEN_PIXEL_HEIGHT = 600;
    public static final int GRID_WIDTH = 16;
    public static final int GRID_HEIGHT = 12;
    public static final int GRID_SQUARE_SIZE = 50;

    public static final String SOUND_KILL
            = "44430__thecheeseman__hurt3.wav";
    public static final String SOUND_FREEZE
            = "freeze.wav";
    public static final String SOUND_PLAYER_PLAYER
            = "17934__zippi1__sound_hello1.wav";
    public static final String SOUND_STAR
            = "ting.wav";

    BufferedImage buffer;
    public boolean nFlag_gameOver = false;
    public String winner = "";
    public String winnerColor = "";
    GridLayout layout = new GridLayout(16, 12);
    private long lastSuccessfulMoveTime = 0;
    private String username;
    private GameEntityFacadeRemote GameSession;
//    PlayerEntity player;
    private HashSet<CollisionEventEntity> seenCollisionEvents
            = new HashSet<CollisionEventEntity>();
    private static final Object seenCollisionEventsLock
            = new Object();
    private ScreenUtil screenUtil
            = new ScreenUtil(GRID_WIDTH, GRID_HEIGHT,
                             SCREEN_PIXEL_WIDTH, SCREEN_PIXEL_HEIGHT);

    /**
     * default constructor. set up board and listeners
     */
    public drawPanel() {
        GameSession = Lookup.lookupGameEntityFacadeRemote();
        GameSession.gameBoard();
        this.setIgnoreRepaint(true);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setLayout(layout);

    }

    /**
     * set up username and the draw buffer
     * @param username
     */
    public void Initialize(String username) {
        //System.out.println("drawpanel: " + username);
        this.username = username;
        buffer = new BufferedImage(800, 600,
                                   BufferedImage.TYPE_INT_RGB);
    }

    /**
     * draw onto draw buffer
     * @throws InterruptedException
     */
    public void drawBuffer() throws InterruptedException {
        Graphics2D b = buffer.createGraphics();
        b.setColor(Color.black);
        b.fillRect(0, 0, 800, 600);

        // draw the grid
        b.setColor(Color.yellow);
        for (int i = 0; i < GRID_WIDTH; i++) {
            b.drawLine(i * GRID_SQUARE_SIZE, 0,
                       i * GRID_SQUARE_SIZE, 600);
        }
        for (int i = 0; i < GRID_HEIGHT; i++) {
            b.drawLine(0, i * GRID_SQUARE_SIZE,
                       800, i * GRID_SQUARE_SIZE);
        }

        // draw entities on buffer b
        List gameEntities = GameSession.findAll();
        PlayerEntity myPlayer =
                     (PlayerEntity) GameSession.find(username);

        Iterator iter = gameEntities.iterator();
        while (iter.hasNext()) {
            GameEntity entity = (GameEntity) iter.next();
            BufferedImage img = null;
            try {
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    drawPlayer(b, player, myPlayer);
                } else if (entity instanceof MonsterEntity) {
                    MonsterEntity monster = (MonsterEntity) entity;
                    drawMonster(b, monster);
                } else if (entity instanceof StarEntity) {
                    StarEntity star = (StarEntity) entity;
                    drawStar(b, star);
                } else if (entity instanceof CollisionEventEntity) {
                    CollisionEventEntity collision
                            = (CollisionEventEntity) entity;

                    if (!isEventAlreadySeen(collision)) {
                        handleCollisionEvent(collision);
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(drawPanel.class.getName())
                      .log(Level.SEVERE, null, ex);
            }

        }
        b.dispose();
    }

    /**
     * draw the player
     * @param b
     * @param player
     * @param myPlayer
     * @throws IOException
     */
    private void drawPlayer(Graphics2D b, PlayerEntity player,
                            PlayerEntity myPlayer)
                 throws IOException {
        BufferedImage img = ImageIO.read(new File("assets/sprite-"
                                         + player.getColor()
                                         + ".png"));
        if ((player.getId().equals(username) ||
             player.getGameOverTime() > myPlayer.getNewGameTime())
            && player.getStars() >= 10) {

            nFlag_gameOver = true;
            if (player.getId().equals(username)) {
                // when I get game over,
                // save my game over time
                myPlayer.setGameOverTime(
                         System.currentTimeMillis());
            } else {
                // if someone else won,
                // copy their game over time
                myPlayer.setGameOverTime(
                         player.getGameOverTime());
            }
            GameSession.edit(myPlayer);

            winner = player.getId();
            winnerColor = player.getColor();
        }
        int[] screenXY = screenUtil.gridToScreen(player.getX(),
                                                 player.getY());
        b.drawImage(img, screenXY[0], screenXY[1], null);
    }

    /**
     * draw the monster
     * @param b
     * @param monster
     * @throws IOException
     */
    private void drawMonster(Graphics2D b, MonsterEntity monster)
                 throws IOException {
        BufferedImage img;
        if (monster.getType().equals("kill")) {
            img = ImageIO.read(new File("assets/kill-"
                                        + monster.getColor()
                                        + ".png"));
        } else {
            img = ImageIO.read(new File("assets/freeze-"
                                        + monster.getColor()
                                        + ".png"));
        }
        int[] screenXY = screenUtil.gridToScreen(monster.getX(),
                                                 monster.getY());
        b.drawImage(img, screenXY[0], screenXY[1], null);
    }

    /**
     * draw the star
     * @param b
     * @param star
     * @throws IOException
     */
    private void drawStar(Graphics2D b, StarEntity star)
                 throws IOException {
        BufferedImage img = ImageIO.read(new File("assets/star.png"));
        int[] screenXY = screenUtil.gridToScreen(star.getX(),
                                                 star.getY());
        b.drawImage(img, screenXY[0], screenXY[1], null);
    }

    /**
     * draw buffer to screen
     */
    public void drawScreen() {
        Graphics2D g = (Graphics2D) this.getGraphics();
        g.drawImage(buffer, 0, 0, this);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }


    /**
     * tell if already processed event (so you don't play same
     * sound twice)
     * @param event
     * @return
     */
    private boolean isEventAlreadySeen(CollisionEventEntity event) {
        boolean seen;
        seen = seenCollisionEvents.contains(event);
        return seen;
    }

    /**
     * handle collisions (play sounds)
     * @param event
     */
    private void handleCollisionEvent(CollisionEventEntity event) {
        int type = event.getCollisionType();
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
                    SoundEffects.playSound(SOUND_KILL);
                    break;
                case CollisionEventEntity.COLLISION_PLAYER_FREEZE:
                    // XXX For now, Freeze is same as Kill
                    SoundEffects.playSound(SOUND_FREEZE);
                    break;
                case CollisionEventEntity.COLLISION_PLAYER_PLAYER:
                    SoundEffects.playSound(SOUND_PLAYER_PLAYER);
                    break;
                case CollisionEventEntity.COLLISION_PLAYER_STAR:
                    SoundEffects.playSound(SOUND_STAR);
                    break;
            }
        }
    }

    /**
     * start game and enter game loop
     * @param username
     */
    public void startGame(String username) {
        Initialize(username);
        boardPanel parent = (boardPanel) this.getParent();
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

    /**
     * handle key press for movement
     * @param e
     */
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
