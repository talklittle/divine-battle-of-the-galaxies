/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aseproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author Administrator
 */
public class drawPanel extends JPanel implements KeyListener {

    BufferedImage buffer;
    entity player;
    entity enemy;
    public boolean nFlag_gameOver = false;
    GridLayout layout = new GridLayout(16, 12);
//    JLabel[] labels;

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
        player = new entity(100, 100);
        enemy = new entity(400, 400);

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
        if (key == KeyEvent.VK_LEFT) {
            player.left = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            player.right = true;
        }
        if (key == KeyEvent.VK_UP) {
            player.up = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            player.down = true;
        }
        if (key == KeyEvent.VK_F12)
            nFlag_gameOver=true;

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            player.left = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            player.right = false;
        }
        if (key == KeyEvent.VK_UP) {
            player.up = false;
        }
        if (key == KeyEvent.VK_DOWN) {
            player.down = false;
        }

    }
}
