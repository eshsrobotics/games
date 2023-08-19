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
import com.badlogic.gdx.utils.Null;

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
     * A factor used to convert from world coordinates to world pixel
     * coordinates.  An object at (x, y) in world space is located at
     * (x/scaleFactor, y/scaleFactor) in world pixel space.
     */
    private float scaleFactor;

    /**
     * Creates a new, valid instance of the Player class.
     *
     * @param region The region of the texture that represents the player's
     *               sprite (currently not animated.)
     *
     * @param scaleFactor The scale factor from world units to pixels.  A value
     *                    of 1.0f/32, for instance, means that each world unit
     *                    is 32x32 pixels.
     */
    public Player(TextureRegion region, float scaleFactor) {

        // We don't really know where the player will ultimately be on the map.
        // We do need to initialize it to SOME value, and this is some value.
        //
        // We are setting the sprite's position in world pixel coordinates.
        this.playerSprite = new Sprite(region);
        this.playerSprite.setPosition(0, 0);
        this.scaleFactor = scaleFactor;
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

    /**
     * Detects whtether our player sprite has touched any MapObject in the given
     * MapLayer.
     * @return The ID of the colliding object, as a string. If the player is
     *         colliding with more than one such object simultaneously, we will
     *         return a value according to our collision priority.
     *
     *         <p>If we have not collided with anything, we return an empty 
     *         string.</p>
     * @apiNote Why are we returning a String here instead of a boolean?  There
     *          must have been a reason.
     */
    public String detectCollision(MapLayer objectLayer) {
        for (int i = 0; i < objectLayer.getObjects().getCount(); i++) {
            MapObject mapObject = objectLayer.getObjects().get(i);
            if (mapObject instanceof RectangleMapObject) {
                // Are we intersecting with this rectangle?
                boolean playerIntersectsWall = intersects((RectangleMapObject)mapObject, hitBox);
                if (playerIntersectsWall) {
                    return mapObject.getProperties().get("id").toString();
                } 
            } else {
                // Detecting other MapObjects goes here.
            }
        }
        return "";
    }

    /**
     * Returns true if the given bounding box intersects the given map object
     * and false otherwise.
     *
     * @param rect The map rectangle we are detecting a collision with.  The
     *             x, y, width, and height values here are all in world PIXEL
     *             coordinates (and so depend on the tilesize.)
     * @param box  The player's bounding box.  The values here are specified in
     *             world coordinates (in other words, in terms of tiles.)
     */
    private boolean intersects(RectangleMapObject rect, BoundingBox box) {            
        float wallX = rect.getRectangle().x;
        float wallY = rect.getRectangle().y;
        float wallWidth = rect.getRectangle().width;
        float wallHeight = rect.getRectangle().height;

        // Convert rect (which is in world pixel coordinates) to world coordinates.
        wallX *= scaleFactor;
        wallY *= scaleFactor;
        wallWidth *= scaleFactor;
        wallHeight *= scaleFactor;

        // Grab the 3D coordinates of the bounding box representing the player.
        // Note that the Z coordinates are effectively ignored.
        Vector3 bottomLeftCorner = new Vector3();
        box.getMin(bottomLeftCorner);
        Vector3 upperRightCorner = new Vector3();
        box.getMax(upperRightCorner);           

        float playerX = bottomLeftCorner.x;
        float playerY = bottomLeftCorner.y;
        float playerWidth = box.getWidth();
        float playerHeight = box.getHeight();

        if (rect.getProperties().get("id").toString() == "5") {
            System.out.printf("wall: (x=%.1f, y=%.1f, width=%.1f, height=%.1f), player: (x=%.1f, y=%.1f, width=%.1f, height=%.1f)\n",
                wallX, wallY, wallWidth, wallHeight,
                playerX, playerY, playerWidth, playerHeight);
        }
        

        if (playerX + playerWidth < wallX) {
            return false;
        }
        
        if (playerY + playerHeight < wallY) {
            return false;
        }

        if (playerX > wallX + wallWidth) {
            // Beyond right side.
            return false;
        }

        if (playerY > wallY + wallHeight) {
            // Beyond top side
            return false;
        }
        return true;
    }
}
