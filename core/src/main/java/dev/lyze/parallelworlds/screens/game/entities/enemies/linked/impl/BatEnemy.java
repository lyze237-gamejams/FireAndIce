package dev.lyze.parallelworlds.screens.game.entities.enemies.linked.impl;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.StaticEntity;
import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.LinkedEnemy;
import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.LinkedEnemyKillPart;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.statics.Statics;

public class BatEnemy extends LinkedEnemy {
    private static final Logger<BatEnemy> logger = new Logger<>(BatEnemy.class);

    private boolean currentlyMoveRight = true;

    public BatEnemy(float x, float y, Level level, float killPartX, float killPartY, boolean invertedGravity) {
        super(x, y, level, killPartX, killPartY, invertedGravity);

        setRun(new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getSnail_walk(), Animation.PlayMode.LOOP));
        setJump(new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getSnail_walk(), Animation.PlayMode.LOOP));
        setFall(new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getSnail_walk(), Animation.PlayMode.LOOP));
        setDeath(new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getSnail_death(), Animation.PlayMode.NORMAL));

        setFloating(true);

        setAnimationXOffset(-0.6f);
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);

        if (linkedEnemyKillPart.isDead()) {
            wantsToMoveLeft = 0;
            wantsToMoveRight = 0;
            return;
        }

        if (currentlyMoveRight) {
            wantsToMoveRight = 0.3f;
            wantsToMoveLeft = 0;
        }
        else {
            wantsToMoveLeft = 0.3f;
            wantsToMoveRight = 0;
        }
    }

    @Override
    protected LinkedEnemyKillPart createEnemyKillPart(float x, float y, Level level, boolean invertedGravity) {
        return new BatEnemyKillPart(x, y, level, this, !invertedGravity);
    }

    @Override
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (collision.other.userData instanceof StaticEntity) {
            if (((StaticEntity) collision.other.userData).isHitbox()) {
                if (collision.normal.x != 0) {
                    logger.logInfo("TURNING AROUND");
                    currentlyMoveRight = !currentlyMoveRight;
                }
            }
            return;
        }

        if (!(collision.other.userData instanceof Player))
            return;

        logger.logInfo("Haha, player is a noob and died!");
        level.killPlayer();
    }
}
