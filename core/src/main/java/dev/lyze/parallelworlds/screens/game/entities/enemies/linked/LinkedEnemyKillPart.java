package dev.lyze.parallelworlds.screens.game.entities.enemies.linked;

import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.AiEntity;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.filters.EnemyCollisionFilter;

public class LinkedEnemyKillPart extends AiEntity {
    private static final Logger<LinkedEnemyKillPart> logger = new Logger<>(LinkedEnemyKillPart.class);

    protected final LinkedEnemy linkedEnemy;

    public LinkedEnemyKillPart(float x, float y, Level level, LinkedEnemy linkedEnemy, boolean invertedGravity) {
        super(x, y, 2, 1.4f, level, EnemyCollisionFilter.instance);

        this.linkedEnemy = linkedEnemy;
        this.invertedGravity = invertedGravity;
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);

        this.position.x = this.linkedEnemy.getPosition().x;
        setFacingRight(linkedEnemy.isFacingRight());
    }
}
