package dev.lyze.parallelworlds.screens.game.entities.impl;

import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.StaticEntity;

public class GroundTile extends StaticEntity {
    public GroundTile(float x, float y, float width, float height, Level level) {
        super(x, y, width, height, level);

        setCollidable(true);
    }
}
