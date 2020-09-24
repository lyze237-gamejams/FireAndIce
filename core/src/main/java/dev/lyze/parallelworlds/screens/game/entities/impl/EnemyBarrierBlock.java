package dev.lyze.parallelworlds.screens.game.entities.impl;

import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.StaticEntity;

public class EnemyBarrierBlock extends StaticEntity {
    public EnemyBarrierBlock(float x, float y, Level level, boolean invertedGravity) {
        super(x, y, 1, 1, level);

        setHitbox(true);
    }
}
