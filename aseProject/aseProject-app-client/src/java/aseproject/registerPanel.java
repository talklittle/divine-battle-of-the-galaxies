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
import facade.PlayerEntityFacadeRemote;
import entity.PlayerEntity;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class registerPanel extends JPanel implements ActionListener {

    public boolean nFlag_registered = false;
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
        newAccountBtn = new JButton("Create New Account");
        loginBtn = new JButton("Log in with Existed Account");
        loginOKBtn = new JButton("OK");
        loginOKBtn.addActionListener(this);
        newAccountUser = new JTextField(4);
        newAccountPsw = new JPasswordField(4);
        loginUserName = new JTextField(4);
        newAccountOK = new JButton("OK");
        newAccountOK.addActionListener(this);
        pswField = new JPasswordField(4);
        pswField.addActionListener(this);
        add(newAccountBtn);
        add(loginBtn);
        newAccountBtn.addActionListener(this);
        loginBtn.addActionListener(this);
        playerFacade = lookupPlayerEntityFacadeRemote();
    }

    public void rebuildPanel() {
        removeAll();
        add(newAccountBtn);
        add(loginBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == loginBtn) {
            rebuildPanel();
            add(loginUserName);
            add(pswField);
            add(loginOKBtn);
            revalidate();
        }
        if (src == newAccountBtn) {
            rebuildPanel();
            add(newAccountUser);
            add(newAccountPsw);
            add(newAccountOK);
            revalidate();
        }
        if (src == newAccountOK) {
            username = newAccountUser.getText();
            PlayerEntity user = playerFacade.find(username);
            if (user == null) {
                String input = newAccountPsw.getText();
                user = new PlayerEntity();
                user.setUsername(username);
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

        if (src == loginOKBtn) {
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
                System.out.println("Got the user: "+User.getUsername());
                boolean isCorrect;
                String correctPassword = User.getPassword();
                char[] charPsw = correctPassword.toCharArray();
                isCorrect = Arrays.equals(input, charPsw);
                if (isCorrect) {
                    nFlag_registered = true;
                    System.out.println("User authenticated with pwd: "+User.getPassword()+" var: "+nFlag_registered);
                } else {
                    JOptionPane.showMessageDialog(null, "Password Error");
                }
            }
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
