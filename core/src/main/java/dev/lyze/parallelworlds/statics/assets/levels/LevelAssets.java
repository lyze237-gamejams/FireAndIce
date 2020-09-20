package dev.lyze.parallelworlds.statics.assets.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.statics.utils.DynamicAssets;

public abstract class LevelAssets extends DynamicAssets {
    private static final Logger<LevelAssets> logger = new Logger<>(LevelAssets.class);

    private TiledMap map;

    public LevelAssets(AssetManager assMan) {
        super(assMan);
    }

    public TiledMap getMap() {
        if (map == null) {
            for (Field field : ClassReflection.getDeclaredFields(getClass())) {
                if (field.getType() == TiledMap.class) {
                    try {
                        field.setAccessible(true);
                        map = (TiledMap) field.get(this);
                        return map;
                    } catch (ReflectionException e) {
                        logger.logError("Ahh reflections", e);
                        throw new RuntimeException(e);
                    }
                }
            }

            throw new NullPointerException("Couldn't find " + TiledMap.class.getName() + " field");
        }

        return map;
    }
}
