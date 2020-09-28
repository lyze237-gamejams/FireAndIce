package dev.lyze.parallelworlds.screens.game.map;

import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.Map;
import dev.lyze.parallelworlds.screens.game.entities.impl.PortalDirectionTile;
import dev.lyze.parallelworlds.screens.game.map.properties.MapProperties;
import dev.lyze.parallelworlds.screens.game.map.properties.PortalDirectionMapProperties;
import dev.lyze.parallelworlds.utils.Point;

import java.util.HashMap;

public class PortalDirectionSpawner extends MapSpawner<PortalDirectionMapProperties> {
    private static final Logger<PortalDirectionSpawner> logger = new Logger<>(PortalDirectionSpawner.class);

    public PortalDirectionSpawner(Level level, Map map) {
        super(level, map, PortalDirectionMapProperties.class);
    }

    @Override
    public void spawnInternal(int x, int y, PortalDirectionMapProperties data, HashMap<Point, MapProperties> spawnedEntities) {
        logger.logInfo("Spawning portal direction " + data.getDirection() + " at " + x + "/" + y);

        var portal = new PortalDirectionTile(x, y, level, data.getDirection());
        level.addStaticEntity(portal);
    }
}
