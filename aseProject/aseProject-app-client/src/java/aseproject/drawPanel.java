/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aseproject;

import entity.GameEntity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Properties;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;

/**
 *
 * @author Administrator
 */
public class drawPanel extends JPanel implements KeyListener, MessageListener {

    BufferedImage buffer;
    GameEntity player;
    GameEntity enemy;
    public boolean nFlag_gameOver = false;
    GridLayout layout = new GridLayout(16, 12);
//    JLabel[] labels;

    private TopicSession subSession;
    private TopicConnection connection;


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

    public void Initialize() {
        buffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        player = new GameEntity(100, 100);
        enemy = new GameEntity(400, 400);

//        initTopic("GameBoardTopic", username, password);
        try {
            initTopic("GameBoardTopic");
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
        connection.createTopicSession(false,
                                      Session.AUTO_ACKNOWLEDGE);

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
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText( );
            System.out.println(text);
        } catch (JMSException jmse){ jmse.printStackTrace( ); }
    }

    public void update() {
        player.move();
    }

    public void checkCollision() {
        if (player.getBounds().intersects(enemy.getBounds())) {
            player.collision = true;
        } else {
            player.collision = false;
        }
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
        if (player.collision == false) {
            b.setColor(Color.red);
            b.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
            b.setColor(Color.CYAN);
            b.fillRect(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        } else {
            b.setColor(Color.white);
            b.drawString("C O L L I S I O N", 350, 300);
            player.collision = false;
        }
        b.dispose();

    }

    public void drawScreen() {
        Graphics2D g = (Graphics2D) this.getGraphics();
        g.drawImage(buffer, 0, 0, this);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }

    public void startGame() {
        Initialize();
        while (true) {
            try {
                update();
                checkCollision();
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && !player.isMoving) {
            player.left = true;
            player.isMoving = true;
        }
        if (key == KeyEvent.VK_RIGHT && !player.isMoving) {
            player.right = true;
            player.isMoving = true;
        }
        if (key == KeyEvent.VK_UP && !player.isMoving) {
            player.up = true;
            player.isMoving = true;
        }
        if (key == KeyEvent.VK_DOWN && !player.isMoving) {
            player.down = true;
            player.isMoving = true;
        }
        if (key == KeyEvent.VK_F12)
            nFlag_gameOver=true;

    }

    public void keyReleased(KeyEvent e) {
        // not needed; see entity.move()
    }
}
