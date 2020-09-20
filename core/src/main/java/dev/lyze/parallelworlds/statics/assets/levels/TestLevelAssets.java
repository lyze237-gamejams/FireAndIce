package dev.lyze.parallelworlds.statics.assets.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import dev.lyze.parallelworlds.statics.utils.LoadAssetFromFile;

public class TestLevelAssets extends LevelAssets {
    @LoadAssetFromFile("maps/Test.tmx")
    private TiledMap map;

    public TestLevelAssets(AssetManager assMan) {
        super(assMan);
    }
}
