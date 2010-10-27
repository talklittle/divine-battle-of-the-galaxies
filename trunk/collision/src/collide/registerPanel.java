/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package collide;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 *
 * @author Administrator
 */
public class registerPanel extends JPanel implements ActionListener{
    public boolean nFlag_registered = false;
    JButton newAccountBtn;
    JButton loginBtn;
    JPasswordField pswField;

    public registerPanel() {
        newAccountBtn = new JButton("Create New Account");
        loginBtn = new JButton("Log in with Existed Account");
        pswField = new JPasswordField(4);
        add(newAccountBtn);
        add(loginBtn);
        newAccountBtn.addActionListener(this);
        loginBtn.addActionListener(this);
    }




    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src == newAccountBtn)
        {
            add(pswField);
            nFlag_registered=true;
        }
    }


}
