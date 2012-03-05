package org.gcreator.runero;

import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.collision.BasicCollisionGroup;
import com.golden.gamedev.object.sprite.VolatileSprite;

public class ProjectileEnemyCollision extends BasicCollisionGroup {

    RuneroGame owner;

    public ProjectileEnemyCollision(RuneroGame owner) {
        this.owner = owner; // set the game owner
                            // we use this for getting image and
                            // adding explosion to playfield
    }

    // when projectiles (in group a) collided with enemy (in group b)
    // what to do?
    public void collided(Sprite s1, Sprite s2) {
        // we kill/remove both sprite!
        s1.setActive(false); // the projectile is set to non-active
        s2.setActive(false); // the enemy is set to non-active

        // show explosion on the center of the exploded enemy
        // we use VolatileSprite -> sprite that animates once and vanishes
        // afterward
        BufferedImage[] images = owner.getImages("resources/explosion.png", 7, 1);
        VolatileSprite explosion = new VolatileSprite(images, s2.getX(), s2.getY());

        // directly add to playfield without using SpriteGroup
        // the sprite is added into a reserved extra sprite group in playfield
        // extra sprite group is used especially for animation effects in game
        owner.playfield.add(explosion);
    }

}