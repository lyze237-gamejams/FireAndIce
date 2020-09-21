package dev.lyze.parallelworlds.screens.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.parallelworlds.screens.game.Level;

public class GravityEntity extends Entity {
    private final Vector2 inputVelocity = new Vector2(), worldVelocity = new Vector2(), combinedVelocity = new Vector2();

    public GravityEntity(float x, float y, float width, float height, Level level) {
        super(x, y, width, height, level);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        inputVelocity.set(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            inputVelocity.x = -64f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            inputVelocity.x = 64f;
        }

        combinedVelocity.set(inputVelocity);
        combinedVelocity.add(worldVelocity);

        combinedVelocity.scl(delta);

        //worldVelocity.add(0, -64 * delta);
        worldVelocity.add(0, -32 * delta);

        //getPosition().set(getPosition().x + combinedVelocity.x, getPosition().y + combinedVelocity.y);
    }
}
