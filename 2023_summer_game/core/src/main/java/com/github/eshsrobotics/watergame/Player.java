package com.github.eshsrobotics.watergame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * This object represents the player (the submarine navigating through the
 * ocean.)
 */
public class Player {

    /**
     * The player's image, along with position information.
     */
    private Sprite playerSprite;

    /**
     * Hitbox is axis aligned, which means it is not easy to rotate
     */
    private BoundingBox hitBox;



    /**
     * Creates a new, valid instance of the Player class.
     *
     * @param region The region of the texture that represents the player's
     *               sprite (currently not animated.)
     *
     * @param scaleFactor The scale factor from world units to pixels
     */
    public Player(TextureRegion region, float scaleFactor) {

        // We don't really know where the player will ultimately be on the map.
        // We do need to initialize it to SOME value, and this is some value.
        //
        // We are setting the sprite's position in world pixel coordinates.
        this.playerSprite = new Sprite(region);
        this.playerSprite.setPosition(0, 0);
        float playerWidthWorld = playerSprite.getWidth() * scaleFactor;
        float playerHeightWorld = playerSprite.getHeight() * scaleFactor;
        Vector3 min = new Vector3(-playerWidthWorld/2, -playerHeightWorld/2, 0);
        Vector3 max = new Vector3(playerWidthWorld/2, playerHeightWorld/2, 0);
        hitBox = new BoundingBox(min, max);
    }

    /**
     * Renders the player as part of an existing batch of commands/textures that
     * are already heading to your graphics card.
     *
     * @param batch      The batch that is currently being (or is about to be)
     *                   drawn on-screen.  This will nominally be the batch used
     *                   by your renderer.
     *
     * @param camera     The camera used to convert world coordinates to screen
     *                   coordinates
     */
    public void render(Batch batch, OrthographicCamera camera) {
        Vector3 worldPosition = new Vector3(this.playerSprite.getX(),
                                            this.playerSprite.getY(),
                                            0);
        Vector3 screenPosition = camera.project(worldPosition);
        batch.draw(playerSprite, screenPosition.x, screenPosition.y);
    }

    /**
     * Returns the player sprite.  The sprite has additional properties, such as
     * boundaries, a scale factor, and coordinates, which can be used for
     * rendering and collision detection purposes.
     */
    public Sprite getSprite() {
        return this.playerSprite;
    }

    public String detectCollision(MapLayer objectLayer) {
        for (int i = 0; i < objectLayer.getObjects().getCount(); i++) {
            MapObject mapObject = objectLayer.getObjects().get(i);
            if (mapObject instanceof RectangleMapObject) {
                // Are we intersecting with this rectangle?

            }
        }
    }

    private boolean intersects(RectangleMapObject rect, BoundingBox box) {
            boolean intersected = false;

            final float width = box.getWidth();
            final float height = box.getHeight();
            Vector3 center = new Vector3(); box.getCenter(center);

            // Test to see if any of the corners of the bounding box are
            // contained within the rect.
            for (Vector3 corner : new Vector3[] {
                new Vector3(center.x - width/2, center.y - width/2, 0),
                new Vector3(center.x + width/2, center.y - width/2, 0),
                new Vector3(center.x + width/2, center.y + width/2, 0),
                new Vector3(center.x - width/2, center.y + width/2, 0)
            }) {

            }
    }
}
