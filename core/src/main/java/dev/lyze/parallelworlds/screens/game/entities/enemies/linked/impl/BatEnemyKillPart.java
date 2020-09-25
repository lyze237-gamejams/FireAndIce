package dev.lyze.parallelworlds.screens.game.entities.enemies.linked.impl;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.LinkedEnemy;
import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.LinkedEnemyKillPart;
import dev.lyze.parallelworlds.screens.game.entities.particles.ParticlesExplosion;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.statics.Statics;

public class BatEnemyKillPart extends LinkedEnemyKillPart {
    private static final Logger<BatEnemyKillPart> logger = new Logger<>(BatEnemyKillPart.class);

    private float startY;

    public BatEnemyKillPart(float x, float y, Level level, int partsOffset, LinkedEnemy linkedEnemy, boolean invertedGravity) {
        super(x, y, level, partsOffset, linkedEnemy, invertedGravity);

        this.startY = this.position.y;

        setRun(new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getBatsoul_walk(), Animation.PlayMode.LOOP));
        setDeath(new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getBatsoul_death(), Animation.PlayMode.NORMAL));

        setAnimationXOffset(-0.6f);
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);

        this.position.y = startY + (((BatEnemy) linkedEnemy).getStartY() - linkedEnemy.getPosition().y);
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
            level.addEntity(new ParticlesExplosion(linkedEnemy.getPosition().x, linkedEnemy.getPosition().y, level, !isInvertedWorld()));
        }
    }
}

