/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aseproject;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import session.GameEntityFacadeRemote;

/**
 * The container panel holding the drawPanel and infoPanel
 * @author Janessa
 */
public class boardPanel extends JPanel {

    drawPanel gamePanel;
    infoPanel iPanel;
    private GameEntityFacadeRemote GameSession;

    /**
     * default constructor. set up panels.
     */
    public boardPanel() {
        this.setLayout(new BorderLayout());
        gamePanel = new drawPanel();
        add(gamePanel, BorderLayout.CENTER);

        iPanel = new infoPanel();
        iPanel.setBorder(BorderFactory.createEmptyBorder(70, 30,
                                                         30, 30));
        GameSession = Lookup.lookupGameEntityFacadeRemote();
        GameSession.gameBoard();
        add(iPanel, BorderLayout.EAST);
    }

    /**
     * wrapper method to drawPanel.startGame()
     * @param username
     */
    public void startGame(String username) {
        System.out.println("boardpanel: " + username);
        gamePanel.startGame(username);
    }

    /**
     * tell whether game is over
     * @return
     */
    public boolean isGameOver() {
        return gamePanel.nFlag_gameOver;
    }

    /**
     * wrapper to set gameOver in the drawPanel
     * @param val
     */
    public void setGameOver(boolean val) {
        gamePanel.nFlag_gameOver = val;
    }

    /**
     * get the winner
     * @return
     */
    public String getWinner() {
        return gamePanel.winner;
    }

    /**
     * get the color of winner
     * @return
     */
    public String getWinnerColor() {
        return gamePanel.winnerColor;
    }
}
