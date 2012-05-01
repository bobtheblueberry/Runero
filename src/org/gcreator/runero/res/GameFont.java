package org.gcreator.runero.res;

import java.awt.Font;

import org.gcreator.runero.gfx.TrueTypeFont;

public class GameFont extends GameResource {

    public String fontName;
    public int size;
    public boolean bold;
    public boolean italic;
    public int rangeMin;
    public int rangeMax;
    public int antialias;
    public int charset;

    private TrueTypeFont ttf;
    private Font font;

    public GameFont(String name) {
        super(name);
    }

    public Font getFont() {
        if (font != null)
            return font;
        int style = Font.PLAIN;
        if (italic)
            style |= Font.ITALIC;
        if (bold)
            style |= Font.BOLD;
        font = new Font(fontName, style, size);
        return font;
    }
    
    public TrueTypeFont load() {
        if (ttf == null)
            ttf = new TrueTypeFont(getFont(), antialias > 0);
        return ttf;
    }
}
