package dev.lyze.parallelworlds.statics.assets.mainMenu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import dev.lyze.parallelworlds.statics.utils.DynamicTextureAtlas;
import dev.lyze.parallelworlds.statics.utils.LoadFromTextureAtlas;
import lombok.Getter;

public class MainMenuTextureAtlas extends DynamicTextureAtlas {
    @Getter @LoadFromTextureAtlas("layer")
    private Array<TextureAtlas.AtlasRegion> layers;

    @Getter @LoadFromTextureAtlas("clouds")
    private TextureAtlas.AtlasRegion clouds;

    public MainMenuTextureAtlas(TextureAtlas atlas) {
        super(atlas);
    }
}
