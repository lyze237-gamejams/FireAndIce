package dev.lyze.parallelworlds.screens.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Player extends AiEntity {
    private static final Logger<Player> logger = new Logger<>(Player.class);

    @Getter
    private final PlayerColor color;

    private boolean inPortalBlock;
    private Direction portalDirection;

    public Player(Level level, PlayerColor color, boolean invertedGravity) {
        super(0, 0, 2, 4, level);

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
        var leftKey = color == PlayerColor.Blue ? Input.Keys.A : Input.Keys.LEFT;
        var rightKey = color == PlayerColor.Blue ? Input.Keys.D : Input.Keys.RIGHT;
        var jump = color == PlayerColor.Blue ? Input.Keys.W : Input.Keys.UP;

        wantsToMoveLeft = Gdx.input.isKeyPressed(leftKey);
        wantsToMoveRight = Gdx.input.isKeyPressed(rightKey);

        wantsToJump = Gdx.input.isKeyJustPressed(jump);
    }

    @Override
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (collision.other.userData instanceof PortalBlock) {
            inPortalBlock = true;
        }

        if (collision.other.userData instanceof PortalDirectionBlock) {
            var portalDirection = (PortalDirectionBlock) collision.other.userData;
            this.portalDirection = portalDirection.getDirection();
        }
    }

    @Override
    public void debugRender(ShapeDrawer shapes) {
        shapes.setColor(color.getRenderColor());
        super.debugRender(shapes);
    }
}
