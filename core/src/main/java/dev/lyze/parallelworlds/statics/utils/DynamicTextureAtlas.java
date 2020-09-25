package dev.lyze.parallelworlds.statics.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.reflect.Annotation;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import dev.lyze.parallelworlds.logger.Logger;

public abstract class DynamicTextureAtlas {
    private static final Logger<DynamicTextureAtlas> logger = new Logger<>(DynamicTextureAtlas.class);
    private final TextureAtlas atlas;

    public DynamicTextureAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public void load() {
        for (Field field : ClassReflection.getDeclaredFields(getClass())) {
            Annotation annotation = field.getDeclaredAnnotation(LoadFromTextureAtlas.class);
            if (annotation == null)
                continue;

            var path = annotation.getAnnotation(LoadFromTextureAtlas.class);

            boolean flipX = false, flipY = false;
            var flipAnnotation = field.getDeclaredAnnotation(Flip.class);
            if (flipAnnotation != null) {
                var flip = flipAnnotation.getAnnotation(Flip.class);

                flipX = flip.flipX();
                flipY = flip.flipY();
            }

            if (field.getType() == TextureAtlas.AtlasRegion.class) {
                logger.logInfo("Loading region: " + path.value());
                field.setAccessible(true);
                try {
                    var region = atlas.findRegion(path.value());
                    region.flip(flipX, flipY);
                    field.set(this, region);
                } catch (ReflectionException e) {
                    logger.logError("Couldn't assign region " + field.getName() + ": " + path.value(), e);
                }
            } else {
                logger.logInfo("Loading regions: " + path.value());
                field.setAccessible(true);
                try {
                    var regions = atlas.findRegions(path.value());
                    for (TextureAtlas.AtlasRegion r : regions)
                        r.flip(flipX, flipY);
                    field.set(this, regions);
                } catch (ReflectionException e) {
                    logger.logError("Couldn't assign region " + field.getName() + ": " + path.value(), e);
                }
            }
        }
    }
}
