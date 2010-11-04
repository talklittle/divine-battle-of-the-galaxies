/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aseproject;

import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Administrator
 */
public class Main implements ActionListener
{

    JFrame window;
    drawPanel gamePanel;
    registerPanel regPanel;

    public Main() {

        window = new JFrame("This is just a test for collision.");
        gamePanel = new drawPanel();
        regPanel = new registerPanel();
        window.setSize(900, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public void go() {
        window.setContentPane(regPanel);
        while (true) {

            if (regPanel.nFlag_registered && gamePanel.nFlag_gameOver == false) {

                window.setContentPane(gamePanel);
                window.validate();
                window.repaint();
                gamePanel.requestFocusInWindow();
                gamePanel.startGame();
            }

            if (gamePanel.nFlag_gameOver == true) {
                System.out.print("Game over");
                JButton endBtn = new JButton("Game over, start again?");
                endBtn.addActionListener(this);
                JPanel endPanel = new JPanel();
                endPanel.add(endBtn);
                window.setContentPane(endPanel);
                window.validate();
                window.repaint();
                System.out.print("Game over");
                while(gamePanel.nFlag_gameOver == true)
                {}
            }
        }
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.go();
    }

    public void actionPerformed(ActionEvent e) {
        gamePanel.nFlag_gameOver=false;
    }
}
