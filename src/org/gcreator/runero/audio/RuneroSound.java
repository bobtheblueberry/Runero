package org.gcreator.runero.audio;

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
        MP3Player p = new MP3Player(s.getData(), s, loop);
        addPlayer(p, s.getId());
        p.start();
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
        addPlayer(p, s.getId());
        p.start();
        if (bg)
            background = p;
    }

    private static void addPlayer(AudioPlayer p, int id) {
        AudioPlayer a = players[id];
        if (a != null)
            a.interrupt();
        players[id] = p;
    }
}
