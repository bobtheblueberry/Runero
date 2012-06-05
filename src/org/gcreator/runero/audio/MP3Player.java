package org.gcreator.runero.audio;

import java.io.ByteArrayInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MP3Player extends AudioPlayer {

    // FIXME MP3 looping
    byte[] data;
    Player player;
    String name;
    boolean loop;

    public MP3Player(byte[] data, String name, boolean loop)
        {
            this.data = data;
            this.name = name;
            this.loop = loop;
        }

    public void play(boolean loop) {
        try {
            player = new Player(new ByteArrayInputStream(data), RuneroSound.getAudioDevice());
            player.play();
        } catch (JavaLayerException e) {
            System.out.println("Cannot play audio " + name);
        }
    }

    @Override
    public void run() {
        play(loop);
    }

    @Override
    public boolean isPlaying() {
        if (player == null)
            return false;
        return player.isComplete();
    }
}