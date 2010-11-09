/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aseproject;

import entity.GameEntity;
import entity.PlayerEntity;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import session.GameEntityFacadeRemote;

/**
 *
 * @author _yy
 */
public class Main implements ActionListener {

    JFrame window;
    drawPanel gamePanel;
    registerPanel regPanel;
    adminPanel adminConsole;
    GameEntityFacadeRemote gameSession;

    boolean nFlag_admin;

    public Main() {

        nFlag_admin = false;

        window = new JFrame("DIVINE BATTLE OF THE GALAXIES.");
        gamePanel = new drawPanel();
        regPanel = new registerPanel(this);
        adminConsole = new adminPanel(this);
        window.setSize(900, 700);
        gameSession = Lookup.lookupGameEntityFacadeRemote();
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int flag = JOptionPane.showConfirmDialog(window, "Really exit?", "Exit Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (JOptionPane.YES_OPTION == flag) {
                    //delete its own player
                    PlayerEntity currentPlayer = regPanel.User;
                    if (currentPlayer != null)
                        gameSession.remove(currentPlayer);
                    System.exit(0);
                } else {
                    return;
                }
            }
        });
        window.setVisible(true);
    }

    public void adminConsole() {
        System.out.println("add admin console");
        nFlag_admin = true;
        window.add(adminConsole);
        window.validate();
        window.repaint();
        adminConsole.requestFocusInWindow();
    }

    public void go() {
        window.add(regPanel);
        window.setVisible(true);

        while (true) {
//            if(nFlag_adminLogout) {
//                nFlag_adminLogout = false;
//                System.out.println("break!");
//                return;
//            }
            if(regPanel.nFlag_registered && !gamePanel.nFlag_gameOver && !nFlag_admin) {
                window.remove(regPanel);
                window.add(gamePanel);
                window.validate();
                window.repaint();
                gamePanel.requestFocusInWindow();
                gamePanel.startGame(regPanel.username);
            }

            if (gamePanel.nFlag_gameOver == true) {
                System.out.println("Game over");

                JButton endBtn = new JButton("Game over, Restart?");
                endBtn.addActionListener(this);
                JLabel winnerLabel = new JLabel("Winner: " + gamePanel.winner + " (" + gamePanel.winnerColor + ")");

                JPanel endPanel = new JPanel();
                endPanel.add(endBtn);
                endPanel.add(winnerLabel);

                window.remove(gamePanel);
                window.add(endPanel);
                window.validate();
                window.repaint();

                // Busy wait while Game Over screen is showing
                while (gamePanel.nFlag_gameOver == true) {
                }

                // Bring back to Register/Login page
                window.remove(endPanel);
                window.add(regPanel);
                window.validate();
                window.repaint();
            }
        }
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.go();


    }

    public void actionPerformed(ActionEvent e) {
        //delete all player entity in the board

        List playerEntities = gameSession.findAll();
        Iterator it = playerEntities.iterator();


        while (it.hasNext()) {
            GameEntity player = (GameEntity) it.next();


            if (player instanceof PlayerEntity) {
                gameSession.remove(player);


            }
        }
        System.out.println("restart the game");
        regPanel.nFlag_registered = false;
        gamePanel.nFlag_gameOver = false;


    }

}