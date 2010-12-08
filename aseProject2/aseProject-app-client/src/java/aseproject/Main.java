/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aseproject;

import entity.GameEntity;
import entity.MonsterEntity;
import entity.PlayerEntity;
import facade.PlayerEntityFacadeRemote;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import session.GameEntityFacadeRemote;

/**
 *
 * @author Andrew
 */
public class Main implements ActionListener {

    public static final long FREEZE_DURATION_MILLIS = 3000;

    JFrame window;
    GameMasterPanel gameMasterPanel;
    private GameEntityFacadeRemote gameSession;
    private PlayerEntityFacadeRemote playerSession;

    private long debugMonsterMovements = 0;

    Timer monsterTimer;
    Timer cleanTimer;


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
        playerSession = Lookup.lookupPlayerEntityFacadeRemote();

        initTimers();
    }

    class MonsterTask extends TimerTask {
        public void run() {
            try {
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
            } catch (EJBException ex) {
                try {
                    gameSession = Lookup._lookupGameEntityFacadeRemote();
                } catch (NamingException ex2) {
                } finally {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex2) {}
                }
            }

            gameMasterPanel.setUpdateCounter(++debugMonsterMovements);
        }
    }

    class CleanTask extends TimerTask {
        public void run() {
            try {
                gameSession.cleanOldCollisionEvents();

                List players = playerSession.findAll();
                Iterator iter = players.iterator();
                while (iter.hasNext()) {
                    PlayerEntity player = (PlayerEntity) iter.next();
                    if (player.isFrozen() && ((System.currentTimeMillis() - player.getFrozentime()) > FREEZE_DURATION_MILLIS)) {
                        player.setFrozen(false);
                        player.setFrozentime(0);
                        playerSession.edit(player);
                    }
                }
            } catch (EJBException ex) {
                try {
                    gameSession = Lookup._lookupGameEntityFacadeRemote();
                } catch (NamingException ex2) {
                } finally {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex2) {}
                }
            }
        }
    }

    public void initTimers() {
        monsterTimer = new Timer();
        monsterTimer.scheduleAtFixedRate(new MonsterTask(), 1000, 1000);
        cleanTimer = new Timer();
        cleanTimer.scheduleAtFixedRate(new CleanTask(), 200, 200);
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
