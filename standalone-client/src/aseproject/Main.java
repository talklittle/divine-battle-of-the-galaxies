/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aseproject;

import entity.GameEntity;
import entity.PlayerEntity;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import session.GameEntityFacadeRemote;

/**
 *
 * @author Administrator
 */
public class Main implements ActionListener {

    JFrame window;
    drawPanel gamePanel;
    registerPanel regPanel;
    GameEntityFacadeRemote gameSession;

    public Main() {

        window = new JFrame("DIVINE BATTLE OF THE GALAXIES.");
        gamePanel = new drawPanel();
        regPanel = new registerPanel();
        window.setSize(900, 700);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public void go() {

        while (true) {
            if (regPanel.nFlag_registered == false) {
                window.setContentPane(regPanel);
                while (regPanel.nFlag_registered == false) {
                }
            }

            if (regPanel.nFlag_registered == true && gamePanel.nFlag_gameOver == false) {

                window.setContentPane(gamePanel);
                gamePanel.activate();
                window.validate();
                window.repaint();
                gamePanel.requestFocusInWindow();
                gamePanel.startGame(regPanel.username);
            }

            if (gamePanel.nFlag_gameOver == true) {
                System.out.print("Game over");
                JButton endBtn = new JButton("Game over.");
                endBtn.addActionListener(this);
                JPanel endPanel = new JPanel();
                endPanel.add(endBtn);
                window.setContentPane(endPanel);
                window.validate();
                window.repaint();
                System.out.print("Game over");
                while (gamePanel.nFlag_gameOver == true) {
                }
            }
        }
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.go();
    }

    public void actionPerformed(ActionEvent e) {
        //delete all player entity in the board
        gameSession = lookupGameEntityFacadeRemote();
        List playerEntities = gameSession.findAll();
        Iterator it = playerEntities.iterator();
        while (it.hasNext()) {
            GameEntity player = (GameEntity) it.next();
            if (player instanceof PlayerEntity) {
                gameSession.remove(player);
            }
        }
        System.out.print("restart the game");
        gamePanel.nFlag_gameOver = false;
        regPanel.nFlag_registered = false;
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