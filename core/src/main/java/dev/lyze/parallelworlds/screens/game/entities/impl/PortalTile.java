package dev.lyze.parallelworlds.screens.game.entities.impl;

import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.TileEntity;
import dev.lyze.parallelworlds.screens.game.entities.enums.PlayerColor;
import lombok.Getter;

public class PortalTile extends TileEntity {
    @Getter
    private final PlayerColor color;

    public PortalTile(float x, float y, Level level, PlayerColor color) {
        super(x, y, 1, 1, level);
        this.color = color;
    }
}
