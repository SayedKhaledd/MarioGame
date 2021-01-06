package Texture;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import static javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;
import javax.sound.sampled.DataLine;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MohamedMashhor
 */
public class SoundEffects {

    final String COLLECTCOIN = "Assets\\Sounds\\collectCoin.wav";
    final String BACKGROUNDMUSIC = "Assets\\Sounds\\backgroundMusic.wav";
    final String LEVELUP = "Assets\\Sounds\\levelFinished.wav";
    final String TURTLEDIED = "Assets\\Sounds\\turtleDied.wav";
    final String JUMP = "Assets\\Sounds\\jump.wav";
    final String DIED = "Assets\\Sounds\\died.wav";
    Clip clip;

    public void playSound(String soundEffect, int loop) {
        try {
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            stream = AudioSystem.getAudioInputStream(new File(soundEffect));
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.loop((loop == -1 ? LOOP_CONTINUOUSLY : loop));
            clip.start();
        } catch (Exception e) {
        }
    }
}
