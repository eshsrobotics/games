package com.github.eshsrobotics.watergame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.Input.Keys;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Texture sub;
    private Texture tileSet;
    private int x;
    private int y;
    private int mapWidth = 14, mapHeight;
    private List<Integer> mapLayout;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Player p;

    /**
     * Instead of one world unit in the map corresponding to one pixel, scale
     * up so that an entire 32x32 tile is one world unit.
     *
     * <p>This is specifically meant to be passed in as the unitScale for the
     * {@link OrthogonalTiledMapRenderer renderer}, but we also use it in
     * other contexts:</p>
     * <ul>
     *   <li></li>
     * </ul>
     */
    private final float unitScale = 1.0f / 32;

    TiledMap getTMXMap() {
        TiledMap map = new TmxMapLoader().load("untitled.tmx");
        return map;
    }

    /**
     * Initializes our game by creating our map (from scratch, using an array.
     * I know.  Stop judging me.)
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        sub = new Texture("submarine.png");
        tileSet = new Texture("fantasy-tileset.png");

        // Skull sprite: column 2, row 10.
        final int PLAYER_SPRITE_COLUMN = 2;
        final int PLAYER_SPRITE_ROW = 10;
        final int TILE_WIDTH = 32;
        final int TILE_HEIGHT = 32;
        p = new Player(new TextureRegion(tileSet,
                                         PLAYER_SPRITE_COLUMN * TILE_WIDTH,
                                         PLAYER_SPRITE_ROW * TILE_HEIGHT,
                                         TILE_WIDTH,
                                         TILE_HEIGHT), WORLD_SCALE);
        p.getSprite().setPosition(15, 15);

        map = getTMXMap();
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);
        camera = new OrthographicCamera();

        // Thanks to the unitScale, one 32x32 tile has the width and height of
        // one world unit.  Thus all our constants that refer to "tiles" here
        // are really referring to world units.
        final float VIEWPORT_WIDTH_IN_TILES = 12;
        final float VIEWPORT_HEIGHT_IN_TILES = 12;

        camera.setToOrtho(false, VIEWPORT_WIDTH_IN_TILES, VIEWPORT_HEIGHT_IN_TILES);
        renderer.setView(camera);
    }

    @Override
    public void render() {
        final float MOVE_SPEED = 0.2f;
        float playerDestinationX = p.getSprite().getX();
        float playerDestinationY = p.getSprite().getY();
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            playerDestinationX = p.getSprite().getX() - MOVE_SPEED;
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            playerDestinationX = p.getSprite().getX() + MOVE_SPEED;
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
           playerDestinationY = p.getSprite().getY() + MOVE_SPEED;
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            playerDestinationY = p.getSprite().getY() - MOVE_SPEED;
        }

        String id = p.detectCollision(getTMXMap().getLayers().get("Walls"));
        if (id == "") {
            // Only move the player if their new position would not collide
            // with a wall.
            p.getSprite().setPosition(playerDestinationX, playerDestinationY);
        } else {
            // System.out.printf("id='%s'\n", id);
        }

        camera.position.x = p.getSprite().getX();
        camera.position.y = p.getSprite().getY();
        camera.update();
        renderer.setView(camera);

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        // Render the player on top of the tiles.
        batch.begin();
        p.render(batch, camera);
        batch.end();

        // batch.begin();
        // batch.draw(image, 140, 210);
        // batch.draw(sub, x, y);
        // batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
