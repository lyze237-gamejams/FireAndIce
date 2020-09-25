package dev.lyze.parallelworlds.screens.game.map;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.Map;
import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.LinkedEnemy;
import dev.lyze.parallelworlds.screens.game.map.properties.LinkedEnemyMapProperties;
import dev.lyze.parallelworlds.screens.game.map.properties.MapProperties;
import dev.lyze.parallelworlds.utils.Point;

import java.util.HashMap;

public class LinkedEnemySpawner extends MapSpawner<LinkedEnemyMapProperties> {
    private static final Logger<LinkedEnemySpawner> logger = new Logger<>(LinkedEnemySpawner.class);

    public LinkedEnemySpawner(Level level, Map map) {
        super(level, map, LinkedEnemyMapProperties.class);
    }

    @Override
    public void spawnInternal(int x, int y, LinkedEnemyMapProperties properties, HashMap<Point, MapProperties> spawnedEntities) {
        logger.logInfo("Spawning linked enemy with inverted world " + properties.isInvertedWorld() + " at " + x + "/" + y);

        try {
            Constructor constructor = ClassReflection.getDeclaredConstructor(properties.getEntity().getEntityClass(), float.class, float.class, Level.class, int.class, boolean.class);
            var linkedEnemy = (LinkedEnemy) constructor.newInstance(x, y, level, properties.getHeight(), properties.isInvertedWorld());
            level.addEntity(linkedEnemy);
        } catch (ReflectionException e) {
            e.printStackTrace();
        }
    }
}
