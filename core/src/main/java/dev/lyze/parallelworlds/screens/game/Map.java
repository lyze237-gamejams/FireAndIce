package dev.lyze.parallelworlds.screens.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.entities.impl.GroundTile;
import dev.lyze.parallelworlds.screens.game.map.MapEntitiesCreation;
import dev.lyze.parallelworlds.statics.Statics;
import dev.lyze.parallelworlds.utils.OrthogonalTiledMapRendererBleeding;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Map {
    private static final Logger<Map> logger = new Logger<>(Map.class);

    private final GameScreen game;
    @Getter
    private final TiledMap map;

    private final OrthogonalTiledMapRenderer renderer;

    @Getter
    private int mapWidth, mapHeight;
    @Getter
    private int tileWidth, tileHeight;
    @Getter
    private int mapPixelWidth, mapPixelHeight;
    @Getter
    private Rectangle boundaries;
    @Getter
    private Music music;

    @Getter
    private Color topColor, bottomColor;

    public Map(GameScreen game, TiledMap map) {
        this.game = game;
        this.map = map;

        renderer = new OrthogonalTiledMapRendererBleeding(map, 1 / 8f);
    }

    public void initialize() {
        new MapEntitiesCreation(game.getLevel(), this).initialize();

        setupFields();
        setupLayers();
        setupCollisions();
        setupBoundaries();
    }

    private void setupBoundaries() {
        var boundariesLayer = map.getLayers().get("Boundaries");
        var objects = boundariesLayer.getObjects();

        if (objects.getCount() > 1) {
            logger.logInfo("Boundaries has multiple collision objects attached. Taking first only.");
        }
        else if (objects.getCount() == 0) {
            logger.logError("Boundaries has no collision objects attached.");
            return;
        }

        boundaries = new Rectangle(((RectangleMapObject) objects.get(0)).getRectangle());
        boundaries.setPosition(boundaries.getX() / tileWidth, boundaries.getY() / tileHeight);
        boundaries.setSize(boundaries.getWidth() / tileWidth, boundaries.getHeight() / tileHeight);
    }

    private void setupLayers() {
        var middleLine = map.getLayers().get("Middle Line");
        if (middleLine != null)
            middleLine.setVisible(false);
    }

    private void setupCollisions() {
        var world = game.getLevel().getWorld();

        for (MapLayer l : map.getLayers()) {
            if (!(l instanceof TiledMapTileLayer)) {
                logger.logInfo("Skipping layer " + l.getName() + " since it is not a tile layer.");
                continue;
            }

            if (l.getName().toLowerCase().contains("gradient")) {
                logger.logInfo("Skipping layer " + l.getName() + " since it is a gradient layer.");
                continue;
            }

            if (!l.isVisible()) {
                logger.logInfo("Skipping layer " + l.getName() + " since it is hidden.");
                continue;
            }


            var layer = (TiledMapTileLayer) l;

            for (int y = 0; y < layer.getHeight(); y++) {
                for (int x = 0; x < layer.getWidth(); x++) {
                    var cell = layer.getCell(x, y);

                    if (cell == null)
                        continue;

                    Rectangle rectangle = getCellCollider(cell);
                    if (rectangle == null)
                        continue;

                    var block = new GroundTile(x + rectangle.x, y + rectangle.y, rectangle.width / tileWidth, rectangle.height / tileHeight, game.getLevel());
                    game.getLevel().addEntity(block);
                }
            }
        }
    }

    private Rectangle getCellCollider(TiledMapTileLayer.Cell cell) {
        var objects = cell.getTile().getObjects();
        if (objects.getCount() > 1) {
            logger.logInfo("Cell has multiple collision objects attached. Taking first only.");
        }

        if (objects.getCount() == 0)
            return null;

        var object = objects.get(0);

        if (!(object instanceof RectangleMapObject)) {
            logger.logInfo("Cell's collider map object is not a rectangle");
            return null;
        }

        return ((RectangleMapObject) object).getRectangle();
    }

    private void setupFields() {
        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);

        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tileWidth;
        mapPixelHeight = mapHeight * tileHeight;

        topColor = map.getProperties().get("topColor", Color.class);
        bottomColor = map.getProperties().get("bottomColor", Color.class);

        var musicPath= map.getProperties().get("music", String.class);
        if (musicPath == null)
            music = Statics.assets.getMusic().getBooster();
        else
            music = Statics.assets.getMusic().get(musicPath);
    }

    public void render(OrthographicCamera cam) {
        renderer.setView(cam);
        renderer.render();
    }

    public void debugRender(ShapeDrawer drawer) {
        drawer.rectangle(boundaries);
    }
}
