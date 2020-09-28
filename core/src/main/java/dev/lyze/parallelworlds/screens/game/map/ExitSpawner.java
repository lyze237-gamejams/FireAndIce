package dev.lyze.parallelworlds.screens.game.map;

import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.Map;
import dev.lyze.parallelworlds.screens.game.entities.impl.ExitTile;
import dev.lyze.parallelworlds.screens.game.map.properties.ExitSpawnerMapProperties;
import dev.lyze.parallelworlds.screens.game.map.properties.MapProperties;
import dev.lyze.parallelworlds.utils.Point;

import java.util.HashMap;

public class ExitSpawner extends MapSpawner<ExitSpawnerMapProperties> {
    private static final Logger<ExitSpawner> logger = new Logger<>(ExitSpawner.class);

    public ExitSpawner(Level level, Map map) {
        super(level, map, ExitSpawnerMapProperties.class);
    }

    @Override
    public void spawnInternal(int x, int y, ExitSpawnerMapProperties data, HashMap<Point, MapProperties> spawnedEntities) {
        logger.logInfo("Spawning exit at " + x + "/" + y + " with level " + data.getLevel());

        level.addStaticEntity(new ExitTile(x, y, level, data.getLevel()));
    }
}
