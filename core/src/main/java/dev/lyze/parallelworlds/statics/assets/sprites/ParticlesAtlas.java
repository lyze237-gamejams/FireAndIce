package dev.lyze.parallelworlds.statics.assets.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import dev.lyze.parallelworlds.statics.utils.DynamicTextureAtlas;
import dev.lyze.parallelworlds.statics.utils.LoadFromTextureAtlas;
import lombok.Getter;

public class ParticlesAtlas extends DynamicTextureAtlas {
    @Getter @LoadFromTextureAtlas("coins/Explode")
    private Array<TextureAtlas.AtlasRegion> coins_explode;

    @Getter @LoadFromTextureAtlas("coins/Idle")
    private Array<TextureAtlas.AtlasRegion> coins_idle;

    public ParticlesAtlas(TextureAtlas atlas) {
        super(atlas);
    }
}
