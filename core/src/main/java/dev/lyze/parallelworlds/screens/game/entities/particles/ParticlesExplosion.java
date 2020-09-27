package dev.lyze.parallelworlds.screens.game.entities.particles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.GravityEntity;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.filters.CoinsColliderFilter;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.statics.Statics;

import java.util.Random;

public class ParticlesExplosion extends GravityEntity {
    private static final Logger<ParticlesExplosion> logger = new Logger<>(ParticlesExplosion.class);

    private static final Random random = new Random();

    private float invincibilityTimer = 0.1f;
    private boolean groundTouched;

    public ParticlesExplosion(float x, float y, Level level, boolean invertedWorld) {
        super(x, y, 1, 1, level, CoinsColliderFilter.instance);

        setInvertedWorld(invertedWorld);

        setIdle(new Animation<>(0.2f, Statics.assets.getGame().getParticlesAtlas().getCoins_idle(), Animation.PlayMode.LOOP));
        setFall(new Animation<>(0.05f, Statics.assets.getGame().getParticlesAtlas().getCoins_explode(), Animation.PlayMode.NORMAL));

        if (random.nextBoolean()) {
            wantsToMoveRight = random.nextFloat() * 0.5f + 0.3f;
        }
        else {
            wantsToMoveLeft = random.nextFloat() * 0.5f + 0.3f;
        }
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);

        invincibilityTimer -= 0.2f * delta;

        setJumpForce(getJumpForce() - 0.6f * delta);
        if (getJumpForce() <= 0) {
            setJumpForce(0);
            wantsToJump = false;
            wantsToMoveRight = 0;
            wantsToMoveLeft = 0;
        }
        else {
            wantsToMoveLeft -= 1f * delta;
            wantsToMoveRight -= 1f * delta;
            wantsToJump = true;
        }
    }

    @Override
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (!(collision.other.userData instanceof Player)) {
            if (invincibilityTimer > 0)
                return;

            groundTouched = true;
            return;
        }

        if (groundTouched) {
            Statics.assets.getSound().play(Statics.assets.getSound().getCoin1(), Statics.assets.getSound().getCoin2());
            level.addCoin();
            level.removeEntity(this);
        }
    }
}
