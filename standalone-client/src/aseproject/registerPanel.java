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
import facade.PlayerEntityFacadeRemote;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import session.accountInfoFacadeRemote;
import util.Colors;

/**
 * the panel with register/login
 * @author _yy
 */
public class registerPanel extends JPanel
                           implements ActionListener, MouseListener {

    static final int PANEL_MODE_DEFAULT = 0;
    static final int PANEL_MODE_NEW_ACCOUNT = 1;
    static final int PANEL_MODE_LOGIN = 2;
    static final String SOUND_BUTTON_MOUSECLICK
            = "49208__tombola__Fisher_Price24.wav";
    static final String SOUND_BUTTON_MOUSEOVER
            = "49206__tombola__Fisher_Price22.wav";
    public boolean nFlag_registered = false;
    int panelMode = PANEL_MODE_DEFAULT;
    JLabel gameTitle;
    JLabel newAccountUserLabel;
    JLabel newAccountPswLabel;
    JButton newAccountBtn;
    JButton loginBtn;
    JButton newAccountOK;
    JTextField newAccountUser;
    JPasswordField newAccountPsw;
    JTextField loginUserName;
    JPasswordField pswField;
    JButton loginOKBtn;
    PlayerEntityFacadeRemote playerFacade;
    PlayerEntity User;
    accountInfo account;
    accountInfoFacadeRemote accountInfoFacade;
    String username;
    Main parent;

    private static final Random random = new Random();

    /**
     * constructor
     * @param m instance of standalone-client/Main
     */
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
        gameTitle.setFont(new java.awt.Font("Algerian", 1, 36));
        gameTitle.setForeground(new java.awt.Color(51, 51, 255));
        gameTitle.setText("Divine Battle of the Galaxies");
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        loginOKBtn = new JButton();
        loginOKBtn.setFont(new java.awt.Font("Algerian", 1, 12));
        loginOKBtn.setText("OK");
        loginOKBtn.addActionListener(this);
        loginOKBtn.addMouseListener(this);
        loginOKBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        newAccountUserLabel = new JLabel();
        newAccountUserLabel.setFont(
                            new java.awt.Font("Algerian", 0, 12));
        newAccountUserLabel.setText("ENTER YOUR USERNAME");
        newAccountUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        newAccountUser = new JTextField(20);
        newAccountUser.setFont(new java.awt.Font("Algerian", 0, 12));
        newAccountUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        newAccountUser.addActionListener(this);
        newAccountUser.setMaximumSize(
                       newAccountUser.getPreferredSize());
        // set the max number of chars
        newAccountUser.setDocument(new JTextFieldLimit(10));

        newAccountPswLabel = new JLabel();
        newAccountPswLabel.setFont(
                           new java.awt.Font("Algerian", 0, 12));
        newAccountPswLabel.setText("ENTER YOUR PASSWORD");
        newAccountPswLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        newAccountPsw = new JPasswordField(20);
        newAccountPsw.setAlignmentX(Component.CENTER_ALIGNMENT);
        newAccountPsw.addActionListener(this);
        newAccountPsw.setMaximumSize(
                      newAccountPsw.getPreferredSize());
        // set the max number of chars
        newAccountPsw.setDocument(new JTextFieldLimit(20));

        loginUserName = new JTextField(20);
        loginUserName.setFont(new java.awt.Font("Algerian", 0, 12));
        loginUserName.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginUserName.addActionListener(this);
        loginUserName.setMaximumSize(
                      loginUserName.getPreferredSize());
        // set the max number of chars
        loginUserName.setDocument(new JTextFieldLimit(10));

        newAccountOK = new JButton("OK");
        newAccountOK.setFont(new java.awt.Font("Algerian", 1, 12));
        newAccountOK.setText("OK");
        newAccountOK.addActionListener(this);
        newAccountOK.addMouseListener(this);
        newAccountOK.setAlignmentX(Component.CENTER_ALIGNMENT);

        pswField = new JPasswordField(20);
        pswField.addActionListener(this);
        pswField.setAlignmentX(Component.CENTER_ALIGNMENT);
        pswField.setMaximumSize(pswField.getPreferredSize());
        // set the max number of chars
        pswField.setDocument(new JTextFieldLimit(20));

        add(gameTitle);
        add(newAccountBtn);
        add(loginBtn);
        newAccountBtn.addActionListener(this);
        loginBtn.addActionListener(this);
        playerFacade = Lookup.lookupPlayerEntityFacadeRemote();
        accountInfoFacade = Lookup.lookupaccountInfoFacadeRemote();
    }

    /**
     * start over from initial screen (let you register or login)
     */
    public void rebuildPanel() {
        removeAll();
        add(gameTitle);
        add(newAccountBtn);
        add(loginBtn);
    }

    /**
     * go to login screen
     */
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

    /**
     * go to registration screen
     */
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

    /**
     * helper method to do MD5 hashing
     * @param pass
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String MD5(String pass) throws NoSuchAlgorithmException {
	MessageDigest m = MessageDigest.getInstance("MD5");
	byte[] data = pass.getBytes();
	m.update(data, 0, data.length);
	BigInteger i = new BigInteger(1, m.digest());
	return String.format("%1$032X", i);
    }

    /**
     * try to create a new account
     * @param newAccount
     * @param input
     * @return whether it was created
     */
    protected boolean createNewAccount(String newAccount,
                                       String input) {
        username = newAccount;
//        System.out.println(username);
        account = (accountInfo) accountInfoFacade.find(username);
        if (account == null) {
            int usernameLength = newAccount.length();
            int passwordLength = input.length();
            if (usernameLength < 1 || usernameLength > 10) {
                JOptionPane.showMessageDialog(null,
                            "Invalid username: length must be between"
                            + " 1 and 10 (inclusive).");
                return false;
            }
            if (passwordLength < 6 || passwordLength > 20) {
                JOptionPane.showMessageDialog(null,
                            "Invalid password: length must be between"
                            + " 6 and 20 (inclusive).");
                return false;
            }
            // Check for characters and numbers only
            Pattern p = Pattern.compile("[^a-zA-Z_0-9]");
            Matcher m = p.matcher(newAccount);

            if(m.find()) {
                JOptionPane.showMessageDialog(null,
                            "Invalid username: Must contain only "
                            + "letters and numbers");
                return false;
            }
            m = p.matcher(input);
            if(m.find()) {
                JOptionPane.showMessageDialog(null,
                            "Invalid password: Must contain only "
                            + "letters and numbers");
                return false;
            }

            // Insert record in accountInfo
            account = new accountInfo();
            account.setId(username);
            String pass = "";
            try {
                pass = MD5(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            account.setPsw(pass);
            accountInfoFacade.create(account);
            JOptionPane.showMessageDialog(null, "user ID: " + username
                                                + "  Password: "
                                                + input);
            if ("admin".equals(username)) {
                parent.window.remove(this);
                parent.adminConsole();
                return true;
            }
            // Insert record in GameEntity
            System.out.println("Hello World");

            if (numPlayers() <= 3) {
                User = new PlayerEntity();
                User.setId(username);
                String random_color
                       = Colors.COLOR_STRINGS[random.nextInt(8)];
                User.setColor(random_color);
                User.setNewGameTime(System.currentTimeMillis());
                playerFacade.create(User);
                parent.gamePanel.iPanel.initInfo(User.getId());
                nFlag_registered = true;
            } else {
                JOptionPane.showMessageDialog(null,
                            "GAME FULL, PLEASE LOGIN LATER");
                nFlag_registered = false;
            }

        } else {
            //System.out.println("Try another user ID.");
            JOptionPane.showMessageDialog(null,
                        "this ID already exists, "
                        + "please try another one.");
            username = null;
            return false;
        }
        return nFlag_registered;
    }

    /**
     * check if login is valid
     * @param oldAccount
     * @param input
     * @return whether it is valid
     */
    protected boolean verifyOldAccount(String oldAccount,
                                       char[] input) {
        username = oldAccount;
//        System.out.println(username);
        account = accountInfoFacade.find(username);

        if (account == null) {
            JOptionPane.showMessageDialog(null,
                                          "User does not exist");
            username = null;
            return false;
        } else {
            boolean isCorrect;
            String correctPassword = account.getPsw();
            String charPsw = "";
            try {
                charPsw = MD5(new String(input));
            } catch (Exception e) {
                e.printStackTrace();
            }
            isCorrect = correctPassword.equals(charPsw);
            // after verify the psw and account
            if (isCorrect) {
                // if it's the admin account
                if (username.equals("admin")) {
                    parent.window.remove(this);
                    parent.adminConsole();
                    return true;
                }
                // see if the user already has a character
                User = playerFacade.find(username);
                // if he doesn't, create a new character on the board
                if (User == null && numPlayers() <= 3) {
                    User = new PlayerEntity();
                    User.setId(username);
                    String random_color
                            = Colors.COLOR_STRINGS[random.nextInt(8)];
                    User.setColor(random_color);
                    User.setNewGameTime(System.currentTimeMillis());
                    playerFacade.create(User);
                    parent.gamePanel.iPanel.initInfo(User.getId());
                    nFlag_registered = true;

                } else if (numPlayers() >= 4) {
                    JOptionPane.showMessageDialog(null,
                                "GAME FULL, PLEASE LOGIN LATER");
                    nFlag_registered = false;
                } else {
                    JOptionPane.showMessageDialog(null,
                                "YOU ARE ALREADY LOGGED IN");
                    nFlag_registered = false;
                }
            } else {
                JOptionPane.showMessageDialog(null,
                                              "Password Incorrect");
                return nFlag_registered;
            }
        }
        return nFlag_registered;
    }

    /**
     * return number of players currently in game
     * @return
     */
    private int numPlayers() {
        int num = 0;
        Iterator it = playerFacade.findAll().iterator();
        while (it.hasNext()) {
            if (it.next() instanceof PlayerEntity) {
                num++;
            }
        }
        return num;
    }


    /**
     * handler for button clicks
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == loginBtn) {
            SoundEffects.playSound(SOUND_BUTTON_MOUSECLICK);
            doLogin();
        } else if (src == newAccountBtn) {
            SoundEffects.playSound(SOUND_BUTTON_MOUSECLICK);
            doNewAccount();
        } else if (src == newAccountOK || src == newAccountUser
                                       || src == newAccountPsw) {
            SoundEffects.playSound(SOUND_BUTTON_MOUSECLICK);
            createNewAccount(newAccountUser.getText(),
                             newAccountPsw.getText());
        } else if (src == loginOKBtn || src == loginUserName
                                     || src == pswField) {
            SoundEffects.playSound(SOUND_BUTTON_MOUSECLICK);
            verifyOldAccount(loginUserName.getText(),
                             pswField.getPassword());
        }

    }

    /**
     * handler for mouseover of buttons (makes a sound)
     * @param e
     */
    public void mouseEntered(MouseEvent e) {
        SoundEffects.playSound(SOUND_BUTTON_MOUSEOVER);
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
