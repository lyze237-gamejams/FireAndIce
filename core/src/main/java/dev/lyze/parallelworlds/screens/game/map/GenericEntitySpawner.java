package dev.lyze.parallelworlds.screens.game.map;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.Map;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.map.properties.GenericEntitySpawnerMapProperties;
import dev.lyze.parallelworlds.screens.game.map.properties.MapProperties;
import dev.lyze.parallelworlds.utils.Point;
import lombok.SneakyThrows;

import java.util.HashMap;

public class GenericEntitySpawner extends MapSpawner<GenericEntitySpawnerMapProperties> {
    private static final Logger<GenericEntitySpawner> logger = new Logger<>(GenericEntitySpawner.class);

    public GenericEntitySpawner(Level level, Map map) {
        super(level, map, GenericEntitySpawnerMapProperties.class);
    }

    @SneakyThrows
    @Override
    public void spawnInternal(int x, int y, GenericEntitySpawnerMapProperties properties, HashMap<Point, MapProperties> spawnedEntities) {
        logger.logInfo("Spawning entity " + properties.getEntity() + " at " + x + "/" + y);

        var constructor = ClassReflection.getDeclaredConstructor(properties.getEntity().getEntityClass(), float.class, float.class, Level.class, boolean.class);
        var instance = constructor.newInstance(x, y, level, properties.isInvertedGravity());
        level.addEntity((Entity) instance);
    }
}
