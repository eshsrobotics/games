package com.github.eshsrobotics.watergame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture sub;
    private int x;
    private int y;

    // This is the game world.
    private List<TileType> world_tiles;
    private final int world_rows = 5, world_columns = 5;
    private final int tile_width = 32, tile_height = 32; // In pixels.

    // Our position in the game world, in pixels.
    private int world_x, world_y;

    // This is the list of textures that the game world can choose from.
    private Map<TileType, Texture> textures;

    private enum TileType {
        // Only four of these came from OpenGameArt.
        FLOOR, CORNER_L, CORNER_R, WALL, BLANK
    }


    /**
     * Code to execute when the game starts.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        sub = new Texture("submarine.png");
        x = 0;
        y = 0;

        // Populate the list of possible textures (so that the world indices
        // have meaning.)
        textures = new HashMap<TileType, Texture>(4);
        textures.put(TileType.CORNER_L, new Texture("Basic_Ground_CornerL_Pixel.png"));
        textures.put(TileType.CORNER_R, new Texture("Basic_Ground_CornerR_Pixel - Copy.png"));
        textures.put(TileType.WALL, new Texture("Basic_Ground_Filler_Pixel.png"));
        textures.put(TileType.FLOOR, new Texture("Basic_Ground_Top_Pixel.png"));

        // Make a world.
        world_tiles = new ArrayList<TileType>(Arrays.asList(
            TileType.WALL,     TileType.BLANK, TileType.WALL,  TileType.WALL,     TileType.BLANK,
            TileType.WALL,     TileType.WALL,  TileType.WALL,  TileType.WALL,     TileType.WALL,
            TileType.WALL,     TileType.BLANK, TileType.BLANK, TileType.WALL,     TileType.BLANK,
            TileType.CORNER_L, TileType.FLOOR, TileType.FLOOR, TileType.CORNER_R, TileType.BLANK,
            TileType.BLANK,    TileType.BLANK, TileType.BLANK, TileType.BLANK,    TileType.BLANK
        ));
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            x = x - 5;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            x = x + 5;
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            y = y + 5;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            y = y - 5;
        }
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // renderSub(x, y);
        world_x = x;
        world_y = y;
        renderTileLayer(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Renders {@link #world_tiles the grid of tiles} as they would appear when
     * the camera is centered at ({@link #world_x}, {@link #world_y}}.  The
     * rendering is constrained to only appear on-screen at the rectangle
     * ({@code vx},{@code vy})&#x40;{@code vw}x{@code vh}.
     *
     * No zooming is done, no seamless tiling is attempted, and the world must
     * have a 1:1 size correspondence with the tile layer.  (Thus we don't
     * support parallax layers that scroll more slowly than the foreground.)
     */
    private void renderTileLayer(int vx, int vy, int vw, int vh) {
        batch.begin();
        batch.draw(image, 140, 210);
        batch.draw(sub, x, y);
        for (float current_world_y = world_y - vh/2, current_screen_y = vy;
             current_world_y <= world_y + vh/2;
             current_world_y += tile_height, current_screen_y += tile_height) {

            for (float current_world_x = world_x - vw/2, current_screen_x = vx;
                  current_world_x <= world_x + vw/2;
                  current_world_x += tile_width, current_screen_x += tile_width) {

                final float tile_column = (float)Math.floor(current_world_x / tile_width);
                final float tile_row = (float)Math.floor(current_world_y / tile_height);
                if (tile_row < 0 || tile_row >= world_rows ||
                    tile_column < 0 || tile_column >= world_columns) {
                    // Location out of bounds.
                    continue;
                }
                final int offset = (int)(world_columns * tile_row + tile_column);

                final Texture current_texture = textures.get(world_tiles.get(offset));
                if (current_texture == null) {
                    // No such texture (perhaps we don't have an image for it yet.)
                    // TODO: Add an ugly placeholder instead.
                    continue;
                }

                // We don't want to start drawing the current tile at (x, y);
                // that would be silly.  (The tiles would appear to slightly
                // follow the player's movement, since (x, y) is not their real
                // location.)
                //
                // Instead, we slightly reposition the current tile so as to
                // take the current world position _within_ its tile into
                // account.
                float x_adjust = Math.floorMod(world_x, tile_width);
                float y_adjust = Math.floorMod(world_y, tile_height);
                x_adjust = y_adjust = 0;
                batch.draw(current_texture, current_world_x + x_adjust, vy + current_world_y + y_adjust);
            }
        }
        batch.end();
    }

    private void renderSub(float x, float y) {
        batch.begin();
        batch.draw(image, 140, 210);
        batch.draw(sub, x, y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
