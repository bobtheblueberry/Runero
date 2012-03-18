package org.gcreator.runero.res;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.rtf.RTFEditorKit;

public class GameInformation implements WindowListener {

    public Color backgroundColor;
    public boolean mimicGameWindow; // ?
    public String caption;
    public int left = -1; // ?
    public int top = -1; // ?
    public int width = 600;
    public int height = 400;
    public boolean showBorder;
    public boolean allowResize;
    public boolean stayOnTop;
    public boolean pauseGame;// TODO: Pause game
    public String text;
    
    JFrame f;
    
    public void showInfoWindow() {
        if (f != null)
            return;
        f = new JFrame(caption);
        JEditorPane editor = new JEditorPane();
        editor.setEditorKit(new RTFEditorKit());
        editor.setBackground(backgroundColor);
        editor.setText(text);
        editor.setEditable(false);
        JScrollPane p = new JScrollPane(editor);
        f.setAlwaysOnTop(stayOnTop);
        f.add(p);
        f.setSize(width, height);
        f.setResizable(allowResize);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.addWindowListener(this);
    }
    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowClosed(WindowEvent e) {
        f = null;
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
        
    }
}
