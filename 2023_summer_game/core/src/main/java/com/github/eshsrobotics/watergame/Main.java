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
     * Scale factor makes a 32x32 tile into 1 world unit
     */
    public static final float WORLD_SCALE = 1.0f/32;

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

        map = getTMXMap ();
        renderer = new OrthogonalTiledMapRenderer(map, 1.0f / 32);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 12, 12);
        renderer.setView(camera);

    }

    @Override
    public void render() {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            p.getSprite().setX(p.getSprite().getX() - 0.2f);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            p.getSprite().setX(p.getSprite().getX() + 0.2f);
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
           p.getSprite().setY(p.getSprite().getY() + 0.2f);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            p.getSprite().setY(p.getSprite().getY() - 0.2f);
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
