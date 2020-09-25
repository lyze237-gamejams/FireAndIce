package dev.lyze.parallelworlds.screens.game.map;

import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.Map;
import dev.lyze.parallelworlds.screens.game.entities.impl.PortalTile;
import dev.lyze.parallelworlds.screens.game.map.properties.MapProperties;
import dev.lyze.parallelworlds.screens.game.map.properties.PortalSpawnerMapProperties;
import dev.lyze.parallelworlds.utils.Point;

import java.util.HashMap;

public class PortalSpawner extends MapSpawner<PortalSpawnerMapProperties> {
    private static final Logger<PortalSpawner> logger = new Logger<>(PortalSpawner.class);

    public PortalSpawner(Level level, Map map) {
        super(level, map, PortalSpawnerMapProperties.class);
    }

    @Override
    public void spawnInternal(int x, int y, PortalSpawnerMapProperties properties, HashMap<Point, MapProperties> spawnedEntities) {
        logger.logInfo("Spawning portal " + properties.getColor() + " at " + x + "/" + y);

        var portal = new PortalTile(x, y, level, properties.getColor());
        level.addEntity(portal);
    }
}
