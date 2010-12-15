/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GameMasterPanel.java
 *
 * Created on Nov 7, 2010, 3:13:39 AM
 */

package aseproject;

import session.GameEntityFacadeRemote;

/**
 * This is the JPanel containing the debug game master stuff,
 * including the 2 buttons to initialize game board and clear
 * game board.
 * @author Andrew
 */
public class GameMasterPanel extends javax.swing.JPanel {

    GameEntityFacadeRemote gameSession;
    long updateCounter;

    /** Creates new form GameMasterPanel */
    public GameMasterPanel() {
        initComponents();
        gameSession = Lookup.lookupGameEntityFacadeRemote();

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        initBoardButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        updateCounterLabel = new javax.swing.JLabel();
        clearBoardButton = new javax.swing.JButton();

        initBoardButton.setText("Initialize Board");
        initBoardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initBoardButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Monster movement ticks:");

        updateCounterLabel.setText("0");

        clearBoardButton.setText("Clear Board");
        clearBoardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBoardButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
                .addComponent(updateCounterLabel)
                .addGap(62, 62, 62))
            .addGroup(layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clearBoardButton)
                    .addComponent(initBoardButton))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(initBoardButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clearBoardButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(updateCounterLabel))
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Initialize the game objects on the board.
     * @param evt
     */
    private void initBoardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initBoardButtonActionPerformed
        gameSession = Lookup.lookupGameEntityFacadeRemote();
        gameSession.initGameBoard();
    }//GEN-LAST:event_initBoardButtonActionPerformed

    /**
     * When you click the Clear Board button, clear the board.
     * @param evt
     */
    private void clearBoardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBoardButtonActionPerformed
        gameSession.clearGameBoard();
    }//GEN-LAST:event_clearBoardButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearBoardButton;
    private javax.swing.JButton initBoardButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel updateCounterLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * sets the counter displaying the number of times the
     * Timer fired.
     * @param counter
     */
    public void setUpdateCounter(long counter) {
        this.updateCounter = counter;
        updateCounterLabel.setText(String.valueOf(counter));
    }

}
