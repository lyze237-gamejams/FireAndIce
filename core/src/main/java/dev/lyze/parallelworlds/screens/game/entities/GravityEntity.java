package dev.lyze.parallelworlds.screens.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.filters.StaticBlockFilter;
import dev.lyze.parallelworlds.utils.MathUtils;
import dev.lyze.parallelworlds.utils.Vector2Pool;

public class GravityEntity extends Entity {
    private static final Logger<GravityEntity> logger = new Logger<>(GravityEntity.class);

    private final float gravity = -2f;
    private final float maxSpeed = 20f;
    private final float friction = 2f;

    private final Vector2 velocity = new Vector2();
    protected final Vector2 inputVelocity = new Vector2();

    protected boolean isFacingRight = true;

    protected boolean invertedGravity = false;

    public GravityEntity(float x, float y, float width, float height, Level level) {
        super(x, y, width, height, level);
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);

        checkMovementDirection();

        applyInput();
        applyGravity();
        applyFriction();

        applyVelocity(world, delta);
    }

    private void applyVelocity(World<Entity> world, float delta) {
        var response = world.move(item, position.x + velocity.x * delta, position.y + velocity.y * delta, StaticBlockFilter.instance);

        for (int i = 0; i < response.projectedCollisions.size(); i++) {
            onCollision(response.projectedCollisions.get(i));
        }

        position.set(response.goalX, response.goalY);
    }

    private void onCollision(Collision collision) {
        if (collision.other.userData instanceof StaticEntity) {
            if (collision.normal.x != 0) {
                // wall
                velocity.x = 0;
            }
            if (collision.normal.y != 0) {
                // ceiling or floor
                velocity.y = 0;
            }
        }
    }

    private void checkMovementDirection() {
        if (isFacingRight && inputVelocity.x < 0) {
            flip();
        }
        else if (!isFacingRight && inputVelocity.x > 0) {
            flip();
        }
    }

    private void flip() {
        isFacingRight = !isFacingRight;
    }

    private void applyInput() {
        if (inputVelocity.x > 0) {
            velocity.x = MathUtils.approach(velocity.x, maxSpeed, inputVelocity.x);
        } else if (inputVelocity.x < 0) {
            velocity.x = MathUtils.approach(velocity.x, -maxSpeed, inputVelocity.x);
        }
    }

    private void applyFriction() {
        if (inputVelocity.x == 0)
            velocity.x = MathUtils.approach(velocity.x, 0, friction);
    }

    private void applyGravity() {
        velocity.y += ((invertedGravity) ? -gravity : gravity);
    }
}
