package dev.lyze.parallelworlds.statics.assets.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import dev.lyze.parallelworlds.statics.utils.LoadAssetFromFile;

public class Nsyse1LevelAsset extends LevelAssets {
    @LoadAssetFromFile("maps/Nsyse_1.tmx")
    private TiledMap map;

    public Nsyse1LevelAsset(AssetManager assMan) {
        super(assMan);
    }
}
