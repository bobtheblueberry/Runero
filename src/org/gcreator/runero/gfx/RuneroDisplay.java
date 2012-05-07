package org.gcreator.runero.gfx;

import static org.lwjgl.opengl.GL11.*;

import org.gcreator.runero.RuneroGame;
import org.gcreator.runero.gml.lib.KeyboardLibrary;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class RuneroDisplay {

    int width = 640;
    int height = 480;
    boolean gameRunning = true;
    RuneroGame game;
    GraphicsLibrary g = GraphicsLibrary.gfx;

    public void start(RuneroGame game) {
        this.game = game;
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle("Runero");
            Display.create();

            // Uses ENIGMA's code
            glEnable(GL_TEXTURE_2D);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glAlphaFunc(GL_ALWAYS, 0);
            glEnable(GL_BLEND);

            
            // disable the OpenGL depth test since we're rendering 2D graphics
            glDisable(GL_DEPTH_TEST);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();

            glOrtho(0, width, height, 0, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            glViewport(0, 0, width, height);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        game.preload();
        GraphicsLibrary.gfx.init();

        // init OpenGL here

        gameLoop();

       Display.destroy();
    }

    private void gameLoop() {
        while (gameRunning) {
            Display.sync((int) Math.round(game.room_speed));
            // clear screen
            if (RuneroGame.room.room.draw_background_color) {
                g.setClearColor(RuneroGame.room.room.background_color);
            }
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            glColor4f(1, 1, 1, 1);
            // let subsystem paint
            game.update();
            game.render();
            // update window contents
            Display.update();
            
            if (!Display.isActive()) {
                KeyboardLibrary.clearKeyCache();
            }
            
            if (Display.isCloseRequested())
                gameRunning = false;
        }
    }
}