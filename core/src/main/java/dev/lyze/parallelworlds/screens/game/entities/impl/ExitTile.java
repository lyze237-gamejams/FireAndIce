package dev.lyze.parallelworlds.screens.game.entities.impl;

import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.TileEntity;

public class ExitTile extends TileEntity {
    public ExitTile(float x, float y, Level level) {
        super(x, y, 1, 1, level);
    }
}
