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
 *
 * @author Janessa
 */
public class boardPanel extends JPanel {

    drawPanel gamePanel;
    infoPanel iPanel;
    private GameEntityFacadeRemote GameSession;

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

    public void startGame(String username) {
        System.out.println("boardpanel: " + username);
        gamePanel.startGame(username);
    }

    public boolean isGameOver() {
        return gamePanel.nFlag_gameOver;
    }

    public void setGameOver(boolean val) {
        gamePanel.nFlag_gameOver = val;
    }

    public String getWinner() {
        return gamePanel.winner;
    }

    public String getWinnerColor() {
        return gamePanel.winnerColor;
    }
}
