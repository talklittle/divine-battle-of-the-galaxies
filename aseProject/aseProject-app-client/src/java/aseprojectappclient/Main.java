/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aseprojectappclient;

import entity.GameEntity;
import entity.MonsterEntity;
import facade.Lookup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import session.GameEntityFacadeRemote;

/**
 *
 * @author Andrew
 */
public class Main implements ActionListener {

    JFrame window;
    GameMasterPanel gameMasterPanel;
    private GameEntityFacadeRemote gameSession;

    Timer myTimer;


    public Main() {
        window = new JFrame("DIVINE BATTLE OF THE GALAXIES - Game Master.");
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
        gameMasterPanel = new GameMasterPanel();
    }

    private void go() {
        window.add(gameMasterPanel);
        window.setContentPane(gameMasterPanel);
        window.pack();
        window.setVisible(true);

        gameSession = Lookup.lookupGameEntityFacadeRemote();

        initTimer();
    }

    class monsterTask extends TimerTask {

        public void run() {
            List entities = gameSession.findAll();
            Iterator iter = entities.iterator();
            Random rand = new Random();
            int direction;
            while (iter.hasNext()) {
                GameEntity entity = (GameEntity) iter.next();
                if (entity instanceof MonsterEntity) {
                    direction = rand.nextInt(1920) % 4;
                    switch (direction) {
                        case 0://up
                            gameSession.moveUp(entity.getId());
                            break;
                        case 1://down
                            gameSession.moveDown(entity.getId());
                            break;
                        case 2://left
                            gameSession.moveLeft(entity.getId());
                            break;
                        case 3://right
                            gameSession.moveRight(entity.getId());
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
