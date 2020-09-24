package dev.lyze.parallelworlds.screens.game.entities.enemies.linked;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.AiEntity;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.filters.EnemyCollisionFilter;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.statics.Statics;

public class LinkedEnemy extends AiEntity {
    private static final Logger<LinkedEnemy> logger = new Logger<>(LinkedEnemy.class);

    private final LinkedEnemyKillPart linkedEnemyKillPart;

    private boolean currentlyMoveRight = true;

    public LinkedEnemy(float x, float y, Level level, int killPartX, int killPartY, boolean invertedGravity) {
        super(x, y, 2, 1.4f, level, EnemyCollisionFilter.instance);

        this.invertedGravity = invertedGravity;

        setRun(new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getSnail_walk(), Animation.PlayMode.LOOP));
        setDeath(new Animation<>(0.1f, Statics.assets.getGame().getSharedLevelAssets().getCharactersAtlas().getSnail_death(), Animation.PlayMode.NORMAL));

        linkedEnemyKillPart = new LinkedEnemyKillPart(killPartX, killPartY, level, this, !invertedGravity);
        level.addEntity(linkedEnemyKillPart);

        setAnimationXOffset(-0.6f);
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);

        this.linkedEnemyKillPart.getPosition().x = this.position.x;

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
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (!(collision.other.userData instanceof Player)) {
            if (collision.normal.x != 0) {
                logger.logInfo("TURNING AROUND");
                currentlyMoveRight = !currentlyMoveRight;
            }
            return;
        }

        logger.logInfo("Haha, player is a noob and died!");
        level.killPlayer();
    }
}
