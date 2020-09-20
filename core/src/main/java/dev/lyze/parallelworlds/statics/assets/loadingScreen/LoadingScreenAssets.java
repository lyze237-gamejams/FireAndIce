package dev.lyze.parallelworlds.statics.assets.loadingScreen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import dev.lyze.parallelworlds.statics.utils.DynamicAssets;
import dev.lyze.parallelworlds.statics.utils.LoadAssetFromFile;
import lombok.Getter;

public class LoadingScreenAssets extends DynamicAssets {
    @Getter @LoadAssetFromFile("images/Logo.png")
    private Texture logo;

    public LoadingScreenAssets(AssetManager assMan) {
        super(assMan);
    }
}
