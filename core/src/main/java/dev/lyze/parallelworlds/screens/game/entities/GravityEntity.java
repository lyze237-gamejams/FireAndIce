package dev.lyze.parallelworlds.screens.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import lombok.Getter;
import lombok.Setter;

public class GravityEntity extends MoveableEntity {
    private static final Logger<GravityEntity> logger = new Logger<>(GravityEntity.class);

    private final float gravity = -1.8f;

    @Getter @Setter
    private float jumpForce = 0.80f;

    @Getter @Setter
    private Animation<TextureAtlas.AtlasRegion> jump, fall;

    private final double jumpAfterGroundLeftMax = 150;

    @Getter
    private boolean isJumping;

    protected boolean wantsToJump;

    public GravityEntity(float x, float y, float width, float height, Level level, CollisionFilter collisionFilter) {
        super(x, y, width, height, level, collisionFilter);
    }

    @Override
    public void update(World<Entity> world, float delta) {
        applyGravity(delta);
        checkJump();
        super.update(world, delta);
    }

    @Override
    protected void beforeApplyVelocity(World<Entity> world, float delta) {
        applyGravity(delta);
    }

    @Override
    protected void updateAnimation() {
        if (isDead())
            setAnimation(getDeath());
        else if (isJumping)
            setAnimation(jump);
        else if (!isGrounded())
            setAnimation(fall);
        else if (velocity.x > 0 || velocity.x < 0)
            setAnimation(getRun());
        else
            setAnimation(getIdle());
    }

    private void checkJump() {
        if (!wantsToJump)
            return;

        if ((isGrounded() || (System.currentTimeMillis() - getLastGrounded()) < jumpAfterGroundLeftMax) && !isJumping)
            jump();
    }

    protected void jump() {
        if (isInvertedWorld() ? velocity.y > 0 : velocity.y < 0)
            velocity.y = 0;

        velocity.y += fixInverted(jumpForce);
        isJumping = true;
    }

    protected void onCollision(Collision collision) {
        if (collision.type != Response.slide)
            return;

        if (collision.normal.y != 0) {
            // ceiling or floor
            velocity.y = 0;

            if (collision.normal.y == fixInverted(1)) {
                isJumping = false;
            }
        }
    }

    private void applyGravity(float delta) {
        velocity.y += fixInverted(gravity) * delta;

        if (isJumping && isInvertedWorld() ? (velocity.y > 0) : (velocity.y < 0))
            isJumping = false;
    }
}
