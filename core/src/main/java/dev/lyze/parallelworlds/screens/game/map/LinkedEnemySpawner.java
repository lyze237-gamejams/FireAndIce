package dev.lyze.parallelworlds.screens.game.map;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.Map;
import dev.lyze.parallelworlds.screens.game.entities.enums.Direction;
import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.LinkedEnemy;
import dev.lyze.parallelworlds.screens.game.map.properties.LinkedEnemyKillPartProperties;
import dev.lyze.parallelworlds.screens.game.map.properties.LinkedEnemyMapProperties;
import dev.lyze.parallelworlds.screens.game.map.properties.MapProperties;
import dev.lyze.parallelworlds.utils.Point;
import lombok.SneakyThrows;

import java.util.HashMap;

public class LinkedEnemySpawner extends MapSpawner<LinkedEnemyMapProperties> {
    private static final Logger<LinkedEnemySpawner> logger = new Logger<>(LinkedEnemySpawner.class);

    public LinkedEnemySpawner(Level level, Map map) {
        super(level, map, LinkedEnemyMapProperties.class);
    }

    @SneakyThrows
    @Override
    public void spawnInternal(int x, int y, LinkedEnemyMapProperties properties, HashMap<Point, MapProperties> spawnedEntities) {
        logger.logInfo("Spawning linked enemy with direction " + properties.getDirection() + " at " + x + "/" + y);

        var partPoint = spawnedEntities.keySet().stream().filter(p -> p.getX() == x && properties.getDirection() == Direction.Up ? p.getY() > y : p.getY() < y && spawnedEntities.get(p).getClass().equals(LinkedEnemyKillPartProperties.class)).findFirst().orElse(null);
        if (partPoint == null) {
            logger.logError("Couldn't find appropriate linked enemy part for linked enemy.");
            return;
        }

        logger.logInfo("Found linked enemy part at " + partPoint.getX() + "/" + partPoint.getY());

        var constructor = ClassReflection.getDeclaredConstructor(properties.getEntity().getEntityClass(), float.class, float.class, Level.class, float.class, float.class, boolean.class);
        var linkedEnemy = (LinkedEnemy) constructor.newInstance(x, y, level, partPoint.getX(), partPoint.getY(), properties.getDirection() == Direction.Up);
        level.addEntity(linkedEnemy);
    }
}
