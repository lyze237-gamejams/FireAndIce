package dev.lyze.parallelworlds.screens.game.entities.impl;

import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.TileEntity;
import dev.lyze.parallelworlds.screens.game.entities.enums.Direction;
import lombok.Getter;

public class PortalDirectionTile extends TileEntity {
    @Getter
    private final Direction direction;

    public PortalDirectionTile(float x, float y, Level level, Direction direction) {
        super(x, y, 1, 1, level);
        this.direction = direction;
    }
}
