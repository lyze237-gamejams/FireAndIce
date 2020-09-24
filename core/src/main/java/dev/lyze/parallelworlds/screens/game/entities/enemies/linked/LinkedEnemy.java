package dev.lyze.parallelworlds.screens.game.entities.enemies.linked;

import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.AiEntity;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.filters.EnemyCollisionFilter;

public abstract class LinkedEnemy extends AiEntity {
    private static final Logger<LinkedEnemy> logger = new Logger<>(LinkedEnemy.class);

    protected final LinkedEnemyKillPart linkedEnemyKillPart;

    public LinkedEnemy(float x, float y, Level level, float killPartX, float killPartY, boolean invertedGravity) {
        super(x, y, 2, 1.4f, level, EnemyCollisionFilter.instance);

        this.invertedGravity = invertedGravity;

        linkedEnemyKillPart = createEnemyKillPart(killPartX, killPartY, level, invertedGravity);
        level.addEntity(linkedEnemyKillPart);
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);
    }

    protected abstract LinkedEnemyKillPart createEnemyKillPart(float x, float y, Level level, boolean invertedGravity);
}
