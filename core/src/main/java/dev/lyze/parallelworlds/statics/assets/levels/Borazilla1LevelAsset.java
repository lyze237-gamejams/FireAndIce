package dev.lyze.parallelworlds.statics.assets.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import dev.lyze.parallelworlds.statics.utils.LoadAssetFromFile;

public class Borazilla1LevelAsset extends LevelAssets {
    @LoadAssetFromFile("maps/Borazilla_1.tmx")
    private TiledMap map;

    public Borazilla1LevelAsset(AssetManager assMan) {
        super(assMan);
    }
}
