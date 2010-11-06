/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aseproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import entity.PlayerEntity;
import facade.PlayerEntityFacadeRemote;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import session.GameEntityFacadeRemote;

/**
 *
 * @author Administrator
 */
public class registerPanel extends JPanel implements ActionListener {

    static final int PANEL_MODE_DEFAULT = 0;
    static final int PANEL_MODE_NEW_ACCOUNT = 1;
    static final int PANEL_MODE_LOGIN = 2;

    public boolean nFlag_registered = false;

    int panelMode = PANEL_MODE_DEFAULT;

    JLabel gameTitle;
    JLabel newAccountUserLabel;
    JLabel newAccountPswLabel;
    JButton newAccountBtn;
    JButton loginBtn;
    JButton newAccountOK;
    JTextField newAccountUser;
    JTextField newAccountPsw;
    JTextField loginUserName;
    JPasswordField pswField;
    JButton loginOKBtn;
    PlayerEntityFacadeRemote playerFacade;
    PlayerEntity User;

    String username;

    public registerPanel() {

        setBackground(new java.awt.Color(255, 255, 204));

        newAccountBtn = new JButton();
        newAccountBtn.setFont(new java.awt.Font("Algerian", 1, 12));
        newAccountBtn.setText("NEW ACCOUNT");
        newAccountBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        newAccountBtn.setMinimumSize(new Dimension(300, 60));

        loginBtn = new JButton();
        loginBtn.setFont(new java.awt.Font("Algerian", 1, 12));
        loginBtn.setText("LOG ON");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        gameTitle = new JLabel();
        gameTitle.setFont(new java.awt.Font("Algerian", 1, 36)); // NOI18N
        gameTitle.setForeground(new java.awt.Color(51, 51, 255));
        gameTitle.setText("Divine Battle of the Galaxies");
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        loginOKBtn = new JButton();
        loginOKBtn.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        loginOKBtn.setText("OK");
        loginOKBtn.addActionListener(this);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        newAccountUserLabel = new JLabel();
        newAccountUserLabel.setFont(new java.awt.Font("Algerian", 0, 12)); // NOI18N
        newAccountUserLabel.setText("ENTER YOUR USERNAME");
        newAccountUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        newAccountUser = new JTextField(20);
        newAccountUser.setFont(new java.awt.Font("Algerian", 0, 12)); // NOI18N
        newAccountUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        newAccountUser.addActionListener(this);
        newAccountUser.setMaximumSize(newAccountUser.getPreferredSize());

        newAccountPswLabel = new JLabel();
        newAccountPswLabel.setFont(new java.awt.Font("Algerian", 0, 12)); // NOI18N
        newAccountPswLabel.setText("ENTER YOUR PASSWORD");
        newAccountPswLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        newAccountPsw = new JPasswordField(20);
        newAccountPsw.setAlignmentX(Component.CENTER_ALIGNMENT);
        newAccountPsw.addActionListener(this);
        newAccountPsw.setMaximumSize(newAccountPsw.getPreferredSize());

        loginUserName = new JTextField(20);
        loginUserName.setFont(new java.awt.Font("Algerian", 0, 12)); // NOI18N
        loginUserName.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginUserName.addActionListener(this);
        loginUserName.setMaximumSize(loginUserName.getPreferredSize());

        newAccountOK = new JButton("OK");
        newAccountOK.setFont(new java.awt.Font("Algerian", 1, 12)); // NOI18N
        newAccountOK.setText("OK");
        newAccountOK.addActionListener(this);
        newAccountOK.setAlignmentX(Component.CENTER_ALIGNMENT);

        pswField = new JPasswordField(20);
        pswField.addActionListener(this);
        pswField.setAlignmentX(Component.CENTER_ALIGNMENT);
        pswField.setMaximumSize(pswField.getPreferredSize());
        add(gameTitle);
        add(newAccountBtn);
        add(loginBtn);
        newAccountBtn.addActionListener(this);
        loginBtn.addActionListener(this);
        playerFacade = lookupPlayerEntityFacadeRemote();
    }

    public void rebuildPanel() {
        removeAll();
        add(gameTitle);
        add(newAccountBtn);
        add(loginBtn);
    }

    private void doLogin() {
        rebuildPanel();
        add(newAccountUserLabel);
        add(loginUserName);
        add(newAccountPswLabel);
        add(pswField);
        add(loginOKBtn);
        revalidate();
        repaint();
        panelMode = PANEL_MODE_LOGIN;
    }

    private void doNewAccount() {
        rebuildPanel();
        add(newAccountUserLabel);
        add(newAccountUser);
        add(newAccountPswLabel);
        add(newAccountPsw);
        add(newAccountOK);
        revalidate();
        repaint();
        panelMode = PANEL_MODE_NEW_ACCOUNT;
    }

    private void doNewAccountOK() {
        username = newAccountUser.getText();
        PlayerEntity user = (PlayerEntity) playerFacade.find(username);
        if (user == null) {
            String input = newAccountPsw.getText();
            user = new PlayerEntity();
            user.setId(username);
            user.setPassword(input);
            playerFacade.create(user);
            JOptionPane.showMessageDialog(null, "user ID: " + username + "  " + "Password: " + input);
            nFlag_registered = true;
        } else {
            System.out.println("Try another user ID.");
            JOptionPane.showMessageDialog(null, "this ID already exists, please try another one.");
            username = null;
        }
    }

    private void doLoginOK() {
        username = loginUserName.getText();
        System.out.println(username);
//            userEntity newUser = new userEntity();
//            newUser.setId(12345);
//            newUser.setPassword("1234567");
        char[] input = pswField.getPassword();
        playerFacade = lookupPlayerEntityFacadeRemote();
//            userFacade.create(newUser);
//            List users = userFacade.findAll();
//            for (Iterator it = users.iterator(); it.hasNext();) {
//                userEntity user = (userEntity) it.next();
//                System.out.println(user.getId());
//                System.out.println(user.getPassword());
//            }
        User = playerFacade.find(username);
        if (User == null) {
            JOptionPane.showMessageDialog(null, "User does not exist");
            username = null;
        } else {
//                System.out.println("Got the user: " + User.getUsername());
            boolean isCorrect;
            String correctPassword = User.getPassword();
            char[] charPsw = correctPassword.toCharArray();
            isCorrect = Arrays.equals(input, charPsw);
            if (isCorrect) {
                nFlag_registered = true;
                System.out.println("User authenticated with pwd: " + User.getPassword() + " var: " + nFlag_registered);
            } else {
                JOptionPane.showMessageDialog(null, "Password Error");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == loginBtn) {
            doLogin();
        }
        else if (src == newAccountBtn) {
            doNewAccount();
        }
        else if (src == newAccountOK || src == newAccountUser || src == newAccountPsw) {
            doNewAccountOK();
        }
        else if(src == loginOKBtn || src == loginUserName || src == pswField) {
            doLoginOK();
        }

    }


    private PlayerEntityFacadeRemote lookupPlayerEntityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (PlayerEntityFacadeRemote) c.lookup("java:global/aseProject/aseProject-ejb/PlayerEntityFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }


}
