package dev.lyze.parallelworlds.statics.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import dev.lyze.parallelworlds.statics.assets.levels.shared.CharactersAtlas;
import dev.lyze.parallelworlds.statics.assets.levels.shared.ParticlesAtlas;
import dev.lyze.parallelworlds.statics.assets.levels.shared.UiTextureAtlas;
import dev.lyze.parallelworlds.statics.utils.DynamicAssets;
import dev.lyze.parallelworlds.statics.utils.LoadAssetFromFile;
import lombok.Getter;

public class GameAssets extends DynamicAssets  {
    @Getter @LoadAssetFromFile("atlas/ui.atlas")
    private UiTextureAtlas uiAtlas;

    @Getter @LoadAssetFromFile("atlas/characters.atlas")
    private CharactersAtlas charactersAtlas;

    @Getter @LoadAssetFromFile("atlas/particles.atlas")
    private ParticlesAtlas particlesAtlas;

    @Getter @LoadAssetFromFile("images/Pixel.png")
    private Texture pixel;

    @Getter @LoadAssetFromFile("skins/default/default.json")
    private Skin skin;

    @Getter @LoadAssetFromFile("maps/Test.tmx")
    private TiledMap test;
    @Getter @LoadAssetFromFile("maps/Nsyse_1.tmx")
    private TiledMap nsyse1;
    @Getter @LoadAssetFromFile("maps/Borazilla_1.tmx")
    private TiledMap borazilla1;

    public GameAssets(AssetManager assMan) {
        super(assMan);
    }

    public TiledMap get(String path) {
        return getAssMan().get("maps/" + path + ".tmx", TiledMap.class);
    }
}
