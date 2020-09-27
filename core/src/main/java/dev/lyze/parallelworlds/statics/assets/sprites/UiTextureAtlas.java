package dev.lyze.parallelworlds.statics.assets.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.parallelworlds.statics.utils.DynamicTextureAtlas;
import dev.lyze.parallelworlds.statics.utils.LoadFromTextureAtlas;
import lombok.Getter;

public class UiTextureAtlas extends DynamicTextureAtlas {
    @Getter @LoadFromTextureAtlas("Blue_Down")
    private TextureAtlas.AtlasRegion blueDown;
    
    @Getter @LoadFromTextureAtlas("Blue_Up")
    private TextureAtlas.AtlasRegion blueUp;
    
    @Getter @LoadFromTextureAtlas("Blue_Left")
    private TextureAtlas.AtlasRegion blueLeft;
    
    @Getter @LoadFromTextureAtlas("Blue_Right")
    private TextureAtlas.AtlasRegion blueRight;
    
    @Getter @LoadFromTextureAtlas("Red_Down")
    private TextureAtlas.AtlasRegion redDown;

    @Getter @LoadFromTextureAtlas("Red_Up")
    private TextureAtlas.AtlasRegion redUp;

    @Getter @LoadFromTextureAtlas("Red_Left")
    private TextureAtlas.AtlasRegion redLeft;

    @Getter @LoadFromTextureAtlas("Red_Right")
    private TextureAtlas.AtlasRegion redRight;

    public UiTextureAtlas(TextureAtlas atlas) {
        super(atlas);
    }
}
