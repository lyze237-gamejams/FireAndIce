package dev.lyze.parallelworlds.statics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.statics.assets.GameAssets;
import dev.lyze.parallelworlds.statics.assets.levels.shared.CharactersAtlas;
import dev.lyze.parallelworlds.statics.assets.levels.shared.ParticlesAtlas;
import dev.lyze.parallelworlds.statics.assets.levels.shared.UiTextureAtlas;
import dev.lyze.parallelworlds.statics.assets.loadingScreen.LoadingScreenAssets;
import dev.lyze.parallelworlds.statics.assets.mainMenu.MainMenuAssets;
import dev.lyze.parallelworlds.statics.assets.mainMenu.MainMenuTextureAtlas;
import dev.lyze.parallelworlds.statics.assets.sfx.MusicAssets;
import dev.lyze.parallelworlds.statics.utils.DynamicTextureAtlasAssetLoader;
import dev.lyze.parallelworlds.statics.utils.InternalOrExternalFileHandleResolver;
import lombok.Getter;

public class Assets {
    private static final Logger<Assets> logger = new Logger<>(Assets.class);

    @Getter
    private final MainMenuAssets mainMenu;
    @Getter
    private final GameAssets game;
    @Getter
    private final LoadingScreenAssets loadingScreen;
    @Getter
    private final MusicAssets music;

    public Assets() {
        mainMenu = new MainMenuAssets(createAssMan());
        loadingScreen = new LoadingScreenAssets(createAssMan());
        music = new MusicAssets(createAssMan());

        game = new GameAssets();
    }

    public void load() {
        mainMenu.load();
        game.load();
        loadingScreen.load();
        music.load();
    }

    // gwt errors if not done like this
    @SuppressWarnings({"rawtypes", "unchecked"})
    public AssetManager createAssMan() {
        AssetManager ass = new AssetManager();
        ass.setLoader(TiledMap.class, new TmxMapLoader(new InternalOrExternalFileHandleResolver()));
        for (Class<?> textureAtlasClass : new Class<?>[] { MainMenuTextureAtlas.class,  UiTextureAtlas.class, CharactersAtlas.class, ParticlesAtlas.class}) {
            ass.setLoader(textureAtlasClass, new DynamicTextureAtlasAssetLoader(new InternalOrExternalFileHandleResolver(), textureAtlasClass));
        }

        return ass;
    }
}

