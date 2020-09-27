package dev.lyze.parallelworlds.statics.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class InternalOrExternalFileHandleResolver implements FileHandleResolver {
    @Override
    public FileHandle resolve (String fileName) {
        if (Gdx.app.getType() == Application.ApplicationType.WebGL)
            return Gdx.files.internal(fileName);

        var external = Gdx.files.external(fileName);
        if (external.exists())
            return external;
        return Gdx.files.internal(fileName);
    }
}
