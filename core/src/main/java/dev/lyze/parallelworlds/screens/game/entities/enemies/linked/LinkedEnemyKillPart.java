package dev.lyze.parallelworlds.screens.game.entities.enemies.linked;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.jbump.Collision;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.AiEntity;
import dev.lyze.parallelworlds.screens.game.entities.filters.EnemyCollisionFilter;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.statics.Statics;

public class LinkedEnemyKillPart extends AiEntity {
    private static final Logger<LinkedEnemyKillPart> logger = new Logger<>(LinkedEnemyKillPart.class);
    private final LinkedEnemy linkedEnemy;

    public LinkedEnemyKillPart(float x, float y, Level level, LinkedEnemy linkedEnemy, boolean invertedGravity) {
        super(x, y, 1, 1, level, EnemyCollisionFilter.instance);

        this.linkedEnemy = linkedEnemy;
        this.invertedGravity = invertedGravity;
    }

    @Override
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (!(collision.other.userData instanceof Player))
            return;

        logger.logInfo("Ohno I died");
        level.removeEntity(this);
        level.removeEntity(linkedEnemy);
        // ohno they got me :(
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        batch.setColor(Color.ORANGE);
        batch.draw(Statics.assets.getGame().getSharedLevelAssets().getPixel(), position.x, position.y, width, height);
        batch.setColor(Color.WHITE);
    }
}
