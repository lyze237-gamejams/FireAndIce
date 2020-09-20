package dev.lyze.parallelworlds.screens.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.parallelworlds.screens.game.World;

public class Entity {
    protected final World world;
    private final Rectangle hitbox;

    private final Vector2 inputVelocity = new Vector2(), worldVelocity = new Vector2(), combinedVelocity = new Vector2();

    public Entity(int x, int y, int width, int height, World world) {
        this.world = world;

        hitbox = new Rectangle(x, y, width, height);
    }

    public void update(float delta) {
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

        hitbox.setPosition(hitbox.getX() + combinedVelocity.x, hitbox.getY() + combinedVelocity.y);
    }

    public void render(SpriteBatch batch) {

    }

    public void debugRender(ShapeRenderer shapes) {
        shapes.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }
}
