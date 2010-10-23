/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package collide;

import javax.swing.*;


/**
 *
 * @author Administrator
 */
public class gui {
    JFrame window;
    drawPanel panel;
    public gui()
    {
        window = new JFrame("This is just a test for collision.");
        panel = new drawPanel();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(panel);
        window.setVisible(true);

    }

    public void go(){
        panel.startGame();
    }

    public static void main(String[] args)
    {
        gui game = new gui();
        game.go();
    }

}
