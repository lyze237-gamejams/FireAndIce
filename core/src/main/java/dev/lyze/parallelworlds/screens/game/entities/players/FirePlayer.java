package dev.lyze.parallelworlds.screens.game.entities.players;

import com.badlogic.gdx.graphics.g2d.Animation;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.enums.PlayerColor;
import dev.lyze.parallelworlds.statics.Statics;

public class FirePlayer extends Player {
    private static final Logger<FirePlayer> logger = new Logger<>(FirePlayer.class);

    public FirePlayer(Level level) {
        super(level, PlayerColor.Fire, false,
                new Animation<>(0.05f, Statics.assets.getGame().getCharactersAtlas().getFlamebread_idle(), Animation.PlayMode.LOOP),
                new Animation<>(0.05f, Statics.assets.getGame().getCharactersAtlas().getFlamebread_run(), Animation.PlayMode.LOOP),
                new Animation<>(0.05f, Statics.assets.getGame().getCharactersAtlas().getFlamebread_jump(), Animation.PlayMode.NORMAL),
                new Animation<>(0.05f, Statics.assets.getGame().getCharactersAtlas().getFlamebread_fall(), Animation.PlayMode.NORMAL),
                new Animation<>(0.05f, Statics.assets.getGame().getCharactersAtlas().getFlamebread_death(), Animation.PlayMode.NORMAL));
    }
}
