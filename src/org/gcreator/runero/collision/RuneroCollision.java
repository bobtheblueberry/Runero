package org.gcreator.runero.collision;

/*
 * Copyright (c) 2008 Golden T Studios.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.Rectangle;

import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.res.GameSprite;
import org.gcreator.runero.res.GameSprite.Mask;
import org.gcreator.runero.res.GameSprite.SubImage;

/**
 * Borrowed/stolen from GTGE 
 *  
 */
public abstract class RuneroCollision {

    public static boolean checkCollision(Instance i1, Instance i2, boolean pixelPerfectCollision) {
        GameSprite sp1, sp2;
        if (i1.mask_index >= 0)
            sp1 = i1.getMask();
        else
            sp1 = i1.getSprite();

        if (i2.mask_index >= 0)
            sp2 = i2.getMask();
        else
            sp2 = i2.getSprite();

        if (sp1 == null || sp2 == null)
            return false;

        Rectangle r1 = new Rectangle(sp1.getBounds());
        Rectangle r2 = new Rectangle(sp2.getBounds());

        r1.translate((int) Math.round(i1.x - sp1.x), (int) Math.round(i1.y - sp1.y));
        r2.translate((int) Math.round(i2.x - sp2.x), (int) Math.round(i2.y - sp2.y));

        if (r1.intersects(r2)) {
            if (pixelPerfectCollision)
                return isPixelCollide(i1, i2);
            return true;
        }

        return false;

    }

    private static boolean isPixelCollide(Instance i1, Instance i2) {
        GameSprite sp1 = i1.getSprite();
        GameSprite sp2 = i2.getSprite();
        if (sp1 == null || sp2 == null)
            return false;

        SubImage s1 = i1.getSubImage();
        SubImage s2 = i2.getSubImage();
        if (s1 == null || s2 == null)
            return false;

        return isPixelCollide(i1.x - sp1.x, i1.y - sp1.y, s1.mask, i2.x - sp2.x, i2.y - sp2.y, s2.mask);
    }

    private static boolean isPixelCollide(double x1, double y1, Mask mask1, double x2, double y2, Mask mask2) {
        if (mask1 == null || mask2 == null) {
            // TODO: Rectangle to pixel perfect
            return false;
        }
        // initialisation
        double width1 = x1 + mask1.getSprite().width - 1;
        double height1 = y1 + mask1.getSprite().height - 1;
        double width2 = x2 + mask2.getSprite().width - 1;
        double height2 = y2 + mask2.getSprite().height - 1;

        int xstart = (int) Math.max(x1, x2);
        int ystart = (int) Math.max(y1, y2);
        int xend = (int) Math.min(width1, width2);
        int yend = (int) Math.min(height1, height2);

        // intersection rect
        int toty = Math.abs(yend - ystart);
        int totx = Math.abs(xend - xstart);

        for (int y = 1; y < toty - 1; y++) {
            int ny = Math.abs(ystart - (int) y1) + y;
            int ny1 = Math.abs(ystart - (int) y2) + y;

            for (int x = 1; x < totx - 1; x++) {
                int nx = Math.abs(xstart - (int) x1) + x;
                int nx1 = Math.abs(xstart - (int) x2) + x;

                if (mask1.map[nx][ny] && mask2.map[nx1][ny1]) {
                    // collide!!
                    return true;
                }
            }
        }

        return false;
    }
}
