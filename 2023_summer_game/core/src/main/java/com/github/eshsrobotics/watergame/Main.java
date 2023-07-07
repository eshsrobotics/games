package com.github.eshsrobotics.watergame;

import java.util.ArrayList;

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
    private List<Integer> world_tiles;   
    private final int world_rows = 5, world_columns = 5;
    private final int tile_width = 32, tile_height = 32; // In pixels.

    // Our position in the game world, in pixels.
    private int world_x, world_y; 

    // This is the list of textures that the game world can choose from.
    private List<Texture> textures;

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
        textures = new ArrayList<Texture>(4);
        textures.put(TileType.CORNER_L, new Texture("Basic_Ground_CornerL_Pixel.png"));
        textures.put(TileType.CORNER_R, new Texture("Basic_Ground_CornerR_Pixel.png"));
        textures.put(TileType.WALL, new Texture("Basic_Ground_Filler_Pixel.png"));
        textures.put(TileType.FLOOR, new Texture("Basic_Ground_Top_Pixel.png"));

        // Make a world.
        world_tiles = List.of(TileType.WALL,     TileType.BLANK, TileType.WALL,  TileType.WALL,     TileType.BLANK,
                              TileType.WALL,     TileType.WALL,  TileType.WALL,  TileType.WALL,     TileType.WALL,    
                              TileType.WALL,     TileType.BLANK, TileType.BLANK, TileType.WALL,     TileType.BLANK,
                              TileType.CORNER_L, TileType.FLOOR, TileType.FLOOR, TileType.CORNER_R, TileType.BLANK,
                              TileType.BLANK,    TileType.BLANK, TileType.BLANK, TileType.BLANK,    TileType.BLANK); 
                            
                            
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
