package dev.lyze.parallelworlds.screens.game.map;

import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.Map;
import dev.lyze.parallelworlds.screens.game.map.properties.MapProperties;
import dev.lyze.parallelworlds.utils.Point;
import lombok.Getter;

import java.util.HashMap;

public abstract class MapSpawner<TProperties extends MapProperties> {
    protected final Level level;
    private final Map map;

    @Getter
    private final Class<TProperties> propertiesClass;

    public MapSpawner(Level level, Map map, Class<TProperties> propertiesClass) {
        this.level = level;
        this.map = map;
        this.propertiesClass = propertiesClass;
    }

    public void spawn(int x, int y, MapProperties data, HashMap<Point, MapProperties> spawnedEntities) {
        spawnInternal(x, y, (TProperties) data, spawnedEntities);
    }

    public abstract void spawnInternal(int x, int y, TProperties data, HashMap<Point, MapProperties> spawnedEntities);
}
