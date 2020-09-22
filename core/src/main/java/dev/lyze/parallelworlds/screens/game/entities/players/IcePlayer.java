package dev.lyze.parallelworlds.screens.game.entities.players;

import com.badlogic.gdx.graphics.g2d.Animation;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.statics.Statics;

public class IcePlayer extends Player {
    private static final Logger<IcePlayer> logger = new Logger<>(IcePlayer.class);

    public IcePlayer(Level level) {
        super(level, PlayerColor.Ice, true,
                new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getFlamebread_idle(), Animation.PlayMode.LOOP),
                new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getFlamebread_run(), Animation.PlayMode.LOOP),
                new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getFlamebread_jump(), Animation.PlayMode.NORMAL),
                new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getFlamebread_fall(), Animation.PlayMode.NORMAL),
                new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getFlamebread_death(), Animation.PlayMode.NORMAL));
    }
}
