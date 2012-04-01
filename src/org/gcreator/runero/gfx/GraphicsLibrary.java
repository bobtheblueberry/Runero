package org.gcreator.runero.gfx;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.res.GameBackground;
import org.gcreator.runero.res.GameFont;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;

public class GraphicsLibrary {

    private TrueTypeFont font;
    public int fontAlign; // Font alignment, 0,1,2 (left,center,right), respectively

    private GraphicsLibrary() {
    }

    public void init() {
        font = new TrueTypeFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12), true);
    }

    public final static GraphicsLibrary gfx = new GraphicsLibrary();

    public void setTitle(String s) {
        Display.setTitle(s);
    }
    
    public void setColor(Color c) {
        glColor4d(c.getRed() / 255.0, c.getGreen() / 255.0, c.getBlue() / 255.0, c.getAlpha() / 255.0);
    }

    public void setClearColor(Color c) {
        glClearColor(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
    }

    public void drawOval(double x, double y, double width, double height) {
        oval(x, y, width, height, GL_LINE_LOOP);
    }

    public void fillOval(double x, double y, double width, double height) {
        oval(x, y, width, height, GL_TRIANGLE_FAN);
    }

    private void oval(double x, double y, double width, double height, int mode) {
        double theta;
        double angle_increment;
        double x1;
        double y1;

        angle_increment = Math.PI * 2 / 500;
        glPushMatrix();

        glTranslated(x + (width / 2), y + (height / 2), 0);

        glBegin(mode);
        if (mode == GL_TRIANGLE_FAN)
            glVertex2d(0, 0);
        for (theta = 0.0; theta < Math.PI; theta += angle_increment) {
            x1 = (width / 2 * Math.cos(theta));
            y1 = (height / 2 * Math.sin(theta));

            glVertex2d(x1, y1);
        }
        glEnd();

        glPopMatrix();
    }

    public void drawOvalGradient(double x, double y, double width, double height, Color c1, Color c2) {
        double theta;
        double angle_increment;
        double x1;
        double y1;

        angle_increment = Math.PI * 2 / 500;
        glPushMatrix();

        glTranslated(x + (width / 2), y + (height / 2), 0);

        glBegin(GL_TRIANGLE_FAN);
        setColor(c1);
        glVertex2d(0, 0);
        setColor(c2);
        for (theta = 0.0; theta < Math.PI; theta += angle_increment) {
            x1 = (width / 2 * Math.cos(theta));
            y1 = (height / 2 * Math.sin(theta));

            glVertex2d(x1, y1);
        }
        glEnd();

        glPopMatrix();
    }

    public void drawArrow(double x1, double y1, double x2, double y2, double tipSize) {
        drawLine(x1, y1, x2, y2);

        double rad = 0.32;
        double angle = Math.atan2((y2 - y1), (x2 - x1)) - Math.PI / 2;
        double tx1 = x2 - Math.cos(angle + rad) * tipSize;
        double tx2 = x2 - Math.cos(angle - rad) * tipSize;
        double ty1 = y2 + Math.sin(angle + rad) * tipSize;
        double ty2 = y2 + Math.sin(angle - rad) * tipSize;
        glPushMatrix();
        glBegin(GL_POLYGON);
        glVertex2d(tx1, ty1);
        glVertex2d(tx2, ty2);
        glVertex2d(x2, y2);
        glEnd();
        glPopMatrix();
    }

    public void drawLine(double x1, double y1, double x2, double y2) {
        glPushMatrix();
        glBegin(GL_LINE_LOOP);
        for (int i = 0; i < 10; i++) {
            int v = i * 10;
            glVertex2d(x1 + v, y1 + v);
            glVertex2d(x2 + v, y2 + v);
        }
        glEnd();
        glPopMatrix();
    }

    public void fillRect(double x, double y, double width, double height) {
        glRectd(x, y, x + width, y + height);
    }

    public void drawRect(double x, double y, double width, double height) {
        glPushMatrix();
        glTranslated(x, y, 0);
        glBegin(GL_LINE_LOOP);
        {
            glVertex2d(0, 0);
            glVertex2d(0, height);
            glVertex2d(width, height);
            glVertex2d(width, 0);
        }
        glEnd();
        glPopMatrix();
    }

    public void drawRectGradientHor(double x, double y, double width, double height, Color c1, Color c2) {
        glPushMatrix();
        glTranslated(x, y, 0);
        glBegin(GL_QUADS);
        {
            setColor(c1);
            glVertex2d(0, 0);
            glVertex2d(0, height);
            setColor(c2);
            glVertex2d(width, height);
            glVertex2d(width, 0);
        }
        glEnd();
        glPopMatrix();
    }

    public void drawRectGradientVert(double x, double y, double width, double height, Color c1, Color c2) {
        glPushMatrix();
        glTranslated(x, y, 0);
        glBegin(GL_QUADS);
        {
            setColor(c1);
            glVertex2d(0, 0);
            glVertex2d(width, 0);
            setColor(c2);
            glVertex2d(0, height);
            glVertex2d(width, height);
        }
        glEnd();
        glPopMatrix();
    }

    /**
     * Stolen from LateralGM xD
     * @param bg
     * @param x
     * @param y
     * @param tileh
     * @param tilev
     * @param stretch
     */
    public void drawBackground(GameBackground bg, double x, double y, boolean tileh, boolean tilev, boolean stretch) {
        Texture bi = bg.getBackground();
        if (bi == null)
            return;
        int w = stretch ? RuneroGame.room.width : bi.getImageWidth();
        int h = stretch ? RuneroGame.room.height : bi.getImageHeight();
        // TODO: View bounds
        Rectangle c = new Rectangle(0, 0, RuneroGame.room.width, RuneroGame.room.height);
        if (tileh || tilev) {
            double ncol = 1;
            double nrow = 1;
            if (tileh) {
                x = 1 + c.getX() + ((x + w - 1 - c.getX()) % w) - w;
                ncol = 1 + (c.getX() + c.getWidth() - x - 1) / w;
            }
            if (tilev) {
                y = 1 + c.getY() + ((y + h - 1 - c.getY()) % h) - h;
                nrow = 1 + (c.getY() + c.getHeight() - y - 1) / h;
            }

            for (int row = 0; row < nrow; row++)
                for (int col = 0; col < ncol; col++) {
                    drawTexture(bi, (x + w * col), (y + h * row), w, h);
                }
        } else
            drawTexture(bi, x, y, w, h);
    }

    public void drawTexture(Texture t, double x, double y, double width, double height) {
        glColor4d(1, 1, 1, 1);
        // store the current model matrix
        glPushMatrix();
        // bind to the appropriate texture for this sprite
        t.bind();

        // translate to the right location and prepare to draw
        glTranslated(x, y, 0);

        // draw a quad textured to match the sprite
        glBegin(GL_QUADS);
        {
            glTexCoord2d(0, 0);
            glVertex2d(0, 0);

            glTexCoord2d(t.getWidth(), 0);
            glVertex2d(width, 0);

            glTexCoord2d(t.getWidth(), t.getHeight());
            glVertex2d(width, height);

            glTexCoord2d(0, t.getHeight());
            glVertex2d(0, height);

        }
        glEnd();

        // restore the model view matrix to prevent contamination
        glPopMatrix();
    }

    public void screen_redraw() {
        // TODO This probably is broken
        int width = RuneroGame.room.width;
        int height = RuneroGame.room.height;
        glViewport(0, 0, width, height); // Possible bug
        glLoadIdentity();
        glScalef(1, -1, 1);
        glOrtho(-1, width, -1, height, 0, 1); // possible bug

        if (RuneroGame.room.room.draw_background_color) {
            setClearColor(RuneroGame.room.room.background_color);
        }
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        RuneroGame.room.render(this);
    }

    /**
     * Draw Texture
     * 
     * @param x x-coord
     * @param y y-coord
     */
    public void drawTexture(Texture t, double x, double y) {
        drawTexture(t, x, y, t.getImageWidth(), t.getImageHeight());
    }

    public void setFont(GameFont f) {
       //TODO: fonts are broken
       // font = f.load();
    }

    public void drawString(String text, float x, float y) {
        drawString(x, y, text);
    }

    public void drawString(float x, float y, String text) {
       // font.drawString(x, y, text, 1, 1, fontAlign);
    }

    public void drawString(float x, float y, String text, float xscale, float yscale, float angle) {
     //   font.drawString(x, y, text, 1, 1, xscale, yscale, angle, fontAlign);
    }

    public void drawString(double x, double y, String text, double xscale, double yscale, double angle) {
        //font.drawString((float) x, (float) y, text, 1, 1, (float) xscale, (float) yscale, (float) angle, fontAlign);
    }

    public void saveScreenshot(File file) {
        String format = "PNG"; // Example: "PNG" or "JPG"
        // TODO SUPPORT Views
        glReadBuffer(GL_FRONT);
        int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.

        int width = Display.getWidth();
        int height = Display.getHeight();
        java.nio.ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
        glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                int i = (x + (width * y)) * bpp;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
            }

        try {
            javax.imageio.ImageIO.write(image, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
