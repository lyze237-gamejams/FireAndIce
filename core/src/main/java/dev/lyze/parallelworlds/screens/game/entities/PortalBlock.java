package dev.lyze.parallelworlds.screens.game.entities;

import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.players.PlayerColor;
import lombok.Getter;

public class PortalBlock extends StaticEntity {
    @Getter
    private final PlayerColor color;

    public PortalBlock(float x, float y, Level level, PlayerColor color) {
        super(x, y, 1, 1, level);
        this.color = color;
    }
}
