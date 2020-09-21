package dev.lyze.parallelworlds.screens.game.entities;

import dev.lyze.parallelworlds.screens.game.Level;
import lombok.Getter;

public class PortalDirectionBlock extends StaticEntity {
    @Getter
    private final Direction direction;

    public PortalDirectionBlock(float x, float y, Level level, Direction direction) {
        super(x, y, 1, 1, level);
        this.direction = direction;
    }
}
