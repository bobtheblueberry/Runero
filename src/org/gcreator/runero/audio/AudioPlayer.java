package org.gcreator.runero.audio;

public abstract class AudioPlayer extends Thread {

    public AudioPlayer(String name) {
        super(name);
    }
    public abstract void play(boolean loop);
    public abstract boolean isPlaying();
}
