package com.github.eshsrobotics.watergame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.Input.Keys;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture sub;

    TiledMap myMap;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;

    // This is the list of textures that the game world can choose from.
    private Map<Character, Texture> textures;

    /**
     * Code to execute when the game starts.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        sub = new Texture("submarine.png");

        // Populate the list of possible textures (so that the world indices
        // have meaning.)

        textures = new HashMap<>();
        textures.put('p', new Texture("Basic_Ground_CornerL_Pixel.png"));
        textures.put('q', new Texture("Basic_Ground_CornerR_Pixel - Copy.png"));
        textures.put('*', new Texture("Basic_Ground_Filler_Pixel.png"));
        textures.put('o', new Texture("Basic_Ground_Top_Pixel.png"));

        final String world =
        "pooooooq    pooooq\n" +
        "  ******q    *****\n" +
        "   ******q     ***\n" +
        "poq  *****q   p***\n" +
        "***********ooo****\n" +
        "******************";

        myMap = new TiledMap();
        TiledMapTileLayer levelTiles = createTileLayer(world, 32, 32);
        levelTiles.setName("level");
        MapLayers layers = myMap.getLayers();
        layers.add(levelTiles);

        // 32 pixels (the size of a single tile) corresponds to one world unit
        final float unitScale = 1.0f / 32;

        renderer = new OrthogonalTiledMapRenderer(myMap, unitScale);
        camera = new OrthographicCamera();

        final float viewportWidth = 5, viewportHeight = 3;
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        renderer.setView(camera);
    }

    /**
     * Converts a string into a {@link TiledMapTileLayer tile layer}.
     *
     * <p>We do this by iterating over the string line by line (we look for newline
     * characters in order to find the end of each line.)  To the extent that
     * the lines are different lengths, all lines are right-padded with spaces
     * until they have the length of the longest line.</p>
     *
     * <p>After that. we convert each character to its tile equivalent using the
     * {@link #textures} map as a guide.  Invalid characters are mapped to blank
     * tiles.</p>
     *
     * @param mapDefinition    The string that we will convert into a map.  It
     *                         should normally have newlines in it, or the map
     *                         will all be on the same row (not fun to play, I
     *                         assure you.)
     * @param tileWidthPixels  Width of each tile in pixels.
     * @param tileHeightPixels Height of each tile in pixels.
     * @return Returns the equivalent tile layer.
     */
    private TiledMapTileLayer createTileLayer(String mapDefinition, int tileWidthPixels, int tileHeightPixels) {

        final List<String> lines = Arrays.asList(mapDefinition.split("\n"));
        String longestLine = lines.stream()
            .max((line1, line2) -> line1.length() - line2.length())
            .get(); // Will throw if mapDefinition is empty

        // Right-pad all lines to the same length.
        lines.replaceAll(line -> {
            // return line + " ".repeat(longestLine.length() - line.length());
            StringBuilder builder = new StringBuilder(line);
            for (int i = 0; i < longestLine.length() - line.length(); ++i) {
                builder.append(" ");
            }
            return builder.toString();
        });

        final int rows = lines.size();
        final int columns = longestLine.length();
        TiledMapTileLayer layer = new TiledMapTileLayer(columns, rows, tileWidthPixels, tileHeightPixels);

        // Add cells to the layer according to the characters in the string.
        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                final Character key = lines.get(row).charAt(column);
                Texture texture = textures.getOrDefault(key, null);
                if (texture != null) {
                    TextureRegion region = new TextureRegion(texture);
                    StaticTiledMapTile tile = new StaticTiledMapTile(region);
                    Cell cell = new Cell();
                    cell.setTile(tile);
                    layer.setCell(column, (rows - 1) - row, cell);
                }
            }
        }
        return layer;
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            camera.position.x -= .05;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            camera.position.x += .05;
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            camera.position.y += .05;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            camera.position.y -= .05;
        }

        camera.update();
        renderer.setView(camera);
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // renderSub(x, y);

        renderer.render();
    }

    private void renderSub(float x, float y) {
        batch.begin();
        batch.draw(image, 140, 210);
        batch.draw(sub, x, y);
        batch.end();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        myMap.dispose();
        batch.dispose();
        image.dispose();
    }
}
