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
import session.userEntityFacadeRemote;
import entity.userEntity;
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
    userEntityFacadeRemote userFacade;
    userEntity User;

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
        userFacade = lookupuserEntityFacadeRemote();
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
            String userName = newAccountUser.getText();
            int userID = Integer.parseInt(userName);
            userEntity user = userFacade.find(userID);
            if (user == null) {
                String input = newAccountPsw.getText();
                user = new userEntity();
                user.setId(userID);
                user.setPassword(input);
                userFacade.create(user);
                JOptionPane.showMessageDialog(null, "user ID:" + userID + "  " + "Password" + input);
                nFlag_registered = true;
            } else {
                System.out.println("Change another user ID.");
                JOptionPane.showMessageDialog(null, "this ID already existed, Change another one.");
            }
        }

        if (src == loginOKBtn) {
            String userName = loginUserName.getText();
            System.out.println(userName);
//            userEntity newUser = new userEntity();
//            newUser.setId(12345);
//            newUser.setPassword("1234567");
            int userID = Integer.parseInt(userName);
            char[] input = pswField.getPassword();
            userFacade = lookupuserEntityFacadeRemote();
//            userFacade.create(newUser);
//            List users = userFacade.findAll();
//            for (Iterator it = users.iterator(); it.hasNext();) {
//                userEntity user = (userEntity) it.next();
//                System.out.println(user.getId());
//                System.out.println(user.getPassword());
//            }
            System.out.println(userID);
            User = userFacade.find(userID);
            System.out.println("Get the user!");
            boolean isCorrect;
            String correctPassword = User.getPassword();
            char[] charPsw = correctPassword.toCharArray();
            isCorrect = Arrays.equals(input, charPsw);
            if (isCorrect) {
                nFlag_registered = true;
            } else {
                JOptionPane.showMessageDialog(null, "Password Error");
            }
        }

    }

    private userEntityFacadeRemote lookupuserEntityFacadeRemote() {
        try {
            Context c = new InitialContext();
            return (userEntityFacadeRemote) c.lookup("java:comp/env/userEntityFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
