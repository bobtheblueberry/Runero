package org.gcreator.runero.audio;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.res.GameSound;
import org.gcreator.runero.res.GameSound.SoundKind;

public abstract class RuneroSound {

    static AudioPlayer background;
    static AudioPlayer[] players;

    public static void setupAudio() {
        int max = -1;
        for (GameSound s : RuneroGame.game.sounds)
            if (s.getId() > max)
                max = s.getId();
        if (max < 0)
            return; // no sounds
        players = new AudioPlayer[max + 1];
    }

    public static void play(GameSound s, boolean loop) {
        if (s.fileType.toLowerCase().equals(".mp3")) {
            System.out.println("Playing mp3 " + s.fileName);
            playMP3(s, loop);
        } else {
            System.out.println("Playing audio " + s.fileName);
            playWAV(s, loop);
        }

    }

    public static boolean isPlaying(GameSound s) {
        AudioPlayer a = players[s.getId()];
        if (a == null)
            return false;
        return a.isPlaying();
    }

    public static void shutdown() {
        for (AudioPlayer p : players)
            if (p != null)
                p.interrupt();
    }

    private static void playMP3(GameSound s, boolean loop) {
        boolean bg = false;
        if (s.kind == SoundKind.BACKGROUND) {
            bg = true;
            if (background != null) {
                background.interrupt();
                background = null;
            }
        }
        MP3Player p = new MP3Player(s.getData(), s.fileName, loop);
        Thread playerThread = new Thread(p, "MP3 Audio player thread");
        addPlayer(p, s.getId());
        playerThread.start();
        if (bg)
            background = p;
    }

    private static void playWAV(GameSound s, boolean loop) {
        boolean bg = false;
        if (s.kind == SoundKind.BACKGROUND) {
            bg = true;
            if (background != null) {
                background.interrupt();
                background = null;
            }
        }
        WAVPlayer p = new WAVPlayer(s.getData(), s, loop);
        Thread playerThread = new Thread(p, "WAV Audio player thread");
        addPlayer(p, s.getId());
        playerThread.start();
        if (bg)
            background = p;
    }

    private static void addPlayer(AudioPlayer p, int id) {
        AudioPlayer a = players[id];
        if (a != null)
            a.interrupt();
        players[id] = p;
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
