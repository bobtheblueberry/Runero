package org.gcreator.runero;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;

import org.gcreator.runero.res.GameSound;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public abstract class RuneroSound {

    public static void play(GameSound s, boolean loop) {
        if (s.fileType.toLowerCase().equals(".mp3")) {
            System.out.println("Playing mp3");
            playMP3(s, loop);
        } else {
            System.err.println("Cannot play sound type " + s.fileType);
        }
    }

    private static void playMP3(GameSound s, boolean loop) {
        MP3Player p = new MP3Player(s.getData());
        Thread playerThread = new Thread(p, "Audio player thread");
        playerThread.start();
    }

    private static class MP3Player implements Runnable {

        byte[] data;

        public MP3Player(byte[] data)
            {
                this.data = data;
            }

        @Override
        public void run() {

            try {
                Player player = new Player(new ByteInputStream(data, data.length), FactoryRegistry.systemRegistry().createAudioDevice());
                player.play();
            } catch (JavaLayerException e) {
                System.err.println("Cannot play mp3");
                e.printStackTrace();
            }
        }

    }
}
