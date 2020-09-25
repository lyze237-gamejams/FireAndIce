package dev.lyze.parallelworlds.screens.game.entities.impl;

import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.TileEntity;
import lombok.Getter;

public class ExitTile extends TileEntity {
    @Getter
    private String levelName;

    public ExitTile(float x, float y, Level level, String levelName) {
        super(x, y, 1, 1, level);

        this.levelName = levelName;
    }
}
