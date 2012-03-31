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
import java.awt.image.BufferedImage;

import org.gcreator.runero.inst.Instance;
import org.gcreator.runero.res.GameSprite;

/**
 * Borrowed/stolen from GTGE 
 *  
 */
public abstract class RuneroCollision {

    private static boolean isPixelCollide(Instance i1, Instance i2) {
        GameSprite sp1 = i1.getSprite();
        GameSprite sp2 = i2.getSprite();
        if (sp1 == null || sp2 == null)
            return false;

   //     BufferedImage image1 = sp1.getSubImage(i1.getImageIndex());
    //    BufferedImage image2 = sp1.getSubImage(i2.getImageIndex());
return false;
      //  return isPixelCollide(i1.x, i1.y, image1, i2.x, i2.y, image2);
    }
    //TODO: SPRITE BBOX
    private static boolean isPixelCollide(double x1, double y1, BufferedImage image1, double x2, double y2,
            BufferedImage image2) {
        // initialization
        double width1 = x1 + image1.getWidth() - 1, height1 = y1 + image1.getHeight() - 1, width2 = x2
                + image2.getWidth() - 1, height2 = y2 + image2.getHeight() - 1;

        int xstart = (int) Math.max(x1, x2), ystart = (int) Math.max(y1, y2), xend = (int) Math.min(width1, width2), yend = (int) Math
                .min(height1, height2);

        // intersection rect
        int toty = Math.abs(yend - ystart);
        int totx = Math.abs(xend - xstart);

        for (int y = 1; y < toty - 1; y++) {
            int ny = Math.abs(ystart - (int) y1) + y;
            int ny1 = Math.abs(ystart - (int) y2) + y;

            for (int x = 1; x < totx - 1; x++) {
                int nx = Math.abs(xstart - (int) x1) + x;
                int nx1 = Math.abs(xstart - (int) x2) + x;
                try {
                    if (((image1.getRGB(nx, ny) & 0xFF000000) != 0x00)
                            && ((image2.getRGB(nx1, ny1) & 0xFF000000) != 0x00)) {
                        // collide!!
                        return true;
                    }
                } catch (Exception e) {
                    // System.out.println("s1 = "+nx+","+ny+" - s2 =
                    // "+nx1+","+ny1);
                }
            }
        }

        return false;
    }

    /**
     * Returns the intersection rect of two rectangle.
     */
    private static Rectangle getIntersectionRect(double x1, double y1, int width1, int height1, double x2, double y2,
            int width2, int height2) {
        double x12 = x1 + width1, y12 = y1 + height1, x22 = x2 + width2, y22 = y2 + height2;

        if (x1 < x2) {
            x1 = x2;
        }
        if (y1 < y2) {
            y1 = y2;
        }
        if (x12 > x22) {
            x12 = x22;
        }
        if (y12 > y22) {
            y12 = y22;
        }
        x12 -= x1;
        y12 -= y1;
        // x12,y12 will never overflow (they will never be
        // larger than the smallest of the two source w,h)
        // they might underflow, though...

        return new Rectangle((int)x1, (int)y1, (int) x12, (int) y12);
    }

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
}
