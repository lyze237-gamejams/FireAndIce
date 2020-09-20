package dev.lyze.parallelworlds.screens.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.utils.OrthogonalTiledMapRendererBleeding;
import lombok.Getter;

public class Map {
    private static final Logger<Map> logger = new Logger<>(Map.class);

    private final GameScreen game;
    private final TiledMap map;

    private final OrthogonalTiledMapRenderer renderer;

    @Getter
    private int mapWidth, mapHeight;
    @Getter
    private int tileWidth, tileHeight;
    @Getter
    private int mapPixelWidth, mapPixelHeight;

    public Map(GameScreen game, TiledMap map) {
        this.game = game;
        this.map = map;

        renderer = new OrthogonalTiledMapRendererBleeding(map, 1);

        setupFields();
    }

    private void setupFields() {
        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);

        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tileWidth;
        mapPixelHeight = mapHeight * tileHeight;
    }

    public void render(OrthographicCamera cam) {
        renderer.setView(cam);
        renderer.render();
    }
}
