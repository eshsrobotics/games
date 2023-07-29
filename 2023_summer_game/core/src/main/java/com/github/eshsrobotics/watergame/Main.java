package com.github.eshsrobotics.watergame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.Input.Keys;
import java.util.ArrayList;
import java.util.Arrays;
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

    /***
     * Generates a map using an array.
     *
     * @return Returns a working map that has something like a pedestal in it,
     * or something.  There are no MapObject layers and only one tile layer.
     */
    private TiledMap generateTiledMapOldFashioned() {

        map = new TiledMap();

        mapLayout = Arrays.asList(
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,
            1, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
        );
        mapHeight = mapLayout.size() / mapWidth;
        x = 0;
        y = 0;

        TiledMapTileLayer layer = new TiledMapTileLayer(mapWidth, mapHeight, 32, 32);
        for (int i = 0; i < mapLayout.size(); i++) {

            TextureRegion region = null;
            int value = mapLayout.get(i);
            int row, column;

            switch(value) {
                case 0:
                    // Upper left corner texture is the "emptiest", I guess.
                    row = column = 0;
                    break;
                case 1:
                    // Wall texture
                    row = column = 1;
                    break;
                case 2:
                    // Pedestal texture?
                    row = 2;
                    column = 0;
                    break;
                case 3:
                    // Thing at the top of the pedestal
                    row = column = 6;
                    break;
                default:
                    // Something ridiculous, to test for bugs
                    row = 2;
                    column = 6;
                    break;
            }
            region = new TextureRegion(tileSet, row * 32, column * 32, 32, 32);
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(region));
            int x = i % mapWidth;
            int y = i / mapWidth;
            layer.setCell(x, mapHeight - y + 1, cell);
        }
        layer.setName("layer");
        map.getLayers().add(layer);
        return map;
    }

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
                                         TILE_HEIGHT));

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
