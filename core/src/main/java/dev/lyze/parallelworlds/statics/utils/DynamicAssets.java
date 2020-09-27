package dev.lyze.parallelworlds.statics.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.reflect.Annotation;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import dev.lyze.parallelworlds.logger.Logger;
import lombok.Getter;

import java.util.HashMap;

public abstract class DynamicAssets {
    private static final Logger<DynamicAssets> logger = new Logger<>(DynamicAssets.class);

    private final HashMap<Field, LoadAssetFromFile> dynamicAssetFields = new HashMap<>();

    @Getter
    private final AssetManager assMan;

    private boolean consumed;

    public DynamicAssets(AssetManager assMan) {
        this.assMan = assMan;
    }

    @SuppressWarnings("unchecked")
    public void load() {
        for (Field field : ClassReflection.getDeclaredFields(getClass())) {
            Annotation annotation = field.getDeclaredAnnotation(LoadAssetFromFile.class);
            if (annotation == null)
                continue;

            var path = annotation.getAnnotation(LoadAssetFromFile.class);

            dynamicAssetFields.put(field, path);

            logger.logInfo("Loading asset: " + path.value());
            assMan.load(path.value(), field.getType());
        }
    }

    public void consume() {
        if (consumed)
            return;

        if (!assMan.isFinished())
            throw new IllegalArgumentException("Asset manager isn't finished");

        for (Field field : dynamicAssetFields.keySet()) {
            var path = dynamicAssetFields.get(field);

            try {
                logger.logInfo("Consuming asset: " + field.getName() + " / " + path.value());
                field.setAccessible(true);
                field.set(this, assMan.get(path.value()));
            } catch (ReflectionException e) {
                logger.logError("Couldn't assign asset " + field.getName() + ": " + path.value(), e);
            }
        }

        for (Field field : ClassReflection.getDeclaredFields(getClass())) {
            Annotation annotation = field.getDeclaredAnnotation(LoadAssetFromTextureAtlas.class);
            if (annotation == null)
                continue;
            var path = annotation.getAnnotation(LoadAssetFromTextureAtlas.class);

            for (var potentialSkinField: dynamicAssetFields.keySet()) {
                var potentialSkinPath = dynamicAssetFields.get(potentialSkinField);
                if (!potentialSkinPath.value().equals(path.value()))
                    continue;

                try {
                    logger.logInfo("Loading atlas from skin: " + path.value());
                    Skin skin = (Skin) potentialSkinField.get(this);
                    field.setAccessible(true);
                    var dynamicAtlas = (DynamicTextureAtlas) ClassReflection.getConstructor(field.getType(), TextureAtlas.class).newInstance(skin.getAtlas());
                    dynamicAtlas.load();
                    field.set(this, dynamicAtlas);
                } catch (ReflectionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void finishAndConsume() {
        assMan.finishLoading();
        consume();
    }
}
