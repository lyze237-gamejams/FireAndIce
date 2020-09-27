package dev.lyze.parallelworlds.screens.game.entities.enemies.linked.impl;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.logger.Logger;
import dev.lyze.parallelworlds.screens.game.Level;
import dev.lyze.parallelworlds.screens.game.entities.Entity;
import dev.lyze.parallelworlds.screens.game.entities.TileEntity;
import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.LinkedEnemy;
import dev.lyze.parallelworlds.screens.game.entities.enemies.linked.LinkedEnemyKillPart;
import dev.lyze.parallelworlds.screens.game.entities.filters.BatCheckForPlayerCollisionFilter;
import dev.lyze.parallelworlds.screens.game.entities.filters.BatEnemyCollisionFilter;
import dev.lyze.parallelworlds.screens.game.entities.impl.PortalTile;
import dev.lyze.parallelworlds.screens.game.entities.players.Player;
import dev.lyze.parallelworlds.statics.Statics;
import lombok.Getter;

public class BatEnemy extends LinkedEnemy {
    private static final Logger<BatEnemy> logger = new Logger<>(BatEnemy.class);

    private boolean currentlyMoveRight = true;

    private State state = State.Idle;

    @Getter
    private final float startY;

    public BatEnemy(float x, float y, Level level, int partsOffset, boolean invertedGravity) {
        super(x, y, level, partsOffset, invertedGravity);

        startY = position.y;

        setRun(new Animation<>(0.1f, Statics.assets.getGame().getCharactersAtlas().getBat_walk(), Animation.PlayMode.LOOP));
        setDeath(new Animation<>(0.1f, Statics.assets.getGame().getCharactersAtlas().getBat_death(), Animation.PlayMode.NORMAL));

        setAnimationXOffset(-0.6f);

        setCollisionFilter(BatEnemyCollisionFilter.instance);
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);

        if (linkedEnemyKillPart.isDead()) {
            wantsToMoveLeft = 0;
            wantsToMoveRight = 0;
            return;
        }

        if (checkPlayerUnderMe(world) && state == State.Idle) {
            Statics.assets.getSound().playSmallPitch(0.5f, Statics.assets.getSound().getJump3());
            state = State.SwoopDown;
        }

        calculateMovement(world);
    }

    private boolean checkPlayerUnderMe(World<Entity> world) {
        world.project(item, position.x, position.y, width, height, position.x, position.y - fixInverted(20f), BatCheckForPlayerCollisionFilter.instance, getTempCollisions());

        for (int i = 0; i < getTempCollisions().size(); i++) {
            var collision = getTempCollisions().get(i);
            var userData = collision.other.userData;
            if (userData instanceof TileEntity) {
                var tileEntity = (TileEntity) userData;
                if (tileEntity.isHitbox())
                    return false;

                if (tileEntity instanceof PortalTile)
                    return false;
            }

            if (userData instanceof Player)
                return true;
        }

        return false;
    }

    private void calculateMovement(World<Entity> world) {
        switch (state) {
            case Idle:
                wantsToMoveRight = currentlyMoveRight ? 0.3f : 0;
                wantsToMoveLeft = currentlyMoveRight ? 0 : 0.3f;
                break;
            case SwoopDown:
                velocity.y = fixInverted(-0.5f);
                break;
            case SwoopUp:
                velocity.y = fixInverted(0.5f);
                if (isInvertedWorld() ? position.y < startY : position.y > startY) {
                    position.y = startY;
                    velocity.y = 0;
                    state = State.Idle;
                }
                break;
        }
    }

    @Override
    protected void landed() {
        super.landed();

        velocity.y = 0;
        state = State.SwoopUp;
    }

    @Override
    protected LinkedEnemyKillPart createEnemyKillPart(float x, float y, Level level, int partsOffset, boolean invertedGravity) {
        return new BatEnemyKillPart(x, y, level, partsOffset, this, !invertedGravity);
    }

    @Override
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (collision.other.userData instanceof Player) {
            logger.logInfo("Haha, player is a noob and died!");
            level.killPlayer();

            return;
        }

        if (state == State.SwoopDown) {
            System.out.println("UP UPYOUGO");
            velocity.y = 0;
            state = State.SwoopUp;
        }

        if (!collision.type.equals(Response.slide))
            return;

        currentlyMoveRight = !currentlyMoveRight;
    }

    private enum State {
        Idle, SwoopDown, SwoopUp
    }
}
