package org.gcreator.runero;

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

    public static boolean checkCollision(Instance i1, Instance i2) {
        GameSprite sp1, sp2;
        if (i1.mask_index >= 0)
            sp1 = i1.getMask();
        else
            sp1 = i1.getSprite();

        if (i2.mask_index >= 0)
            sp2 = i2.getMask();
        else
            sp2 = i2.getSprite();

        if (sp1 == null && sp2 == null)
            return isPointCollide(i1.x, i1.y, i2.x, i2.y);

        Rectangle r1 = (sp1 == null) ? null : new Rectangle(sp1.getBounds());
        Rectangle r2 = (sp2 == null) ? null : new Rectangle(sp2.getBounds());
        if (r1 != null)
            r1.translate((int) Math.round(i1.x - sp1.x), (int) Math.round(i1.y - sp1.y));
        if (r2 != null)
            r2.translate((int) Math.round(i2.x - sp2.x), (int) Math.round(i2.y - sp2.y));
        // pixel point
        if (sp1 == null)
            return r2.contains(i1.x, i1.y);
        else if (sp2 == null) {
            return r1.contains(i2.x, i2.y);
        }

        if (r1.intersects(r2)) {
            SubImage s1 = i1.getSubImage();
            SubImage s2 = i2.getSubImage();
            if (s1 == null || s2 == null)
                return true;

            if (s1.mask == null && s2.mask == null)
                return true;
            return isPixelCollide(i1.x - sp1.x, i1.y - sp1.y, s1.mask, new Rectangle(sp1.getBounds()),
                    i2.x - sp2.x, i2.y - sp2.y, s2.mask, new Rectangle(sp2.getBounds()));

        }
        return false;

    }

    private static boolean isPixelCollide(double x1, double y1, Mask mask1, Rectangle r1, double x2, double y2,
            Mask mask2, Rectangle r2) {
        
        // initialisation
        double width1 = x1 + r1.width - 1;
        double height1 = y1 + r1.height - 1;
        double width2 = x2 + r2.width - 1;
        double height2 = y2 + r2.height - 1;

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

                if (mask1 == null) {
                    if (mask2.map[nx1][ny1] && r1.contains(nx, ny))
                        return true;
                } else if (mask2 == null) {
                    if (mask1.map[nx][ny] && r2.contains(nx, ny))
                        return true;
                } else if (mask1.map[nx][ny] && mask2.map[nx1][ny1]) {
                    // collide!!
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isPointCollide(double x1, double y1, double x2, double y2) {
        return Math.round(x1) == Math.round(x2) && Math.round(y1) == Math.round(y2);
    }
}
