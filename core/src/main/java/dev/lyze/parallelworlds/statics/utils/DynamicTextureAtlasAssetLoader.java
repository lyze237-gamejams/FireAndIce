package dev.lyze.parallelworlds.statics.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class DynamicTextureAtlasAssetLoader<TData extends DynamicTextureAtlas> extends SynchronousAssetLoader<TData, DynamicTextureAtlasAssetLoader<TData>.TextureAtlasParameter> {
    private final Class<TData> dynamicClass;
    private FileHandle atlas;

    public DynamicTextureAtlasAssetLoader(FileHandleResolver resolver, Class<TData> dynamicClass) {
        super(resolver);

        this.dynamicClass = dynamicClass;
    }

    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, TextureAtlasParameter parameter) {
        this.atlas = file;

        Array<AssetDescriptor> assetDescriptors = new Array<>();
        assetDescriptors.add(new AssetDescriptor(file, TextureAtlas.class));
        return assetDescriptors;
    }

    public TData load(AssetManager assetManager, String fileName, FileHandle file, TextureAtlasParameter parameter) {
        try {
            var dynamicTextureAtlas = (TData) ClassReflection.getConstructor(dynamicClass, TextureAtlas.class).newInstance(assetManager.get(atlas.path(), TextureAtlas.class));

            if (parameter == null || parameter.load)
                dynamicTextureAtlas.load();

            return dynamicTextureAtlas;
        } catch (ReflectionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public class TextureAtlasParameter extends AssetLoaderParameters<TData> {
        public boolean load;

        public TextureAtlasParameter (boolean load) {
            this.load = load;
        }
    }
}
