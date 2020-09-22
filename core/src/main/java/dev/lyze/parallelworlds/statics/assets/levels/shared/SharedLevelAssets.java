package dev.lyze.parallelworlds.statics.assets.levels.shared;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import dev.lyze.parallelworlds.statics.utils.DynamicAssets;
import dev.lyze.parallelworlds.statics.utils.LoadAssetFromFile;
import lombok.Getter;

public class SharedLevelAssets extends DynamicAssets {
    @Getter @LoadAssetFromFile("atlas/ui.atlas")
    private UiTextureAtlas uiAtlas;

    @Getter @LoadAssetFromFile("atlas/characters.atlas")
    private CharactersAtlas charactersAtlas;

    @Getter @LoadAssetFromFile("images/Pixel.png")
    private Texture pixel;

    public SharedLevelAssets(AssetManager ass) {
        super(ass);
    }
}
