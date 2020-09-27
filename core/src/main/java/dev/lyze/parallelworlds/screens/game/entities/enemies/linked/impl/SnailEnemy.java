package dev.lyze.parallelworlds.screens.game.entities.enemies.linked.impl;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.LinkedEnemy;
import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.LinkedEnemyKillPart;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.statics.Statics;

public class SnailEnemy extends LinkedEnemy {
    private static final Logger<SnailEnemy> logger = new Logger<>(SnailEnemy.class);

    private boolean currentlyMoveRight = true;

    public SnailEnemy(float x, float y, Level level, int partsOffset, boolean invertedGravity) {
        super(x, y, level, partsOffset, invertedGravity);

        setRun(new Animation<>(0.1f, Statics.assets.getGame().getCharactersAtlas().getSnail_walk(), Animation.PlayMode.LOOP));
        setDeath(new Animation<>(0.1f, Statics.assets.getGame().getCharactersAtlas().getSnail_death(), Animation.PlayMode.NORMAL));

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
    protected LinkedEnemyKillPart createEnemyKillPart(float x, float y, Level level, int partsOffset, boolean invertedGravity) {
        return new SnailEnemyKillPart(x, y, level, partsOffset,this, !invertedGravity);
    }

    @Override
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (!(collision.other.userData instanceof Player)) {
            if (!collision.type.equals(Response.slide))
                return;

            logger.logInfo("Turning around because of " + collision.other.userData.getClass());
            currentlyMoveRight = !currentlyMoveRight;
            return;
        }

        logger.logInfo("Haha, player is a noob and died!");
        level.killPlayer();
    }
}
