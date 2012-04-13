/*
    This program is a runner for Game Maker style games

    Copyright (c) 2012 Serge Humphrey<bobtheblueberry@gmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gcreator.runero.gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

public class TextureLoader {

    public LinkedList<Texture> textures;
    
    public TextureLoader() {
        textures = new LinkedList<Texture>();
    }
    
    /**
     * Load a texture
     *
     * @param resourceName The location of the resource to load
     * @return The loaded texture
     * @throws IOException Indicates a failure to access the resource
     */
    public Texture getTexture(File res) {
        long start = System.currentTimeMillis();
        try {
            BufferedImage i = ImageIO.read(res);
            Texture t = BufferedImageUtil.getTexture(res.getName(), i);
            textures.add(t);
            long time = System.currentTimeMillis() - start;
            System.out.println("Loaded " + res + " in " + time + " ms");
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}