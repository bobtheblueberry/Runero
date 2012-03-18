package org.gcreator.runero.gfx;

import org.gcreator.runero.RuneroGame;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.*;

public class RuneroDisplay {

    int width = 640;
    int height = 480;
    boolean gameRunning = true;
    RuneroGame game;

    public void start(RuneroGame game) {
        this.game = game;
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle("Runero");
            Display.create();

            // grab the mouse, dont want that hideous cursor when we're playing!

            // Mouse.setGrabbed(true);

            // enable textures since we're going to use these for our sprites
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND_SRC);

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
            System.exit(0);
        }
        game.preload();
        GraphicsLibrary.gfx.init();

        // init OpenGL here

        gameLoop();

        Display.destroy();
    }

    private void gameLoop() {
        while (gameRunning) {
            Display.sync((int)Math.round(game.room_speed));
            // clear screen
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();

            // let subsystem paint
            game.update();
            game.render();
            // update window contents
            Display.update();
            
            if (Display.isCloseRequested())
                gameRunning = false;
        }
    }
}