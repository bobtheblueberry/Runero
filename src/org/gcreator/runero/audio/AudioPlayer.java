package org.gcreator.runero.audio;

public abstract class AudioPlayer extends Thread {

    public abstract void play(boolean loop);
    public abstract boolean isPlaying();
}
