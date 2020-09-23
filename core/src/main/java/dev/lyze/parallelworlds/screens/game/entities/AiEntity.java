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

public class AiEntity extends Entity {
    private static final Logger<AiEntity> logger = new Logger<>(AiEntity.class);

    private final float gravity = -1.8f;
    private final float movementSpeedIncrease = 10f;
    private final float maxSpeed = 0.25f;
    private final float friction = 5f;

    private final float jumpForce = 0.80f;

    private final double jumpAfterGroundLeftMax = 150;

    protected final Vector2 velocity = new Vector2();
    protected final Vector2 inputVelocity = new Vector2();

    @Getter
    @Setter
    protected boolean isFacingRight = true;

    protected boolean invertedGravity = false;

    protected float wantsToMoveLeft;
    protected float wantsToMoveRight;
    protected boolean wantsToJump;

    @Getter @Setter(AccessLevel.PROTECTED)
    private float animationXOffset;

    @Getter
    private boolean isJumping;
    @Getter
    private boolean isGrounded;
    @Getter
    private boolean isDead;
    private double lastGrounded = 0f;

    @Getter @Setter
    private Animation<TextureAtlas.AtlasRegion> idle, run, jump, fall, death;
    private Animation<TextureAtlas.AtlasRegion> currentAnimation;

    private float animationTime;

    private final CollisionFilter collisionFilter;

    private final Collisions tempCollisions = new Collisions();

    @Getter
    private final GlyphLayout debugGlyphLayout = new GlyphLayout();

    public AiEntity(float x, float y, float width, float height, Level level, CollisionFilter collisionFilter) {
        super(x, y, width, height, level);

        this.collisionFilter = collisionFilter;
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);

        animationTime += delta;

        applyGravity(delta);
        setInput();
        checkGround(world);
        checkJump();
        checkMovementDirection();

        applyInput(delta);
        applyGravity(delta);
        applyFriction(delta);

        checkCollisionsAndApplyVelocity(world, delta);

        updateAnimation();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        if (currentAnimation == null)
            return;

        var frame = currentAnimation.getKeyFrame(animationTime);

        var drawX = isFacingRight ? position.x + animationXOffset : position.x + width - animationXOffset;
        var drawY = invertedGravity ? position.y + height : position.y;

        var drawWidth = isFacingRight ? frame.getRegionWidth() / level.getMap().getTileWidth() : -frame.getRegionWidth() / level.getMap().getTileWidth();
        var drawHeight = invertedGravity ? -frame.getRegionHeight() / level.getMap().getTileHeight() : frame.getRegionHeight() / level.getMap().getTileHeight();

        drawWidth *= 1.5f;
        drawHeight *= 1.5f;

        batch.draw(frame, drawX, drawY, drawWidth, drawHeight);
    }

    protected void updateAnimation() {
        if (isDead)
            setAnimation(death);
        else if (isJumping)
            setAnimation(jump);
        else if (!isGrounded)
            setAnimation(fall);
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

    private void checkGround(World<Entity> world) {
        world.project(item, position.x, position.y, width, height, position.x, position.y - fixInverted(0.1f), collisionFilter, tempCollisions);
        for (int i = 0; i < tempCollisions.size(); i++) {
            if (tempCollisions.get(i).type.equals(Response.slide)) {
                lastGrounded = System.currentTimeMillis();
                if (!isGrounded)
                    landed();
                isGrounded = true;
                return;
            }
        }

        isGrounded = false;
    }

    public void die() {
        if (!isDead)
            isDead = true;
    }

    protected void landed() { }

    private void checkJump() {
        if (!wantsToJump)
            return;

        if ((isGrounded || (System.currentTimeMillis() - lastGrounded) < jumpAfterGroundLeftMax) && !isJumping)
            jump();
    }

    protected void jump() {
        if (invertedGravity ? velocity.y > 0 : velocity.y < 0)
            velocity.y = 0;

        velocity.y += fixInverted(jumpForce);
        isJumping = true;
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
        if (collision.normal.y != 0) {
            // ceiling or floor
            velocity.y = 0;

            if (collision.normal.y == fixInverted(1)) {
                isJumping = false;
            }
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

    private void applyGravity(float delta) {
        velocity.y += fixInverted(gravity) * delta;

        if (isJumping && invertedGravity ? (velocity.y > 0) : (velocity.y < 0))
            isJumping = false;
    }

    protected float fixInverted(float val) {
        return invertedGravity ? -val : val;
    }

    public void debugTextRender(BitmapFont font, Camera cam, SpriteBatch screenBatch) {
        var pos = Vector3Pool.instance.obtain();
        pos.set(position, 0);
        cam.project(pos);

        var str = "Pos: " + position.x + "/" + position.y + "\n" +
                "Jum: " + isJumping + " ; Grn: " + isGrounded + "\n" +
                "Vel: " + velocity.x + "/" + velocity.y + "\n" +
                "Grv: " + fixInverted(gravity);
        font.draw(screenBatch, str, pos.x + width, pos.y + height);

        str = "WJ: " + wantsToJump + "\n" +
                "WL: " + wantsToMoveLeft + "\n" +
                "WR: " + wantsToMoveRight + "\n";
        debugGlyphLayout.setText(font, str);
        font.draw(screenBatch, str, pos.x - debugGlyphLayout.width, pos.y + height);


        str = currentAnimation.getKeyFrame(animationTime).name;
        debugGlyphLayout.setText(font, str);
        font.draw(screenBatch, str, pos.x - debugGlyphLayout.width / 2f, pos.y + debugGlyphLayout.height*2);

        Vector3Pool.instance.free(pos);
    }
}
