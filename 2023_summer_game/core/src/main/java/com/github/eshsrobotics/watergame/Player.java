package com.github.eshsrobotics.watergame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * This object represents the player (the submarine navigating through the
 * ocean.)
 */
public class Player {

    /**
     * The texture region we'll be grabbing the player sprite from.
     */
    private TextureRegion region;

    /**
     * The player's image, along with position information.
     */
    private Sprite playerSprite;



    /**
     * Creates a new, valid instance of the Player class.
     *
     * @param region The region of the texture that represents the player's
     *               sprite (currently not animated.)
     */
    public Player(TextureRegion region) {

        // Copy the region argument into one of our member variables so other
        // methods have access to it.
        this.region = region;

        // We don't really know where the player will ultimately be on the map.
        // We do need to initialize it to SOME value, and this is some value.
        //
        // We are setting the sprite's position in world pixel coordinates.
        this.playerSprite = new Sprite(region);
        this.playerSprite.setPosition(0, 0);
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
        batch.draw(this.region, screenPosition.x, screenPosition.y);
    }

    /**
     * Returns the player sprite.  The sprite has additional properties, such as
     * boundaries, a scale factor, and coordinates, which can be used for
     * rendering and collision detection purposes.
     */
    public Sprite getSprite() {
        return this.playerSprite;
    }

    // int i; // <-- i is a variable of type int.
    // public class Point {
    // float x;
    // float y;
    // void translate(float dx, float dy) { x += dx; y += dy; }
    // };
    // // A += B -> A = A + B
    // // Great if you have just one point
    // float x = 10;
    // float y = 30;
    //
    // But I need more: I need 1000 points to represent the positions of bullets in
    // my game
    //
    // float x1, x2, x3, /*...*/, x999, x1000; // Foolish
    // float[] x = new float[1000], y = new float[1000]; // Slightly less foolish
    // (parallel arrays)
    // Point[] coordinates = new Point[1000]; // Smart: Boom! 1,000 x and y values.
    //
    // Point p = new Point();
    // p.x = 10; // x and y are fields of the Point class.
    // p.y = 30; // p is an instance of the Point class. (Use "new" to create
    // instances.)
    // p.translate(10, 10);
    // // p.x is now: 20
    // // p.y is now: 40

}
