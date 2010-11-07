/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aseprojectappclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Andrew
 */
public class Main implements ActionListener {

    JFrame window;
    GameMasterPanel gameMasterPanel;


    public Main() {
        window = new JFrame("DIVINE BATTLE OF THE GALAXIES - Game Master.");
        window.setSize(900, 700);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int flag = JOptionPane.showConfirmDialog(window, "You're the Game Master. Really exit?", "Exit Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (JOptionPane.YES_OPTION == flag) {

                    //
                    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    // TODO should kick everyone out, right?
                    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                    //

                    System.exit(0);
                } else {
                    return;
                }
            }
        });
        window.setVisible(true);
        gameMasterPanel = new GameMasterPanel();
    }

    private void go() {
        window.add(gameMasterPanel);
        window.setContentPane(gameMasterPanel);
        window.setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main main = new Main();
        main.go();
    }

}
