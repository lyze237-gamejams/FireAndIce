package dev.lyze.parallelworlds.screens.game.entities.players;

import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;

public class IcePlayer extends Player {
    private static final Logger<IcePlayer> logger = new Logger<>(IcePlayer.class);

    public IcePlayer(Level level) {
        super(level, PlayerColor.Ice, false, null, null, null, null);
    }
}
