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

    @Getter @LoadFromTextureAtlas("frozenbread/Death")
    private Array<TextureAtlas.AtlasRegion> frozenbread_death;

    @Getter @LoadFromTextureAtlas("frozenbread/Idle")
    private Array<TextureAtlas.AtlasRegion> frozenbread_idle;

    @Getter @LoadFromTextureAtlas("frozenbread/Jump")
    private Array<TextureAtlas.AtlasRegion> frozenbread_jump;

    @Getter @LoadFromTextureAtlas("frozenbread/Fall")
    private Array<TextureAtlas.AtlasRegion> frozenbread_fall;

    @Getter @LoadFromTextureAtlas("frozenbread/Run")
    private Array<TextureAtlas.AtlasRegion> frozenbread_run;


    @Getter @LoadFromTextureAtlas("snail/Walk")
    private Array<TextureAtlas.AtlasRegion> snail_walk;

    @Getter @LoadFromTextureAtlas("snail/Death")
    private Array<TextureAtlas.AtlasRegion> snail_death;

    @Getter @LoadFromTextureAtlas("snailsoul/Walk")
    private Array<TextureAtlas.AtlasRegion> snailsoul_walk;

    @Getter @LoadFromTextureAtlas("snailsoul/Death")
    private Array<TextureAtlas.AtlasRegion> snailsoul_death;

    @Getter @LoadFromTextureAtlas("coins/BigCoin")
    private Array<TextureAtlas.AtlasRegion> coins_bigCoin;

    public CharactersAtlas(TextureAtlas atlas) {
        super(atlas);
    }
}
