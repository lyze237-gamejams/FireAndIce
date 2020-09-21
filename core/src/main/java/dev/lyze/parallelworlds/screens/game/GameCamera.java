package dev.lyze.parallelworlds.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dev.lyze.parallelworlds.utils.Vector3Pool;

public class GameCamera extends OrthographicCamera {
    public void zoomToPlayers(Vector2 pos1, Vector2 pos2) {
        var redPlayerViewport = Vector3Pool.instance.obtain();
        var bluePlayerViewport = Vector3Pool.instance.obtain();

        redPlayerViewport.set(pos1.x / Gdx.graphics.getWidth(), pos1.y / Gdx.graphics.getHeight(), 0);
        bluePlayerViewport.set(pos2.x / Gdx.graphics.getWidth(), pos2.y / Gdx.graphics.getHeight(), 0);

        this.project(redPlayerViewport);
        this.project(bluePlayerViewport);

        var viewportDist = redPlayerViewport.dst(bluePlayerViewport);
        if (viewportDist > 0.8f) {
            this.zoom = this.zoom + 0.01f;
        }
        if (this.zoom < 0.5f) {
            this.zoom = 1f;
        }

        Vector3Pool.instance.free(redPlayerViewport);
        Vector3Pool.instance.free(bluePlayerViewport);
    }

    public void lerpToPlayers(Vector2 pos1, Vector2 pos2) {
        var avgX = (pos1.x + pos2.x) / 2f;
        var avgY = (pos1.y + pos2.y) / 2f;

        this.position.x = this.position.x + (avgX - this.position.x) * 0.1f;
        this.position.y = this.position.y + (avgY - this.position.y) * 0.1f;
    }

    public void keepInBoundaries(Rectangle boundaries) {
        var halfViewportWidth = (this.viewportWidth * this.zoom) / 2f;
        var halfViewportHeight = (this.viewportHeight * this.zoom) / 2f;

        this.position.x = MathUtils.clamp(this.position.x, boundaries.getX() + halfViewportWidth, boundaries.getX() + boundaries.getWidth() - halfViewportWidth);
        this.position.y = MathUtils.clamp(this.position.y, boundaries.getY() + halfViewportHeight, boundaries.getY() + boundaries.getHeight() - halfViewportHeight);
    }
}
