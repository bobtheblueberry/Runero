package org.gcreator.runero.audio;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.gcreator.runero.res.GameSound;

public class WAVPlayer extends AudioPlayer {

    private byte[] data;
    GameSound sound;
    Clip clip;
    boolean loop;

    public WAVPlayer(byte[] data, GameSound s, boolean loop)
        {
            super(s.fileName);
            this.data = data;
            this.loop = loop;
            this.sound = s;
        }

    public void run() {
        play(loop);
    }

    public void play(boolean loop) {

        AudioInputStream stream = null;
        try {
            stream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(data));
        } catch (UnsupportedAudioFileException e1) {
            System.err.println("Unsupported audio " + sound.fileName);
            e1.printStackTrace();
            return;
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
        AudioFormat format = stream.getFormat();

        if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            AudioFormat tmp = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(),
                    format.getSampleSizeInBits() * 2, format.getChannels(), format.getFrameSize() * 2,
                    format.getFrameRate(), true);
            stream = AudioSystem.getAudioInputStream(tmp, stream);
            format = tmp;
        }

        DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(),
                ((int) stream.getFrameLength() * format.getFrameSize()));

        try {
            clip = (Clip) AudioSystem.getLine(info);
            if (loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            if (clip.isControlSupported(FloatControl.Type.PAN)) {
                FloatControl panControl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
                panControl.setValue((float) sound.pan);
            }
            if (clip.isControlSupported(FloatControl.Type.VOLUME)) {
                FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.VOLUME);
                volume.setValue((float) sound.volume);
            }
            clip.open(stream);
            clip.start();
        } catch (Exception e) {
            System.err.println("Cannot play " + sound.fileName);
            e.printStackTrace();
        }
    }

    @Override
    public boolean isPlaying() {
        if (clip == null)
            return false;
        return clip.isRunning();
    }
}
