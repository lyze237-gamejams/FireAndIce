package dev.lyze.parallelworlds.screens.game.entities.enemies.linked;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.AiEntity;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.filters.EnemyCollisionFilter;
import dev.lyze.parallelworlds.screens.game.entities.particles.ParticlesExplosion;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.statics.Statics;

public class LinkedEnemyKillPart extends AiEntity {
    private static final Logger<LinkedEnemyKillPart> logger = new Logger<>(LinkedEnemyKillPart.class);
    private final LinkedEnemy linkedEnemy;

    public LinkedEnemyKillPart(float x, float y, Level level, LinkedEnemy linkedEnemy, boolean invertedGravity) {
        super(x, y, 2, 1.4f, level, EnemyCollisionFilter.instance);

        setRun(new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getSnailsoul_walk(), Animation.PlayMode.LOOP));
        setDeath(new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getSnailsoul_death(), Animation.PlayMode.NORMAL));

        this.linkedEnemy = linkedEnemy;
        this.invertedGravity = invertedGravity;

        setAnimationXOffset(-0.6f);
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);
        setFacingRight(linkedEnemy.isFacingRight());
    }

    @Override
    protected void updateAnimation() {
        if (isDead())
            setAnimation(getDeath());
        else
            setAnimation(getRun());
    }


    @Override
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (!(collision.other.userData instanceof Player))
            return;

        logger.logInfo("Ohno I died");
        linkedEnemy.die();
        die();

        for (int i = 0; i < 10; i++) {
            level.addEntity(new ParticlesExplosion(linkedEnemy.getPosition().x, linkedEnemy.getPosition().y, level, !invertedGravity));
        }
    }
}
