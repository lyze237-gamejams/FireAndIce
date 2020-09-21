package dev.lyze.parallelworlds.screens.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.entities.Block;
import dev.lyze.parallelworlds.utils.OrthogonalTiledMapRendererBleeding;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

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
    @Getter
    private Rectangle boundaries;

    public Map(GameScreen game, TiledMap map) {
        this.game = game;
        this.map = map;

        renderer = new OrthogonalTiledMapRendererBleeding(map, 1 / 8f);
    }

    public void initialize() {
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

        boundaries = ((RectangleMapObject) objects.get(0)).getRectangle();
        boundaries.setPosition(boundaries.getX() / tileWidth, boundaries.getY() / tileHeight);
        boundaries.setSize(boundaries.getWidth() / tileWidth, boundaries.getHeight() / tileHeight);
    }

    private void setupLayers() {
        var middleLine = map.getLayers().get("Middle Line");
        if (middleLine != null)
            middleLine.setVisible(false);

        var entities = map.getLayers().get("Entities");
        if (entities != null) {
            entities.setVisible(false);
            setupEntities((TiledMapTileLayer) entities);
        }
    }

    private void setupEntities(TiledMapTileLayer entities) {
        for (int y = 0; y < entities.getHeight(); y++) {
            for (int x = 0; x < entities.getWidth(); x++) {
                var cell = entities.getCell(x, y);

                if (cell == null)
                    continue;

                var tile = cell.getTile();
                var properties = tile.getProperties();

                switch (properties.get("type", String.class)) {
                    case "Spawn":
                        game.getLevel().spawnPlayer(properties.get("player", String.class), x, y);
                        break;
                    case "Portal":
                        game.getLevel().spawnPortal(properties.get("color", String.class), x, y);
                        break;
                    case "PortalDirection":
                        game.getLevel().spawnPortalDirection(properties.get("direction", String.class), x, y);
                        break;
                }
            }
        }
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

                    //var block = new Block(x * tileWidth + rectangle.x, y * tileHeight + rectangle.y, rectangle.width, rectangle.height, game.getLevel());
                    var block = new Block(x + rectangle.x, y + rectangle.y, rectangle.width / tileWidth, rectangle.height / tileHeight, game.getLevel());
                    block.addToWorld(world);
                    game.getLevel().getEntities().add(block);
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
    }

    public void render(OrthographicCamera cam) {
        renderer.setView(cam);
        renderer.render();
    }

    public void debugRender(ShapeDrawer drawer) {
        drawer.rectangle(boundaries);
    }
}
