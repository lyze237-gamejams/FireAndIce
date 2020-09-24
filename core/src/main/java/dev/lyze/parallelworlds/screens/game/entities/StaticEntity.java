package dev.lyze.parallelworlds.screens.game.entities;

import dev.lyze.parallelworlds.screens.game.Level;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class StaticEntity extends Entity {
    @Getter @Setter(AccessLevel.PROTECTED)
    private boolean isCollidable;
    public StaticEntity(float x, float y, float width, float height, Level level) {
        super(x, y, width, height, level);
    }
}
