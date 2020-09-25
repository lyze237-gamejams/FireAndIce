package dev.lyze.parallelworlds.screens.game.entities.enemies.linked;

import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.MoveableEntity;
import dev.lyze.parallelworlds.screens.game.entities.filters.SnailEnemyCollisionFilter;

public abstract class LinkedEnemy extends MoveableEntity {
    private static final Logger<LinkedEnemy> logger = new Logger<>(LinkedEnemy.class);

    protected final LinkedEnemyKillPart linkedEnemyKillPart;

    public LinkedEnemy(float x, float y, Level level, float killPartX, float killPartY, boolean invertedWorld) {
        super(x, y, 2, 1.4f, level, SnailEnemyCollisionFilter.instance);

        this.setInvertedWorld(invertedWorld);

        linkedEnemyKillPart = createEnemyKillPart(killPartX, killPartY, level, invertedWorld);
        level.addEntity(linkedEnemyKillPart);
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);
    }

    protected abstract LinkedEnemyKillPart createEnemyKillPart(float x, float y, Level level, boolean invertedGravity);
}
