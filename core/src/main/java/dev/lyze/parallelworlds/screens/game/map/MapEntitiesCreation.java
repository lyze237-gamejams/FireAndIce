package dev.lyze.parallelworlds.screens.game.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.Map;
import dev.lyze.parallelworlds.screens.game.map.properties.MapProperties;
import dev.lyze.parallelworlds.utils.Point;

import java.util.ArrayList;
import java.util.HashMap;

public class MapEntitiesCreation {
    private static final Logger<MapEntitiesCreation> logger = new Logger<>(MapEntitiesCreation.class);

    private final Level level;
    private final Map map;

    private final ArrayList<MapSpawner<?>> mapSpawners = new ArrayList<>();

    private final HashMap<Point, MapSpawner<?>> entitiesCoordinates = new HashMap<>();
    private final HashMap<Point, MapProperties> spawnedEntities = new HashMap<>();

    public MapEntitiesCreation(Level level, Map map) {
        this.level = level;
        this.map = map;

        mapSpawners.add(new PlayerSpawner(level, map));
        mapSpawners.add(new PortalSpawner(level, map));
        mapSpawners.add(new PortalDirectionSpawner(level, map));
        mapSpawners.add(new LinkedEnemySpawner(level, map));
        mapSpawners.add(new GenericEntitySpawner(level, map));
    }

    public void initialize() {
        var entitiesLayer = (TiledMapTileLayer) map.getMap().getLayers().get("Entities");
        if (entitiesLayer == null) {
            logger.logError("Entities layer is null");
            throw new IllegalArgumentException();
        }
        entitiesLayer.setVisible(false);

        for (int y = 0; y < entitiesLayer.getHeight(); y++) {
            for (int x = 0; x < entitiesLayer.getWidth(); x++) {
                var cell = entitiesLayer.getCell(x, y);

                if (cell == null)
                    continue;

                var properties = cell.getTile().getProperties();
                var type = properties.get("type", String.class);
                if (type == null)
                    continue;

                var spawner = mapSpawners.stream().filter(t -> t.getClass().getSimpleName().equals(type)).findFirst().orElse(null);
                if (spawner == null) {
                    logger.logError("Couldn't find appropriate spawner " + type + " for properties of cell " + x + "/" + y);
                    throw new NullPointerException();
                }

                entitiesCoordinates.put(new Point(x, y), spawner);
            }
        }

        for (int cnt = 0; spawnedEntities.size() < entitiesCoordinates.size(); cnt++) {
            for (Point coord : entitiesCoordinates.keySet()) {
                var cell = entitiesLayer.getCell(coord.getX(), coord.getY());
                if (cell.getTile().getProperties().get("step", Integer.class) == cnt) {
                    var spawnedProperties = spawn(coord.getX(), coord.getY(), entitiesCoordinates.get(coord), cell);
                    spawnedEntities.put(coord, spawnedProperties);
                }
            }
        }
    }

    public MapProperties spawn(int x, int y, MapSpawner<?> spawner, TiledMapTileLayer.Cell cell) {
        var tile = cell.getTile();

        try {
            var instance = ClassReflection.newInstance(spawner.getPropertiesClass());
            for (Field field : ClassReflection.getDeclaredFields(spawner.getPropertiesClass())) {
                initializeMapProperties(tile, instance, field);
            }

            spawner.spawn(x, y, instance, spawnedEntities);

            return instance;
        } catch (ReflectionException e) {
            logger.logError("Couldn't create " + spawner.getPropertiesClass().getSimpleName(), e);
            throw new IllegalArgumentException();
        }
    }

    private void initializeMapProperties(TiledMapTile tile, MapProperties instance, Field field) throws ReflectionException {
        field.setAccessible(true);

        var value = tile.getProperties().get(field.getName());
        if (field.getType().isEnum()) {
            if (value.toString().equals("null"))
                value = null;
            else
                value = Enum.valueOf((Class<Enum>) field.getType(), value.toString());
        }

        field.set(instance, value);
    }
}
