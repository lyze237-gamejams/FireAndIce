package dev.lyze.parallelworlds.screens.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.dongbat.jbump.World;
import dev.lyze.parallelworlds.screens.game.Level;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Player extends AiEntity {
    @Getter
    private final PlayerColor color;

    public Player(Level level, PlayerColor color, boolean invertedGravity) {
        super(0, 0, 2, 4, level);

        this.invertedGravity = invertedGravity;
        this.color = color;
    }

    @Override
    public void update(World<Entity> world, float delta) {
        checkInput();
        super.update(world, delta);
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
    public void debugRender(ShapeDrawer shapes) {
        shapes.setColor(color.getRenderColor());
        super.debugRender(shapes);
    }
}
