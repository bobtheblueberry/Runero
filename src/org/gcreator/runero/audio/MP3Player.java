package org.gcreator.runero.audio;

import java.io.ByteArrayInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;

import org.gcreator.runero.res.GameSound;

public class MP3Player extends AudioPlayer {

    byte[] data;
    Player player;
    GameSound s;
    boolean loop;

    public MP3Player(byte[] data, GameSound s, boolean loop)
        {
            super(s.fileName);
            this.data = data;
            this.loop = loop;
        }

    public void play(boolean loop) {
        try {
            player = new Player(new ByteArrayInputStream(data), getAudioDevice());
            if (loop)
                while (true)
                    player.play();
            else
                player.play();

        } catch (JavaLayerException e) {
            System.out.println("Cannot play audio " + s.fileName);
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

    static AudioDevice dev;

    static AudioDevice getAudioDevice() {
        try {
            if (dev == null)
                dev = FactoryRegistry.systemRegistry().createAudioDevice();
            return dev;
        } catch (JavaLayerException e) {
            System.err.println("Cannot get audio device");
            e.printStackTrace();
        }
        return null;
    }
}