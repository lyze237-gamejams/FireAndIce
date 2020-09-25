package dev.lyze.parallelworlds.screens.game.entities.players;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.GravityEntity;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.impl.ExitTile;
import dev.lyze.parallelworlds.screens.game.entities.impl.PortalDirectionTile;
import dev.lyze.parallelworlds.screens.game.entities.enums.Direction;
import dev.lyze.parallelworlds.screens.game.entities.enums.PlayerColor;
import dev.lyze.parallelworlds.screens.game.entities.filters.PlayerCollisionFilter;
import dev.lyze.parallelworlds.screens.game.gamepads.VirtualGamepadGroup;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public abstract class Player extends GravityEntity {
    private static final Logger<Player> logger = new Logger<>(Player.class);

    @Getter
    private final PlayerColor color;

    private Direction portalDirection;
    private VirtualGamepadGroup gamepad;

    public Player(Level level, PlayerColor color, boolean invertedWorld, Animation<TextureAtlas.AtlasRegion> idle, Animation<TextureAtlas.AtlasRegion> run, Animation<TextureAtlas.AtlasRegion> jump, Animation<TextureAtlas.AtlasRegion> fall, Animation<TextureAtlas.AtlasRegion> death) {
        super(0, 0, 2, 1.25f, level, PlayerCollisionFilter.instance);

        setIdle(idle);
        setRun(run);
        setJump(jump);
        setFall(fall);
        setDeath(death);

        setInvertedWorld(invertedWorld);
        this.color = color;

        setAnimationXOffset(-0.6f);
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
            setInvertedWorld(oldPortalDirection == Direction.Down);
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

        var userData = collision.other.userData;

        if (userData instanceof ExitTile) {
            level.loadLevel(((ExitTile) userData).getLevelName());
            return;
        }

        if (userData instanceof PortalDirectionTile) {
            var portalDirection = (PortalDirectionTile) userData;
            this.portalDirection = portalDirection.getDirection();
        }
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
