package dev.lyze.parallelworlds.screens.game.entities;

import dev.lyze.parallelworlds.screens.game.Level;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class TileEntity extends Entity {
    @Getter @Setter(AccessLevel.PROTECTED)
    private boolean hitbox;
    public TileEntity(float x, float y, float width, float height, Level level) {
        super(x, y, width, height, level);
    }
}
