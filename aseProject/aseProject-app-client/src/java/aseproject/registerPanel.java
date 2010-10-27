/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aseproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 *
 * @author Administrator
 */
public class registerPanel extends JPanel implements ActionListener {

    public boolean nFlag_registered = false;
    JButton newAccountBtn;
    JButton loginBtn;
    JPasswordField pswField;
    JButton loginOKBtn;

    public registerPanel() {
        newAccountBtn = new JButton("Create New Account");
        loginBtn = new JButton("Log in with Existed Account");
        loginOKBtn = new JButton("OK");
        loginOKBtn.addActionListener(this);
        pswField = new JPasswordField(4);
        pswField.addActionListener(this);
        add(newAccountBtn);
        add(loginBtn);
        newAccountBtn.addActionListener(this);
        loginBtn.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == loginBtn) {
            add(pswField);
            add(loginOKBtn);
            revalidate();
        }
        if (src == loginOKBtn) {

            char[] input = pswField.getPassword();
            boolean isCorrect;
            char[] correctPassword = {'b', 'u'};
            isCorrect = Arrays.equals(input, correctPassword);
            if (isCorrect) {
                nFlag_registered = true;
            }
        }
    }
}
