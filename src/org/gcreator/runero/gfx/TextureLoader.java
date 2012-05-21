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

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.zip.InflaterInputStream;

import org.gcreator.runero.Runner;
import org.lwjgl.BufferUtils;

public class TextureLoader {

    public LinkedList<Texture> textures;

    public TextureLoader()
        {
            textures = new LinkedList<Texture>();
        }

    /** Scratch buffer for texture ID's */
    private IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);

    private int createTextureID() {
        glGenTextures(textureIDBuffer);
        return textureIDBuffer.get(0);
    }

    /**
     * Loads a texture
     *
     * @param resourceName The location of the resource to load
     * @return The loaded texture
     * @throws IOException Indicates a failure to access the resource
     */
    public Texture getTexture(String dir, String res) {
        String path = dir + "/" + res;
        try {
            long start = System.currentTimeMillis();
            int target = GL_TEXTURE_2D;
            InflaterInputStream in;
            if (Runner.JAR)
                in = new InflaterInputStream(new BufferedInputStream(TextureLoader.class.getResourceAsStream("/res/"
                        + path)));
            else
                in = new InflaterInputStream(new BufferedInputStream(new FileInputStream(Runner.GameFolder.getPath()
                        + File.separator + path)));
            int width = read4(in);
            int height = read4(in);
            int texWidth = read4(in);
            int texHeight = read4(in);
            byte[] data = new byte[texWidth * texHeight * 4];
            int tmp = 0;
            int inc = (texWidth - width) * 4;
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    for (int k = 0; k < 4; k++)
                        data[tmp++] = (byte) in.read();
                }
                tmp += inc;
            }
            // create the texture ID for this texture
            int textureID = createTextureID();
            Texture texture = new Texture(target, textureID);

            // bind this texture
            glBindTexture(target, textureID);

            texture.setWidth(width);
            texture.setHeight(height);

            glTexParameteri(target, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(target, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            ByteBuffer imageBuffer;

            texture.setTextureHeight(texHeight);
            texture.setTextureWidth(texWidth);

            imageBuffer = ByteBuffer.allocateDirect(data.length);
            imageBuffer.order(ByteOrder.nativeOrder());
            imageBuffer.put(data, 0, data.length);
            imageBuffer.flip();
            // produce a texture from the byte buffer
            glTexImage2D(target, 0, GL_RGBA, texWidth, texHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);
            long time = System.currentTimeMillis() - start;
            System.out.println("Loaded " + path + " in " + time + " ms");

            return texture;
        } catch (IOException exc) {
            System.err.println("Cannot load texture " + path);
            exc.printStackTrace();
        }
        return null;
    }

    private static int read4(InputStream i) throws IOException {
        int a = i.read();
        int b = i.read();
        int c = i.read();
        int d = i.read();
        return (a | (b << 8) | (c << 16) | (d << 24));
    }
}