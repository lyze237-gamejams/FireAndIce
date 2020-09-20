package dev.lyze.parallelworlds.statics.assets.levels.shared;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.parallelworlds.statics.utils.DynamicTextureAtlas;
import dev.lyze.parallelworlds.statics.utils.LoadFromTextureAtlas;
import lombok.Getter;

public class UiTextureAtlas extends DynamicTextureAtlas {
    @Getter @LoadFromTextureAtlas("Coin")
    private TextureAtlas.AtlasRegion coin;

    public UiTextureAtlas(TextureAtlas atlas) {
        super(atlas);
    }
}
