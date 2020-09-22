package dev.lyze.parallelworlds.statics.assets.levels.shared;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import dev.lyze.parallelworlds.statics.utils.DynamicTextureAtlas;
import dev.lyze.parallelworlds.statics.utils.LoadFromTextureAtlas;
import lombok.Getter;

public class CharactersAtlas extends DynamicTextureAtlas {
    @Getter @LoadFromTextureAtlas("flamebread/Death")
    private Array<TextureAtlas.AtlasRegion> flamebread_death;

    @Getter @LoadFromTextureAtlas("flamebread/Idle")
    private Array<TextureAtlas.AtlasRegion> flamebread_idle;

    @Getter @LoadFromTextureAtlas("flamebread/Jump")
    private Array<TextureAtlas.AtlasRegion> flamebread_jump;

    @Getter @LoadFromTextureAtlas("flamebread/Fall")
    private Array<TextureAtlas.AtlasRegion> flamebread_fall;

    @Getter @LoadFromTextureAtlas("flamebread/Run")
    private Array<TextureAtlas.AtlasRegion> flamebread_run;

    public CharactersAtlas(TextureAtlas atlas) {
        super(atlas);
    }
}
