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
import java.util.ArrayList;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import session.GameServerBeanRemote;

/**
 *
 * @author Administrator
 */
public class drawPanel extends JPanel implements KeyListener, MessageListener {


    public static final long MOVEMENT_INPUT_DELAY_MILLIS = 100;

    BufferedImage buffer;
    ArrayList<GameEntity> gameBoard = null;

    public boolean nFlag_gameOver = false;
    GridLayout layout = new GridLayout(16, 12);
//    JLabel[] labels;

    private long lastSuccessfulMoveTime = 0;

    private TopicSession subSession;
    private TopicConnection connection;

    private GameServerBeanRemote gameServer;
    private String username;


    public drawPanel() {
        this.setIgnoreRepaint(true);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setLayout(layout);
//        labels = new JLabel[192];
//        for(int i=0;i<192;i++)
//        {
//            labels[i] = new JLabel();
//            labels[i].setBackground(Color.red);
//            add(labels[i]);
//
//        }

    }

    public void Initialize(String username) {
        buffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

//        initTopic("GameBoardTopic", username, password);
        try {
            initTopic("GameBoardTopic");

            // Lookup the GameServerBeanRemote
            InitialContext context = new InitialContext();
            gameServer = (GameServerBeanRemote)
                    context.lookup("java:comp/env/GameServerBeanRemote");
        } catch (Exception e) {
            System.err.println("shoot, there is Topic init exception " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initTopic(String topicName) throws NamingException, JMSException {
        // Obtain a JNDI connection
        Properties env = new Properties( );
        // ... specify the JNDI properties specific to the vendor

        InitialContext jndi = new InitialContext(env);

        // Look up a JMS connection factory
        TopicConnectionFactory conFactory =
            (TopicConnectionFactory)jndi.lookup("TopicConnectionFactory");

        // Create a JMS connection
        TopicConnection connection =
//        conFactory.createTopicConnection(username,password);
        conFactory.createTopicConnection();

        // Create two JMS session objects
        TopicSession subSession =
            connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

        // Look up a JMS topic
        Topic gameTopic = (Topic)jndi.lookup(topicName);

        // Create a JMS subscriber
        TopicSubscriber subscriber =
            subSession.createSubscriber(gameTopic);

        // Set a JMS message listener
        subscriber.setMessageListener(this);

        // Start the JMS connection; allows messages to be delivered
        connection.start( );
    }

    @Override
    /* Receive message from topic subscriber */
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            ArrayList<GameEntity> gameBoard = (ArrayList<GameEntity>) objectMessage.getObject();
        } catch (JMSException jmse){ jmse.printStackTrace( ); }
    }

    public void drawBuffer() {
        Graphics2D b = buffer.createGraphics();
        b.setColor(Color.black);
        b.fillRect(0, 0, 800, 600);

        b.setColor(Color.yellow);
        for(int i = 0;i<16;i++)
            b.drawLine(i*50, 0, i*50, 600);
        for(int i = 0; i<12;i++)
            b.drawLine(0, i*50, 800, i*50);

        // TODO draw entities on buffer b
        for (GameEntity entity : gameBoard) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) entity;
                String color = playerEntity.getColor();
                BufferedImage img = null;
                
                try {
                    img = ImageIO.read(new File("assets/sprite-"+color+".jpg"));
                    b.drawImage(img, playerEntity.getX(), playerEntity.getY(), null);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (entity instanceof MonsterEntity) {
                MonsterEntity monsterEntity = (MonsterEntity) entity;
                String type = monsterEntity.getType();
                String color = monsterEntity.getColor();
                BufferedImage img = null;

                try {
                    img = ImageIO.read(new File("assets/"+type+"-"+color+".jpg"));
                    b.drawImage(img, monsterEntity.getX(), monsterEntity.getY(), null);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (entity instanceof MonsterEggEntity) {
                MonsterEggEntity monsterEggEntity = (MonsterEggEntity) entity;
                String type = monsterEggEntity.getType();
                BufferedImage img = null;

                try {
                    img = ImageIO.read(new File("assets/"+type+"-item.jpg"));
                    b.drawImage(img, monsterEggEntity.getX(), monsterEggEntity.getY(), null);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (entity instanceof StarEntity) {
                StarEntity starEntity = (StarEntity) entity;
                BufferedImage img = null;

                try {
                    img = ImageIO.read(new File("assets/star.jpg"));
                    b.drawImage(img, starEntity.getX(), starEntity.getY(), null);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
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

    public void keyTyped(KeyEvent e) {
        int key = e.getKeyCode();

        // send a message to server (do client-side validation too)!!!
        if (key == KeyEvent.VK_LEFT) {
            gameServer.moveLeft(username);
        }
        if (key == KeyEvent.VK_RIGHT) {
            gameServer.moveRight(username);
        }
        if (key == KeyEvent.VK_UP) {
            gameServer.moveUp(username);
        }
        if (key == KeyEvent.VK_DOWN) {
            gameServer.moveDown(username);
        }
        if (key == KeyEvent.VK_F12)
            nFlag_gameOver=true;


    }

    public void keyPressed(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void keyReleased(KeyEvent e) {
        // nothing
    }
}
