package dev.lyze.parallelworlds.screens.game.entities.players;

import com.badlogic.gdx.graphics.g2d.Animation;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.enums.PlayerColor;
import dev.lyze.parallelworlds.statics.Statics;

public class FrozenPlayer extends Player {
    private static final Logger<FrozenPlayer> logger = new Logger<>(FrozenPlayer.class);

    public FrozenPlayer(Level level) {
        super(level, PlayerColor.Ice, true,
                new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getFrozenbread_idle(), Animation.PlayMode.LOOP),
                new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getFrozenbread_run(), Animation.PlayMode.LOOP),
                new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getFrozenbread_jump(), Animation.PlayMode.NORMAL),
                new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getFrozenbread_fall(), Animation.PlayMode.NORMAL),
                new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getFrozenbread_death(), Animation.PlayMode.NORMAL));
    }
}
