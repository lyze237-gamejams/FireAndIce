package dev.lyze.parallelworlds.screens.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.screens.game.Level;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Player extends GravityEntity {
    private final float movementSpeed = 10f;

    @Getter
    private final PlayerColor color;

    private float movementInputDirection;

    public Player(Level level, PlayerColor color, boolean invertedGravity) {
        super(0, 0, 2, 4, level);

        this.invertedGravity = invertedGravity;
        this.color = color;
    }

    @Override
    public void update(World<Entity> world, float delta) {
        checkInput();
        applyMovement();
        super.update(world, delta);
    }

    private void checkInput() {
        inputVelocity.set(0, 0);

        movementInputDirection = 0;
        var leftKey = color == PlayerColor.Blue ? Input.Keys.A : Input.Keys.LEFT;
        var rightKey = color == PlayerColor.Blue ? Input.Keys.D : Input.Keys.RIGHT;

        movementInputDirection += Gdx.input.isKeyPressed(leftKey) ? -1 : 0;
        movementInputDirection += Gdx.input.isKeyPressed(rightKey) ? 1 : 0;
    }

    private void applyMovement() {
        inputVelocity.set(movementSpeed * movementInputDirection, 0);
    }

    @Override
    public void debugRender(ShapeDrawer shapes) {
        shapes.setColor(color.getRenderColor());
        super.debugRender(shapes);
    }
}
