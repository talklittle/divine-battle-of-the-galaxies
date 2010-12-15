/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aseproject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Utility class to play sounds.
 * @author Andrew
 */
public class SoundEffects {
    public static void playSound(String filename) {
        try {
            InputStream in = new FileInputStream("assets/"
                                                 + filename);
            AudioStream as = new AudioStream(in);
            AudioPlayer.player.start(as);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
