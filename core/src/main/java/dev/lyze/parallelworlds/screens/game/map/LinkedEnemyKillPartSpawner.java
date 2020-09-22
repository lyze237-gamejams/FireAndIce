package dev.lyze.parallelworlds.screens.game.map;

import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.Map;
import dev.lyze.parallelworlds.screens.game.map.properties.LinkedEnemyKillPartProperties;
import dev.lyze.parallelworlds.screens.game.map.properties.MapProperties;
import dev.lyze.parallelworlds.utils.Point;

import java.util.HashMap;

public class LinkedEnemyKillPartSpawner extends MapSpawner<LinkedEnemyKillPartProperties> {
    private static final Logger<LinkedEnemyKillPartSpawner> logger = new Logger<>(LinkedEnemyKillPartSpawner.class);
    public LinkedEnemyKillPartSpawner(Level level, Map map) {
        super(level, map, LinkedEnemyKillPartProperties.class);
    }

    @Override
    public void spawnInternal(int x, int y, LinkedEnemyKillPartProperties data, HashMap<Point, MapProperties> spawnedEntities) {
        logger.logInfo("Spawning linked enemy part at " + x + "/" + y);
    }
}
