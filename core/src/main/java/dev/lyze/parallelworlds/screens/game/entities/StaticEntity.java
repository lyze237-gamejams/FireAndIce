package dev.lyze.parallelworlds.screens.game.entities;

import dev.lyze.parallelworlds.screens.game.Level;
import lombok.Getter;

public class StaticEntity extends Entity {
    @Getter
    protected boolean isSolid;

    public StaticEntity(float x, float y, float width, float height, Level level, boolean isSolid) {
        super(x, y, width, height, level);
        this.isSolid = isSolid;
    }
}
