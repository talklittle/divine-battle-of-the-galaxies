/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aseproject;

import entity.PlayerEntity;
import facade.PlayerEntityFacadeRemote;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Janessa
 */
public class infoPanel extends JPanel {
    ImageIcon img;
    //GameEntityFacadeRemote GameSession;
    private PlayerEntity user;
    private String username;
    private int numStars;
    private boolean initialized;
    BoxLayout layout;
    JLabel starsLabel;
    JLabel spriteLabel;
    JLabel numStarsLabel;
    JLabel frozenLabel;
    JLabel userLabel;
    PlayerEntityFacadeRemote playerSession;

    public infoPanel() {
        playerSession = Lookup.lookupPlayerEntityFacadeRemote();
        
        layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(175,400));
        this.setMaximumSize(getPreferredSize());
        numStars = 0;
        initialized = false;
        userLabel = new JLabel();
        userLabel.setFont(new java.awt.Font("Algerian", 0, 18));
        userLabel.setForeground(new java.awt.Color(202, 0, 0));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        starsLabel = new JLabel();
        starsLabel.setFont(new java.awt.Font("Algerian", 0, 18));
        starsLabel.setText("# of Stars: ");
        starsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        numStarsLabel = new JLabel();
        numStarsLabel.setFont(new java.awt.Font("Algerian", 0, 36));
        numStarsLabel.setText(""+numStars);
        numStarsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        frozenLabel = new JLabel();
        frozenLabel.setFont(new java.awt.Font("Algerian", 0, 24));
        frozenLabel.setForeground(new java.awt.Color(51, 51, 255));
        frozenLabel.setText("");
        frozenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void initInfo(String id) {
        this.username = id;
        this.user = playerSession.find(username);
        userLabel.setText(user.getId());
        System.out.println("initinfo: "+user.getId());
        numStars = user.getStars();
        try {
            String path = "assets/sprite-"+user.getColor()+".png";
            img = new ImageIcon(path,"player sprite");
            //initialized = true;

            spriteLabel = new JLabel(img,JLabel.CENTER);
            spriteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            removeAll();
            add(userLabel);
            add(spriteLabel);
            add(starsLabel);
            add(numStarsLabel);
            add(frozenLabel);
            initialized = (user!=null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateInfo() {
        if(!initialized)
            return;
        user = playerSession.find(username);
        if(user != null) {
            numStars = user.getStars();
            numStarsLabel.setText(""+numStars);
            if (user.isFrozen()) {
                frozenLabel.setText("Frozen");
            } else {
                frozenLabel.setText("");
            }
        }
        repaint();
    }

    /*@Override
    public void paintComponent(Graphics g) {
         super.paintComponent(g);    // paints background
         if(initialized) {
             g.drawImage(img, 0, 0, null);
            numStars = user.getStars();
        }
        //g.drawString("Number of Stars:"+numStars, 0, 150);
    }*/
}
