/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aseproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import entity.PlayerEntity;
import entity.accountInfo;
import facade.Lookup;
import facade.PlayerEntityFacadeRemote;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import session.accountInfoFacadeRemote;
import util.Colors;

/**
 *
 * @author _yy
 */
public class registerPanel extends JPanel implements ActionListener, MouseListener {

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
    accountInfo account;
    accountInfoFacadeRemote accountInfoFacade;
    String username;

    Main parent;

    public registerPanel(Main m) {

        parent = m;

        setBackground(new java.awt.Color(255, 255, 204));
        this.setIgnoreRepaint(true);
        newAccountBtn = new JButton();
        newAccountBtn.setFont(new java.awt.Font("Algerian", 1, 12));
        newAccountBtn.setText("NEW ACCOUNT");
        newAccountBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        newAccountBtn.setMinimumSize(new Dimension(300, 60));
        newAccountBtn.addMouseListener(this);

        loginBtn = new JButton();
        loginBtn.setFont(new java.awt.Font("Algerian", 1, 12));
        loginBtn.setText("LOG ON");
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.addMouseListener(this);

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
        loginOKBtn.addMouseListener(this);
        loginOKBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        newAccountOK.addMouseListener(this);
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
        playerFacade = Lookup.lookupPlayerEntityFacadeRemote();
        accountInfoFacade = Lookup.lookupaccountInfoFacadeRemote();
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
        account = (accountInfo) accountInfoFacade.find(username);
        if (account == null) {
            String input = newAccountPsw.getText();
            User = new PlayerEntity();
            account = new accountInfo();
            account.setId(username);
            account.setPsw(input);
            User.setId(username);
            Random ranColor = new Random();
            String random_color = Colors.COLOR_STRINGS[ranColor.nextInt(8)];
            User.setColor(random_color);
            playerFacade.create(User);
            accountInfoFacade.create(account);
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
        char[] input = pswField.getPassword();
        account = accountInfoFacade.find(username);

        if (account == null) {
            JOptionPane.showMessageDialog(null, "User does not exist");
            username = null;
        } else {
            boolean isCorrect;
            String correctPassword = account.getPsw();
            char[] charPsw = correctPassword.toCharArray();
            isCorrect = Arrays.equals(input, charPsw);
            if (isCorrect) {
                if(username.equals("admin")) {
                    parent.window.remove(this);
                    parent.adminConsole();
                    return;
                }
                nFlag_registered = true;
                //System.out.println("nFlag_admin="+nFlag_admin);
                User = playerFacade.find(username);
                if (User == null) {
                    System.out.println("THE USER IS NOT IN GAME, CREATE NEW CHARACTER");
                    User = new PlayerEntity();
                    User.setId(username);
                    Random ranColor = new Random();
                    String random_color = Colors.COLOR_STRINGS[ranColor.nextInt(8)];
                    User.setColor(random_color);
                    playerFacade.create(User);
                } else {
                    User.setX(0);
                    User.setY(0);
                    User.setStars(0);
                    playerFacade.edit(User);
                }
                System.out.println("User authenticated with pwd: " + account.getPsw() + " var: " + nFlag_registered);
            } else {
                JOptionPane.showMessageDialog(null, "Password Error");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == loginBtn) {
            SoundEffects.playSound("49208__tombola__Fisher_Price24.wav");
            doLogin();
        } else if (src == newAccountBtn) {
            SoundEffects.playSound("49208__tombola__Fisher_Price24.wav");
            doNewAccount();
        } else if (src == newAccountOK || src == newAccountUser || src == newAccountPsw) {
            SoundEffects.playSound("49208__tombola__Fisher_Price24.wav");
            doNewAccountOK();
        } else if (src == loginOKBtn || src == loginUserName || src == pswField) {
            SoundEffects.playSound("49208__tombola__Fisher_Price24.wav");
            doLoginOK();
        }

    }

    public void mouseEntered(MouseEvent e) {
        SoundEffects.playSound("49206__tombola__Fisher_Price22.wav");
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}
