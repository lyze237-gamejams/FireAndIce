package dev.lyze.parallelworlds.screens.game.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.*;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.utils.MathUtils;
import dev.lyze.parallelworlds.utils.Vector3Pool;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class MoveableEntity extends Entity {
    private static final Logger<MoveableEntity> logger = new Logger<>(MoveableEntity.class);

    private final float movementSpeedIncrease = 10f;
    private final float maxSpeed = 0.25f;
    private final float friction = 5f;

    protected final Vector2 velocity = new Vector2();
    protected final Vector2 inputVelocity = new Vector2();

    @Getter
    @Setter
    protected boolean isFacingRight = true;

    protected float wantsToMoveLeft;
    protected float wantsToMoveRight;

    @Getter @Setter(AccessLevel.PROTECTED)
    private float animationXOffset;

    @Getter
    private boolean isDead;

    @Getter
    private boolean isGrounded;
    @Getter
    private double lastGrounded;

    @Getter @Setter
    private Animation<TextureAtlas.AtlasRegion> idle, run, death;
    private Animation<TextureAtlas.AtlasRegion> currentAnimation;

    @Getter @Setter
    private boolean invertedWorld;

    private float animationTime;

    @Getter @Setter
    private CollisionFilter collisionFilter;

    @Getter
    private final Collisions tempCollisions = new Collisions();

    @Getter
    private final GlyphLayout debugGlyphLayout = new GlyphLayout();

    public MoveableEntity(float x, float y, float width, float height, Level level, CollisionFilter collisionFilter) {
        super(x, y, width, height, level);

        this.collisionFilter = collisionFilter;
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);

        animationTime += delta;

        setInput();
        checkGround(world);
        checkMovementDirection();

        applyInput(delta);
        applyFriction(delta);

        beforeApplyVelocity(world, delta);
        checkCollisionsAndApplyVelocity(world, delta);

        updateAnimation();
    }

    private void checkGround(World<Entity> world) {
        world.project(item, position.x, position.y, width, height, position.x, position.y - fixInverted(0.1f), getCollisionFilter(), getTempCollisions());
        for (int i = 0; i < getTempCollisions().size(); i++) {
            if (getTempCollisions().get(i).type.equals(Response.slide)) {
                if (!isGrounded)
                    landed();

                lastGrounded = System.currentTimeMillis();
                isGrounded = true;

                return;
            }
        }

        isGrounded = false;
    }

    protected void landed() {
    }

    protected void beforeApplyVelocity(World<Entity> world, float delta) { }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        if (currentAnimation == null)
            return;

        var frame = currentAnimation.getKeyFrame(animationTime);

        var drawX = isFacingRight ? position.x + animationXOffset : position.x + width - animationXOffset;
        var drawY = invertedWorld ? position.y + height : position.y;

        var drawWidth = isFacingRight ? frame.getRegionWidth() / level.getMap().getTileWidth() : -frame.getRegionWidth() / level.getMap().getTileWidth();
        var drawHeight = invertedWorld ? -frame.getRegionHeight() / level.getMap().getTileHeight() : frame.getRegionHeight() / level.getMap().getTileHeight();

        drawWidth *= 1.5f;
        drawHeight *= 1.5f;

        batch.draw(frame, drawX, drawY, drawWidth, drawHeight);
    }

    protected void updateAnimation() {
        if (isDead)
            setAnimation(death);
        else if (velocity.x > 0 || velocity.x < 0)
            setAnimation(run);
        else
            setAnimation(idle);
    }

    protected void setAnimation(Animation<TextureAtlas.AtlasRegion> newAnimation) {
        if (this.currentAnimation == newAnimation)
            return;

        if (newAnimation == null)
            return;

        this.currentAnimation = newAnimation;
        animationTime = 0;
    }

    public void die() {
        if (!isDead)
            isDead = true;
    }

    private void checkCollisionsAndApplyVelocity(World<Entity> world, float delta) {
        if (isDead)
            return;

        var response = world.move(item, position.x + velocity.x, position.y + velocity.y, collisionFilter);

        for (int i = 0; i < response.projectedCollisions.size(); i++)
            onCollision(response.projectedCollisions.get(i));

        position.set(response.goalX, response.goalY);
    }

    protected void onCollision(Collision collision) {
        if (collision.type != Response.slide)
            return;

        if (collision.normal.x != 0) {
            // wall
            velocity.x = 0;
        }
    }

    private void setInput() {
        inputVelocity.set(wantsToMoveLeft > 0.2f ? -movementSpeedIncrease : wantsToMoveRight > 0.2f ? movementSpeedIncrease : 0, 0);
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

    private void applyInput(float delta) {
        if (inputVelocity.x > 0) {
            velocity.x = MathUtils.approach(velocity.x, maxSpeed * wantsToMoveRight, inputVelocity.x * delta);
        } else if (inputVelocity.x < 0) {
            velocity.x = MathUtils.approach(velocity.x, -maxSpeed * wantsToMoveLeft, inputVelocity.x * delta);
        }
    }

    private void applyFriction(float delta) {
        if (inputVelocity.x == 0)
            velocity.x = MathUtils.approach(velocity.x, 0, friction * delta);
    }

    protected float fixInverted(float val) {
        return invertedWorld ? -val : val;
    }

    public void debugTextRender(BitmapFont font, Camera cam, SpriteBatch screenBatch) {
        var pos = Vector3Pool.instance.obtain();
        pos.set(position, 0);
        cam.project(pos);

        var str = "WL: " + wantsToMoveLeft + "\n" +
                "WR: " + wantsToMoveRight + "\n";
        debugGlyphLayout.setText(font, str);
        font.draw(screenBatch, str, pos.x - debugGlyphLayout.width, pos.y + height);


        str = currentAnimation.getKeyFrame(animationTime).name;
        debugGlyphLayout.setText(font, str);
        font.draw(screenBatch, str, pos.x - debugGlyphLayout.width / 2f, pos.y + debugGlyphLayout.height*2);

        Vector3Pool.instance.free(pos);
    }
}
