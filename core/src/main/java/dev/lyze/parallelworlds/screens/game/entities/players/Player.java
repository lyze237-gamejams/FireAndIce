package dev.lyze.parallelworlds.screens.game.entities.players;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.AiEntity;
import dev.lyze.parallelworlds.screens.game.entities.Direction;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.PortalDirectionBlock;
import dev.lyze.parallelworlds.screens.game.gamepads.VirtualGamepadGroup;
import dev.lyze.parallelworlds.utils.Vector3Pool;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Player extends AiEntity {
    private static final Logger<Player> logger = new Logger<>(Player.class);

    private final Animation<TextureAtlas.AtlasRegion> idle, run, jump, fall, death;
    private Animation<TextureAtlas.AtlasRegion> currentAnimation;

    private float animationTime;

    @Getter
    private final PlayerColor color;

    private Direction portalDirection;
    private VirtualGamepadGroup gamepad;

    public Player(Level level, PlayerColor color, boolean invertedGravity, Animation<TextureAtlas.AtlasRegion> idle, Animation<TextureAtlas.AtlasRegion> run, Animation<TextureAtlas.AtlasRegion> jump, Animation<TextureAtlas.AtlasRegion> fall, Animation<TextureAtlas.AtlasRegion> death) {
        super(0, 0, 2 * 1.5f, 1.25f * 1.5f, level);

        this.currentAnimation = this.idle = idle;
        this.run = run;
        this.jump = jump;
        this.fall = fall;
        this.death = death;

        this.invertedGravity = invertedGravity;
        this.color = color;
    }

    @Override
    public void update(World<Entity> world, float delta) {
        animationTime += delta;
        checkInput();

        var oldPortalDirection = portalDirection;
        portalDirection = null;

        super.update(world, delta);

        if (oldPortalDirection != null && portalDirection == null) {
            // left all direction blocks
            logger.logInfo("DIRECTION " + oldPortalDirection);
            if (oldPortalDirection == Direction.Up)
                invertedGravity = false;
            else if (oldPortalDirection == Direction.Down)
                invertedGravity = true;
        }

        if (isJumping()) {
            setAnimation(jump);
        } else if (!isGrounded()) {
            setAnimation(fall);
        } else if (velocity.x > 0 || velocity.x < 0) {
            setAnimation(run);
        } else {
            setAnimation(idle);
        }
    }

    private void checkInput() {
        wantsToMoveLeft = gamepad.getLeftPressed();
        wantsToMoveRight = gamepad.getRightPressed();

        wantsToJump = gamepad.isJumpJustPressed();
    }

    @Override
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (collision.other.userData instanceof PortalDirectionBlock) {
            var portalDirection = (PortalDirectionBlock) collision.other.userData;
            this.portalDirection = portalDirection.getDirection();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(Color.WHITE);
        var frame = currentAnimation.getKeyFrame(animationTime);

        var drawX = isFacingRight ? position.x : position.x + width;
        var drawY = invertedGravity ? position.y + height : position.y;

        var drawWidth = isFacingRight ? frame.getRegionWidth() / level.getMap().getTileWidth() : -frame.getRegionWidth() / level.getMap().getTileWidth();
        var drawHeight = invertedGravity ? -frame.getRegionHeight() / level.getMap().getTileHeight() : frame.getRegionHeight() / level.getMap().getTileHeight();

        drawWidth *= 1.5f;
        drawHeight *= 1.5f;

        batch.draw(frame, drawX, drawY, drawWidth, drawHeight);

        super.render(batch);
    }

    private void setAnimation(Animation<TextureAtlas.AtlasRegion> newAnimation) {
        if (this.currentAnimation == newAnimation)
            return;

        this.currentAnimation = newAnimation;
        animationTime = 0;
    }

    @Override
    public void debugRender(ShapeDrawer shapes) {
        shapes.setColor(color.getRenderColor());
        super.debugRender(shapes);
    }

    public void setGamepadGroup(VirtualGamepadGroup virtualGamepad) {
        this.gamepad = virtualGamepad;
    }

    public void debugTextRender(BitmapFont font, Camera cam, SpriteBatch screenBatch) {
        super.debugTextRender(font, cam, screenBatch);

        var pos = Vector3Pool.instance.obtain();
        pos.set(position, 0);
        pos.add(width, height, 0);

        cam.project(pos);

        var text = currentAnimation.getKeyFrame(animationTime).name;
        getDebugGlyphLayout().setText(font, text);
        font.draw(screenBatch,
                text,
                pos.x - getDebugGlyphLayout().width / 2f, pos.y + getDebugGlyphLayout().height*2);

        Vector3Pool.instance.free(pos);
    }
}
