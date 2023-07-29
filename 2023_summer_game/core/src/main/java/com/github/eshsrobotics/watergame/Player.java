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
     * The Sprite that keeps track of the player's position and orientation
     * for clipping, collision, and display purposes.
     */
    private Sprite playerSprite;

    public Sprite getSprite() {
        return playerSprite;
    }

    /**
     * Creates a new, valid instance of the Player class.
     *
     * @param region The region of the texture that represents the player's
     *               sprite (currently not animated.)
     */
    public Player(TextureRegion region) {

        // Copy the region argument into one of our member variables so other
        // methods have access to it.
        this.playerSprite = new Sprite(region);

        // We don't really know where the player will ultimately be on the map.
        // We do need to initialize it to SOME value, and this is some value.
        this.playerSprite.setPosition(0, 0);
    }

    /**
     * Renders the player as part of an existing batch of commands/textures that
     * are already heading to your graphics card.
     *
     * @param batch      The batch that is currently being (or is about to be)
     *                   drawn on-screen.  This will nominally be the batch used
     *                   by your renderer.
     * @param viewBounds The rectangle where all the on-screen drawing is taking
     *                   place.
     */
    public void render(Batch batch, OrthographicCamera camera) {
        Vector3 worldPosition = new Vector3(this.playerSprite.getX(), this.playerSprite.getY(), 0);
        Vector3 screenPosition = camera.project(worldPosition);
        batch.draw(this.playerSprite, screenPosition.x, screenPosition.y);
    }
}
