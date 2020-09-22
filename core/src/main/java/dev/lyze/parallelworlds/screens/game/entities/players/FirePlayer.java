package dev.lyze.parallelworlds.screens.game.entities.players;

import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;

public class FirePlayer extends Player {
    private static final Logger<FirePlayer> logger = new Logger<>(FirePlayer.class);

    public FirePlayer(Level level) {
        super(level, PlayerColor.Fire, false, null, null, null, null);
    }
}
