package dev.lyze.parallelworlds.screens.game.entities.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import dev.lyze.parallelworlds.statics.Statics;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Player extends AiEntity {
    private static final Logger<Player> logger = new Logger<>(Player.class);

    private final Animation<TextureAtlas.AtlasRegion> idle, run, jump, death;

    @Getter
    private final PlayerColor color;

    private Direction portalDirection;
    private VirtualGamepadGroup gamepad;

    public Player(Level level, PlayerColor color, boolean invertedGravity, Animation<TextureAtlas.AtlasRegion> idle, Animation<TextureAtlas.AtlasRegion> run, Animation<TextureAtlas.AtlasRegion> jump, Animation<TextureAtlas.AtlasRegion> death) {
        super(0, 0, 2, 4, level);

        this.idle = idle;
        this.run = run;
        this.jump = jump;
        this.death = death;

        this.invertedGravity = invertedGravity;
        this.color = color;
    }

    @Override
    public void update(World<Entity> world, float delta) {
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
    }

    private void checkInput() {
        wantsToMoveLeft = gamepad.isLeftPressed();
        wantsToMoveRight = gamepad.isRightPressed();

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
        batch.setColor(color.getRenderColor());
        batch.draw(Statics.assets.getGame().getSharedLevelAssets().getPixel(), position.x, position.y, width, height);
        batch.setColor(Color.WHITE);
        super.render(batch);
    }

    @Override
    public void debugRender(ShapeDrawer shapes) {
        shapes.setColor(color.getRenderColor());
        super.debugRender(shapes);
    }

    public void setGamepadGroup(VirtualGamepadGroup virtualGamepad) {
        this.gamepad = virtualGamepad;
    }
}
