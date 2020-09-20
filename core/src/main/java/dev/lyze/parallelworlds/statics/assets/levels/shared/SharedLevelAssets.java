package dev.lyze.parallelworlds.statics.assets.levels.shared;

import com.badlogic.gdx.assets.AssetManager;
import dev.lyze.parallelworlds.statics.utils.DynamicAssets;
import dev.lyze.parallelworlds.statics.utils.LoadAssetFromFile;
import lombok.Getter;

public class SharedLevelAssets extends DynamicAssets {
    @Getter @LoadAssetFromFile("atlas/ui.atlas")
    private UiTextureAtlas uiAtlas;

    public SharedLevelAssets(AssetManager ass) {
        super(ass);
    }
}
